package frc.robot.subsystems;


import org.photonvision.*;
import org.photonvision.PhotonCamera;
import org.photonvision.common.*;
import org.photonvision.targeting.*;
import org.photonvision.targeting.MultiTargetPNPResult;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import org.photonvision.PhotonPoseEstimator; 

import frc.robot.Constants.VisionConstants;
import frc.robot.Constants.GameConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import static edu.wpi.first.units.Units.*;

import java.util.List;
import java.util.Optional;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.MutAngle;

public class PhotonVision extends SubsystemBase {
    
    //Create the camera
    public static final PhotonCamera camera = new PhotonCamera(VisionConstants.kCameraName);

    //Create a pose estimator
    PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(GameConstants.kWeldedLayout, VisionConstants.kRobotToCam);

    //Initialize vision estimation variable
    Optional<EstimatedRobotPose> visionEst = Optional.empty();


    //Get April Tag Data
    public Command getRobotFieldData() {
        return run(() -> {

            //Get all unread results
            List<PhotonPipelineResult> results = camera.getAllUnreadResults();

            //Check if the latest result has April Tags
            if (!results.isEmpty()) {
                PhotonPipelineResult result = results.get(results.size() - 1); //Takes the last item in the results list, i.e. last detected result

                boolean HasTargets = result.hasTargets();
                
                //Get a list of targets
                if (HasTargets) {

                    List<PhotonTrackedTarget> targets = result.getTargets();
                    
                    var multiTagResult = result.getMultiTagResult();
                    if (multiTagResult.isPresent()) {
                        visionEst = poseEstimator.estimateCoprocMultiTagPose(result);
                            if (visionEst.isEmpty()) {
                                visionEst = poseEstimator.estimateLowestAmbiguityPose(result);
                            }
                    }
                    
                    //Select best target
                    PhotonTrackedTarget target = result.getBestTarget();
                    

                    //Transform from the camera to April Tag, can also used 2d transform: check docs
                    Transform3d transform_to_target = target.getBestCameraToTarget();

                    //Pose ambiguity
                    double poseAmbiguity = target.getPoseAmbiguity();

                    //Get ID of April Tag Found
                    int TagID = target.getFiducialId();
                    
                    //Get distance to tag
                    //double distanceToTag = PhotonUtils.getDistanceToPose(robotPose, targetPose);

                    // Calculate a translation from the camera to the target.
                    //Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(distanceToTag, Rotation2d.fromDegrees(-target.getYaw()));
                    
                }
                

            }

        });

    }
    
    @Override
    public void periodic() {
        double targetYaw = 0.0;
        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
          // Camera processed a new frame since last
          // Get the last one in the list.
          var result = results.get(results.size() - 1);
          if (result.hasTargets()) {
              // At least one AprilTag was seen by the camera
              for (var target : result.getTargets()) {
                  if (target.getFiducialId() == -0) {// need to replace with tag we are looking for
                      // Found Tag 0, record its information
                      targetYaw = target.getYaw();
                  }
              }
          }
        }
    }
}