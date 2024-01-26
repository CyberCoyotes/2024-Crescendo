package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.RunCommand;

/* Subsystem class to primarily use a Time of Flight sensor from 'Playing with Fusion'.
 * It will read the distance from the sensor to the 'note' and determine if the note is in a load position.
 * It should return an actual distance reading to the SmartDashboard and a boolean for a method 'isNoteLoaded'
 * This sensor data will change LED status and enable/disable intake and index motors
 */

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class NoteSensorSubsystem extends SubsystemBase{
    
     /* Set ID in web interface http://172.22.11.2:5812/  */   
     private TimeOfFlight noteDistance = new TimeOfFlight(Constants.CANIDs.timeOfFlightID);

     /* Set the distance to the note to be considered 'in load position.'
     Measure in (mm) to determine an appropriate value.*/
     public int noteDistanceCheck = 0;
  
    /* Constructor */
    public NoteSensorSubsystem() {
        /* Initialize the sensor, and '.setRangingMode(RangingMode.Short)' for this usage.
        *
        | Sample value  | Time  |
        |-------------  |------ |
        | 1             | 20 ms |
        | 2             | 33 ms |
        | 3             | 50 ms |
        | 4 (default)   | 100 ms|
        | 5             | 200 ms|
        */
    noteDistance.setRangingMode(RangingMode.Short,0.5);
    }

    public double getNoteDistance() {
        /* Gets the distance from the sensor to the nearest edge of the note*/
        return noteDistance.getRange(); // return NoteDistance.
    }

    public boolean isNoteLoaded() {
        /* Returns true if the note is loaded, false if not */
        return noteDistance.getRange() < noteDistanceCheck ; // return NoteDistance.
    }

    public void setLEDColor() {
        /* Set the LED color based on the note position.
        * Requires 'isNoteLoadeded' value and two led methods */
        if (isNoteLoaded() == true) {
            // SetColor decide loaded color
             
        } else if (isNoteLoaded() == false) {
            //SetColor decide UnLoaded color
            
        }
    }
    
    public void periodic() {
        // This method will be called once per scheduler run
        // It did not seem to be called on the dashboard until I added a Command to the RobotContainer, but it would display updated data even when the button was not pressed.
        SmartDashboard.putNumber("Note Distance", noteDistance.getRange());
        SmartDashboard.putBoolean("Note Loaded", isNoteLoaded());
    }

} // end of class NoteSensorSubsystem
