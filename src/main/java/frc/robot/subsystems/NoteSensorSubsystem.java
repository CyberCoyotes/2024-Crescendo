package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* 
 * Subsystem class to primarily use a Time of Flight sensor from 'Playing with Fusion'.
 * It will read the distance from the sensor to the 'note' and determine if the note is in a load position.
 * It should return an actual distance reading to the SmartDashboard and a boolean for a method 'isNoteLoaded'
 * This sensor data will change LED status and enable/disable intake and index motors
 */
public class NoteSensorSubsystem extends SubsystemBase{
    
    /* Constructor */
    public NoteSensorSubsystem() {
    }

    public void isNoteLoaded() {
        // Code to run repeatedly while the command is scheduled to run
        
    }

} // end of class NoteSensorSubsystem
