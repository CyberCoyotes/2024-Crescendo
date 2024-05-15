package frc.robot.xperimental;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NoteVision extends Command {
    private final double targetArea = 100.0; // Example target area for object detection
    private final double targetDistance = 50.0; // Example target distance for object detection
    private final double targetHeight = 320.0; // Example target height for object detection

    private boolean isNotesDetected = false;

    public NoteVision() {
        // addRequirements(); // Assuming you have a Limelight subsystem in RobotContainer
    }

    @Override
    public void initialize() {
        isNotesDetected = false; // Reset detection flag at the beginning of each command execution
    }

    @Override
    public void execute() {
        // Accessing Limelight data
        double tv = NetworkTableInstance.getDefault().getTable("limelight-marvin").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight-marvin").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight-marvin").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight-marvin").getEntry("ta").getDouble(0);

        // Your object detection logic
        if (tv == 1 && ta > targetArea) { // Example condition for object detection
            // Your distance and height calculations
            // double distance = (targetHeight - RobotContainer.cameraHeight) / Math.tan(Math.toRadians(RobotContainer.cameraAngle + ty));
            // Assuming you have a Coral object detection method
            // isNotesDetected = Coral.detectObject("notes") && distance < targetDistance;
        } else {
            isNotesDetected = false;
        }

        // Example delay to avoid excessive network traffic
        Timer.delay(0.1);
    }

    @Override
    public boolean isFinished() {
        // Finish command execution if the object is detected
        return isNotesDetected;
    }

    @Override
    public void end(boolean interrupted) {
        // Clean up resources if needed
    }
}
