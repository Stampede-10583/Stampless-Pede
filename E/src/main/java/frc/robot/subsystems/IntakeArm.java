// package frc.robot.subsystems;

// import frc.robot.Constants.IntakeConstants;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.Command;
// import static edu.wpi.first.units.Units.*;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.ctre.phoenix6.configs.MotorOutputConfigs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.DutyCycleOut;
// import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.NeutralModeValue;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class IntakeArm extends SubsystemBase {
//     private final TalonFX m_DeployMotor = new TalonFX(IntakeConstants.kDeployMotorCanID);
//     private final TalonFX m_IntakeMotor = new TalonFX(IntakeConstants.kIntakeMotorCanID);
//     private final DutyCycleOut m_DeployDutyCycle = new DutyCycleOut(IntakeConstants.kDeployDutyCycle);
//     private final DutyCycleOut m_RetractDutyCycle = new DutyCycleOut(IntakeConstants.kRetractDutyCycle);
//     private final VelocityTorqueCurrentFOC m_velocityTorque = new VelocityTorqueCurrentFOC(0).withSlot(0);

//     public IntakeArm() {
//         // Initialize your intake arm components here
//         final TalonFXConfiguration deployConfig = new TalonFXConfiguration()
//                 .withMotorOutput(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake)
//                         .withInverted(InvertedValue.CounterClockwise_Positive));
//         deployConfig.TorqueCurrent.withPeakForwardTorqueCurrent(Amps.of(IntakeConstants.kDeployMaxCurrent))
//                 .withPeakReverseTorqueCurrent(Amps.of(-IntakeConstants.kDeployMaxCurrent));
//         final TalonFXConfiguration intakeConfig = deployConfig.clone().withMotorOutput(deployConfig.MotorOutput.clone()
//                 .withNeutralMode(NeutralModeValue.Coast).withInverted(InvertedValue.CounterClockwise_Positive));
//         intakeConfig.TorqueCurrent.withPeakForwardTorqueCurrent(Amps.of(IntakeConstants.kIntakeMaxCurrent))
//                 .withPeakReverseTorqueCurrent(Amps.of(-IntakeConstants.kIntakeMaxCurrent));
//         // set slot 0 gains
//         deployConfig.Slot0.kS = IntakeConstants.kDeployMotorkS; // Add 0.25 V output to overcome static friction
//         deployConfig.Slot0.kV = IntakeConstants.kDeployMotorkV; // A velocity target of 1 rps results in 0.12 V output
//         deployConfig.Slot0.kA = IntakeConstants.kDeployMotorkA; // An acceleration of 1 rps/s requires 0.01 V output
//         deployConfig.Slot0.kP = IntakeConstants.kDeployMotorkP; // A position error of 2.5 rotations results in 12V
//         deployConfig.Slot0.kI = IntakeConstants.kDeployMotorkI; // no output for integrated error
//         deployConfig.Slot0.kD = IntakeConstants.kDeployMotorkD; // A velocity error of 1 rps results in 0.1 V output

//         intakeConfig.Slot0.kS = IntakeConstants.kIntakeMotorkS; // Add 0.25 V output to overcome static friction
//         intakeConfig.Slot0.kV = IntakeConstants.kIntakeMotorkV; // A velocity target of 1 rps results in 0.12 V output
//         intakeConfig.Slot0.kA = IntakeConstants.kIntakeMotorkA; // An acceleration of 1 rps/s requires 0.01 V output
//         intakeConfig.Slot0.kP = IntakeConstants.kIntakeMotorkP; // A position error of 2.5 rotations results in 12V
//         intakeConfig.Slot0.kI = IntakeConstants.kIntakeMotorkI; // no output for integrated error
//         intakeConfig.Slot0.kD = IntakeConstants.kIntakeMotorkD; // A velocity error of 1 rps results in 0.1 V output

//         m_IntakeMotor.getConfigurator().apply(intakeConfig.Slot0);
//         m_DeployMotor.getConfigurator().apply(deployConfig.Slot0);
//         m_DeployMotor.setPosition(IntakeConstants.kIntakeArmRotationOffset);
//     }
//     // to do: switch to a closed loop control of the deploy motor using the angle
//     // measurement as feedback, and add a command to move to a specific angle

//     public void deployIntake(double velocity) {
//         m_DeployMotor.setControl(m_velocityTorque.withVelocity(velocity));
//     }

//     public Command deployIntakeCommand() {
//         return runOnce(() -> {
//             deployIntake(IntakeConstants.kDeployVelocity);
//         });
//     }

//     public void retractIntake(double velocity) {
//         m_DeployMotor.setControl(m_velocityTorque.withVelocity(velocity));
//     }

//     public Command retractIntakeCommand() {
//         return runOnce(() -> {
//             retractIntake(IntakeConstants.kRetractVelocity);
//         });
//     }

//     public void stopDeployMotor() {
//         m_DeployMotor.stopMotor();
//     }

//     public Command stopDeployMotorCommand() {
//         return runOnce(() -> {
//             stopDeployMotor();
//         });
//     }

//     public void runIntake(double velocity) {
//         m_IntakeMotor.setControl(m_velocityTorque.withVelocity(velocity));
//     }

//     public Command runIntakeCommand() {
//         return runOnce(() -> {
//             runIntake(IntakeConstants.kIntakeVelocity);
//         });
//     }

//     public Command runIntakeReverse() {
//         return runOnce(() -> {
//             m_IntakeMotor.set(-.75);
//         });
//     }

//     public void stopIntake() {
//         m_IntakeMotor.stopMotor();
//     }

//     public Command stopIntakeCommand() {
//         return runOnce(() -> {
//             stopIntake();
//         });
//     }

//     public double getAngle() {
//         m_DeployMotor.getPosition().refresh();
//         double rawAngle = m_DeployMotor.getPosition().getValueAsDouble() * IntakeConstants.kArmDegreesPerRotation /*
//                                                                                                                    * 95
//                                                                                                                    * degree
//                                                                                                                    * intake
//                                                                                                                    * offset
//                                                                                                                    */;

//         return rawAngle;
//     }

//     @Override
//     public void periodic() {
//         getAngle();
//         m_IntakeMotor.getVelocity().refresh();
//         SmartDashboard.putNumber("Intake Angle", getAngle());
//         SmartDashboard.putNumber("Intake RPM", m_IntakeMotor.getVelocity().getValueAsDouble());
//     }

//     @Override
//     public void simulationPeriodic() {
//         getAngle(); // Update the angle measurement in simulation
//         m_IntakeMotor.getVelocity().refresh();
//         SmartDashboard.putNumber("Intake Angle", getAngle());
//         SmartDashboard.putNumber("Intake RPM", m_IntakeMotor.getVelocity().getValueAsDouble());
//     }
// }
