package frc.robot.experimental;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MagLimitSwitch  extends SubsystemBase{

    /* This could (should?) be moved into ArmSubsystem.java once tested. No other subsystem would use this limit switch */
    /* REV Magnetic Limit Switch
     * To be used for the arm for triggering a reset to the arm encoder when its position is "home"
     */
    private DigitalInput limitSwitch = new DigitalInput(1);

    public boolean getSwitchState() {
     return limitSwitch.get();
    }
    public void magSwitch() {
        if (getSwitchState() == true) {
            // Reset Arm
        } 
    }
    @Override
    public void periodic(){
     SmartDashboard.putBoolean("Mag Limit Switch", getSwitchState());
    } 
    

}