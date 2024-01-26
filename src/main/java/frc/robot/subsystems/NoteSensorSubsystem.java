package frc.robot.subsystems;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/* Subsystem class to primarily use a Time of Flight sensor from 'Playing with Fusion'.
 * It will read the distance from the sensor to the 'note' and determine if the note is in a load position.
 * This sensor data will change LED status and enable/disable intake and index motors
 */
public class NoteSensorSubsystem extends SubsystemBase {
     /* Set ID in web interface http://172.22.11.2:5812/     */   
     private TimeOfFlight sensorNoteDistance = new TimeOfFlight(Constants.CANIDs.timeOfFlightID);

     /* Set the distance to the note to be considered 'in load position.'
     Measure in (mm) to determine an appropriate value.*/
     public int noteDistanceCheck = 0;

    /* Constructor */
    public NoteSensorSubsystem() {
        /* Initialize the sensor, and '.setRangingMode(RandingMode.short)' for this usage.
        *
        | Sample Time   | Time  |
        |-------------  |------ |
        | 1             | 20 ms |
        | 2             | 33 ms |
        | 3             | 50 ms |
        | 4 (default)   | 100 ms|
        | 5             | 200 ms|
        */
    }

    public double getNoteDistance() {
        /* Gets the distance from the sensor to the nearest edge of the note*/
        return 0.0; // return sensorNoteDistance.
    }

    public boolean isNoteLoaded() {
        /* Returns true if the note is loaded, false if not */
        return true; // return sensorNoteDistance.
    }

    public void setLEDColor() {
        /* Set the LED color based on the note position.
        * Requires 'isNoteLoadeded' value and two led methods */

    }

    
    public void periodic() {
        // This method will be called once per scheduler run
        // It did not seem to be called on the dashboard until I added a Command to the RobotContainer, but it would display updated data even when the button was not pressed.
        SmartDashboard.putNumber("Note Distance", sensorNoteDistance.getRange());
        SmartDashboard.putBoolean("Note Loaded", isNoteLoaded());
    }
        
} // end of class NoteSensorSubsystem
