/* 
* Linear interpolation method
* Take known distance values, `ty` from `limelight-speedy` 
* and arm angle poses using `setArmPose()` 
* and use linear interpolation to figures out values in between
*/

// Tested on Friday, 5-10-2024, and it worked as expected

package frc.robot.xperimental;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.arm.ArmSubsystem;
import frc.robot.vision.LimelightHelpers;

public class VisionArmPose extends SubsystemBase {
    private final LimelightHelpers heimdall;
    private final ArmSubsystem m_armSubsystem;
    private static final double TY_LOWER_BOUND = -10;
    private static final double TY_UPPER_BOUND = -2;
    private static final double ARM_POSE_LOWER_BOUND = 20;
    private static final double ARM_POSE_UPPER_BOUND = 0;

    public VisionArmPose(LimelightHelpers heimdall, ArmSubsystem armSubsystem) {
        this.heimdall = heimdall;
        this.m_armSubsystem = armSubsystem;
    }

    @Override
    public void periodic() {
        // Get the ty value from the limelight
        double ty = LimelightHelpers.getTY("limelight-speedy");

        // Interpolate the setArmPose value based on the ty value
        double interpolatedArmPose = interpolate(ty, TY_LOWER_BOUND, TY_UPPER_BOUND, ARM_POSE_LOWER_BOUND, ARM_POSE_UPPER_BOUND);

        // Set the arm pose
        m_armSubsystem.setArmPose(interpolatedArmPose);

        // Print the interpolated arm pose. For debugging purposes only
        // System.out.println("Interpolated Arm Pose: " + interpolatedArmPose);
    }

    private double interpolate(double x, double x1, double x2, double y1, double y2) {
        if (x1 == x2) {
            return (y1 + y2) / 2;
        }
        return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
    }
}