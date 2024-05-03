package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.util.LedSubsystem;
import frc.robot.vision.LimelightHelpers;

public class LimelightLedFeedback extends Command {
    private LimelightHelpers limelight;
    private NetworkTable table;
    private NetworkTableEntry tyEntry;
    private double ty;
    private LedSubsystem led;

    // LED colors defined in LedSubsystem

    // Define your ranges for turning LEDs different colors
    private final double RANGE_1 = -10.0;   //  Red
    private final double RANGE_2 = -8.0;   //
    private final double RANGE_3 = -6.0;
    private final double RANGE_4 = -4.0;
    private final double RANGE_5 = -2.0;

    public LimelightLedFeedback(LimelightHelpers limelight, LedSubsystem led) {
        this.limelight = limelight;
        this.led = led;
        addRequirements(limelight, led);

        // requires(limelight);
    }

    @Override
    public void initialize() {
        // Get the network table and entry for the Limelight's tx value
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tyEntry = table.getEntry("ty");
    }

    @Override
    public void execute() {
        // Get the current value of tx from the network table
        ty = tyEntry.getDouble(0.0);

        // Choose LED color based on the value of tx
        if (ty < RANGE_1) {
            led.ColorFlowRed();
        } else if (ty < RANGE_2) {
            led.ColorFlowOrange();
        } else if (ty < RANGE_3) {
            led.ColorFlowYellow();
        } else if (ty < RANGE_4) {
            led.ColorFlowGreen();
        } else if (ty < RANGE_5) {
            led.ColorFlowBlue();
        } else {
            led.ColorFlowPurple();            
        }
    }

    @Override
    public boolean isFinished() {
        return false; // This command never finishes, it continually updates LED color based on tx value
    }

} // end of class LimelightLedFeedback
