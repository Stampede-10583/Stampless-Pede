// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants.ShooterConstants;

// import com.revrobotics.spark.SparkClosedLoopController;
// import com.revrobotics.spark.SparkMax;

// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.ctre.phoenix6.CANBus;
// import com.ctre.phoenix6.hardware.TalonFX;

// public class MotorTest extends SubsystemBase {
//     private final SparkMax TestMotor = new SparkMax(ShooterConstants.kShooterLoaderMotorCanID, MotorType.kBrushless);
//     private final SparkClosedLoopController pidControllerTest;

//     public MotorTest(){
//         pidControllerTest = TestMotor.getClosedLoopController();
//     }

//     public Command runTestMotor(){
//         return runOnce(() -> {
//             TestMotor.set(0.2);
            
//             //System.out.println(this.getEncoderCount()); hello world, i am world not hello, i am saying hello to the world.
//     });
//     }

//     public Command stopTestMotor(){
//         return runOnce(() ->{
//             TestMotor.stopMotor();

//         });
//     }
// }
