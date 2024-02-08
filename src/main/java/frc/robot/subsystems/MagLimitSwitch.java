package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MagLimitSwitch  extends SubsystemBase{
    private DigitalInput limitSwitch = new DigitalInput(1); // figure out what actual outlet called

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