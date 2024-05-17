package frc.robot.xperimental;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionChatAimSubsystem extends SubsystemBase {
    private final NetworkTable limelightTable;

    public VisionChatAimSubsystem() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight-marvin");
    }

    public double getHorizontalOffset() {
        return limelightTable.getEntry("tx").getDouble(0.0);
    }

    public boolean isTargetVisible() {
        return limelightTable.getEntry("tv").getDouble(0.0) > 0.0;
    }
}

// https://chatgpt.com/c/d266be22-d056-4462-b1db-1a666c01cc3f