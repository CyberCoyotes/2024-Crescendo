package frc.robot.xperimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.vision.LimelightHelpers;
import frc.robot.arm.ArmSubsystem;
import frc.robot.util.LEDs;

@SuppressWarnings("unused")

public class FeedbackDistance extends Command{
    
    /* A command class with methods
     * Functions:
     * - Get the `ty` value from the limelight
     * - Check the "ty range" of the `ty` value, if it is within the range 8 to -8, return true
     * - Check the "ty range" of the `ty` value, if it is within the range beyond -8, return false
     * - If the "ty range is true", set the led color to green
     * - If the "ty range is false", set the led color to purple
     */
    private LimelightHelpers limelight;
    private LEDs led;
    private Vision3 vision;
    private ArmSubsystem arm;

    public FeedbackDistance(LimelightHelpers limelight, LEDs led, Vision3 vision, ArmSubsystem arm) {
        this.limelight = limelight;
        this.led = led;
        this.vision = vision;
    }

    @Override
    public void execute() {
        // vision.checkTYRange();
    }

}
