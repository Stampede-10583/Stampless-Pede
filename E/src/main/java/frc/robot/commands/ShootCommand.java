// package frc.robot.commands;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants.IntakeConstants;
// import frc.robot.subsystems.IntakeArm;
// import frc.robot.subsystems.ShooterSubsystem;
// import frc.robot.subsystems.Vision;

// public class ShootCommand extends Command {
//     private final ShooterSubsystem m_shooter;
//     private final IntakeArm m_intakeArm;
//     private final Vision m_vision;
//     private final int targetTagID;
//     private double targetDistance;


//     public ShootCommand(ShooterSubsystem shooterArg, IntakeArm intakeArmArg, Vision visionArg, int targetTagIDArg) {
//         m_shooter = shooterArg;
//         m_intakeArm = intakeArmArg;
//         m_vision = visionArg;
//         targetTagID = targetTagIDArg;
//         addRequirements(m_shooter, m_vision);
//     }

//     @Override
//     public void initialize() {
//         targetDistance = m_vision.getTagDistance(targetTagID); //rework this i am just using it for now for theoretical code
//         m_shooter.runFrontMotors(targetDistance); /// THE TARGET DISTANCE IS BEING USED A VELOCITY THIS MUST BE CHANGES ASAP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//         m_shooter.runRearMotor(targetDistance);
//         Timer.delay(.1);
//         m_intakeArm.runIntake(IntakeConstants.kIntakeVelocity/2); //FOR NOW
//         m_shooter.runLoaderMotor();
//     }

//     @Override
//     public void execute() {
//         Timer.delay(1);
//         m_intakeArm.retractIntake(IntakeConstants.kIntakeJiggleVelocity);
//         Timer.delay(.1);
//         m_intakeArm.deployIntake(IntakeConstants.kIntakeJiggleVelocity);
//         Timer.delay(.1);
//         m_intakeArm.retractIntake(IntakeConstants.kIntakeJiggleVelocity);
//         Timer.delay(.1);
//         m_intakeArm.deployIntake(IntakeConstants.kIntakeJiggleVelocity);
//         Timer.delay(.1);
//         m_intakeArm.stopDeployMotor();
//     }

//     @Override
//     public void end(boolean isFinished) {
//         m_intakeArm.stopIntake();
//         m_shooter.stopShooter();
//         m_intakeArm.stopDeployMotor();
//     }

//     @Override
//     public boolean isFinished() {
//         return false;
//     }
// }