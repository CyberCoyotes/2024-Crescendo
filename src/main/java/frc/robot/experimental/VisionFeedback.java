package frc.robot.experimental;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.util.LEDSubsystem;
import frc.robot.vision.LimelightHelpers;

public class VisionFeedback extends Command {
    private LimelightHelpers limelight;
    private NetworkTable table;
    private NetworkTableEntry tyEntry;
    private double ty;
    private LEDSubsystem led;

    // LED colors defined in LedSubsystem

    // Define your ranges for turning LEDs different colors
    private final double TAG_RANGE_1 = -10.0;   //  Red
    private final double TAG_RANGE_2 = -8.0;   //
    private final double TAG_RANGE_3 = -6.0;
    private final double TAG_RANGE_4 = -4.0;
    private final double TAG_RANGE_5 = -2.0;

    public VisionFeedback(LimelightHelpers limelight, LEDSubsystem led) {
        this.limelight = limelight;
        this.led = led;
        // addRequirements(limelight, led); // this will throw an error because these are NOT subsystems
    }

    @Override
    public void initialize() {
        // Get the network table and entry for the Limelight's ty value
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tyEntry = table.getEntry("ty");
        
    }

    @Override
    public void execute() {
        // Get the current value of ty from the network table
        ty = tyEntry.getDouble(0.0);
        SmartDashboard.putNumber("Feedback ty", ty);

        // Choose LED color based on the value of tx
        if (ty < TAG_RANGE_1) {
            led.ColorFlowRed();
        } else if (ty < TAG_RANGE_2) {
            led.ColorFlowOrange();
        } else if (ty < TAG_RANGE_3) {
            led.ColorFlowYellow();
        } else if (ty < TAG_RANGE_4) {
            led.ColorFlowGreen();
        } else if (ty < TAG_RANGE_5) {
            led.ColorFlowBlue();
        } else if (ty > TAG_RANGE_1){
            led.ColorFlowPurple();            
        }
    }

    @Override
    public boolean isFinished() {
        // This command never finishes, it continually updates LED color based on ty value
        return false; 
    }


} // end of class LimelightLedFeedback
