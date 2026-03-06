// package frc.robot.subsystems;


// import frc.robot.Constants.GameConstants;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.Command;
// import static edu.wpi.first.units.Units.*;
// import edu.wpi.first.units.measure.Angle;
// import edu.wpi.first.units.measure.MutAngle;

// public class AutoAlign {
//     boolean targetVisible = false;
//         double targetYaw = 0.0;
//         var results = camera.getAllUnreadResults();
//         if (!results.isEmpty()) {
//             // Camera processed a new frame since last
//             // Get the last one in the list.
//             var result = results.get(results.size() - 1);
//             if (result.hasTargets()) {
//                 // At least one AprilTag was seen by the camera
//                 for (var target : result.getTargets()) {
//                     if (target.getFiducialId() == 7) {
//                         // Found Tag 7, record its information
//                         targetYaw = target.getYaw();
//                         targetVisible = true;
//                     }
//                 }
//             }
//         }
// }
