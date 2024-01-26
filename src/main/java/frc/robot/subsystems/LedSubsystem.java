package frc.robot.subsystems;

/* Subsystem class for controlling the LEDs on the robot. used to indicate the status of other subsystems.
* It will be use data from the Time of Flight sensor data (NoteSensorSubsystem) for determining the load status/position of a 'note'
* It is also partially cosmetic
*/

/* 
CANdle requires the Phoenix (v5) vendordep
https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2024-latest.json

com.ctre.phoenix.led.CANdle Class Reference 
https://api.ctr-electronics.com/phoenix/release/java/
*/

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.RainbowAnimation;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LedSubsystem extends SubsystemBase{
    
    /* Constructor */
    public LedSubsystem() {
        
    }

} // end of class LedSubsystem
