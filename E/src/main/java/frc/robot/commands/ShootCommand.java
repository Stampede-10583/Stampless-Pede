package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.Vision;
import java.util.HashMap;

public class ShootCommand extends Command {
    private final ShooterSubsystem m_shooter;
    private final IntakeArm m_intakeArm;
    private final Vision m_vision;
    private final int[] targetTagIDs;
    private double closestRadius;

    record MotorOutputVelocities(double FrontMotorVelocityRPM, double RearMotorVelocityRPM) {
    }

    private HashMap<Double, MotorOutputVelocities> distanceToVelocityMap = new HashMap<>(); // this is a placeholder for
                                                                                            // the actual map that will
                                                                                            // be used to convert
                                                                                            // distance to velocity

    public ShootCommand(ShooterSubsystem shooterArg, IntakeArm intakeArmArg, Vision visionArg, int[] targetTagIDArgs) {
        m_shooter = shooterArg;
        m_intakeArm = intakeArmArg;
        m_vision = visionArg;
        targetTagIDs = targetTagIDArgs;
        addRequirements(m_shooter, m_vision);
        distanceToVelocityMap.put(OperatorConstants.kRadii[0], new MotorOutputVelocities(100.0, 100.0)); // Example
                                                                                                         // mapping,
                                                                                                         // replace with
                                                                                                         // actual
                                                                                                         // values
        distanceToVelocityMap.put(OperatorConstants.kRadii[1], new MotorOutputVelocities(200.0, 200.0)); // Example
                                                                                                         // mapping,
                                                                                                         // replace with
                                                                                                         // actual
                                                                                                         // values
        distanceToVelocityMap.put(OperatorConstants.kRadii[2], new MotorOutputVelocities(300.0, 300.0)); // Example
                                                                                                         // mapping,
                                                                                                         // replace with
                                                                                                         // actual
                                                                                                         // values
    }

    @Override
    public void initialize() {
        closestRadius = m_vision.findClosestRadius(OperatorConstants.kRadii, m_vision.getTagDistance(targetTagIDs[1]));
        m_shooter.runFrontMotors(distanceToVelocityMap.get(closestRadius).FrontMotorVelocityRPM);
        m_shooter.runRearMotor(distanceToVelocityMap.get(closestRadius).RearMotorVelocityRPM);
        Timer.delay(.1);
        m_intakeArm.runIntake(-(IntakeConstants.kIntakeVelocity / 2)); // FOR NOW
        m_shooter.runLoaderMotor();
    }

    @Override
    public void execute() {
        Timer.delay(1);
        m_intakeArm.deployIntake(IntakeConstants.kIntakeJigglePosition);
        Timer.delay(.1);
        m_intakeArm.deployIntake(0);
        Timer.delay(.1);
        m_intakeArm.deployIntake(IntakeConstants.kIntakeJigglePosition);
        Timer.delay(.1);
        m_intakeArm.deployIntake(0);
        Timer.delay(.1);
        m_intakeArm.stopDeployMotor();
    }

    @Override
    public void end(boolean isFinished) {
        m_intakeArm.deployIntake(0);
        m_intakeArm.stopIntake();
        m_shooter.stopShooter();
        m_intakeArm.stopDeployMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}