package frc.robot.util;

import java.util.Optional;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/* Subsystem class to primarily use a Time of Flight sensor from 'Playing with Fusion'.
 * It will read the distance from the sensor to the 'note' and determine if the note is in a load position.
 * It should return an actual distance reading to and a boolean for a method 'isNoteLoaded'
 * This sensor data will change LED status and enable/disable intake and index motors
 */

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NoteSensor extends SubsystemBase {

    /* Set ID in web interface http://172.22.11.2:5812/ */
    private TimeOfFlight noteDistance = new TimeOfFlight(Constants.NOTE_SENSOR_ID);

    /*
     * This line declares a private instance variable called `m_ledSubsystem` of
     * type `LedSubsystem`
     * It is initialized with a new instance of the `LedSubsystem` class
     * It is needed to control the LEDs based on the method `isNoteLoaded()`
     */
    private LEDs m_ledSubsystem = new LEDs();
     /*
     * Set the distance to the note to be considered load position.'
     * Measure in (mm) to determine an appropriate value.
     */
    public int noteDistanceCheck = 375;

    /* Constructor */
    public NoteSensor() {
        /*
         * Initialize the sensor, and '.setRangingMode(RangingMode.Short)' foDr this
         * usage.
         *
         * | Sample value | Time |
         * |---------------|--------|
         * | 1 | 20 ms |
         * | 2 | 33 ms |
         * | 3 | 50 ms |
         * | 4 (default) | 100 ms |
         * | 5 | 200 ms |

         *****************************/
        // The refresh time at Lake City was 1, i.e. 20ms
        noteDistance.setRangingMode(RangingMode.Short, 1);
    }

    public double getNoteDistance() {
        /* Gets the distance from the sensor to the nearest edge of the note */
        return noteDistance.getRange(); // return NoteDistance.
    }

    public boolean isNoteLoaded() {
        /* Returns true if the note is loaded, false if not */
        return noteDistance.getRange() < noteDistanceCheck; // return NoteDistance.
    }

     public void setLEDColor() {
        /*
         * Set the LED color based on the note position.
         * Requires 'isNoteLoadeded' value and two led methods
         */
        Optional<Alliance> AllianceColor = DriverStation.getAlliance();
        if (isNoteLoaded() == true) {
            // Decided loaded color = green

            m_ledSubsystem.ColorGreen();

        } else if (isNoteLoaded() == false && AllianceColor.get() == Alliance.Red) {
        //  m_ledSubsystem.ColorFlowRed();
         m_ledSubsystem.ColorRed();
            
           
        } else if(isNoteLoaded() == false && AllianceColor.get() == Alliance.Blue) {
            // m_ledSubsystem.ColorFlowBlue(); 
            m_ledSubsystem.ColorBlue(); 

        }
        
    }

    public void periodic() {
        // This method will be called once per scheduler run
        // It did not seem to be called on the dashboard until I added a Command to the
        // RobotContainer, but it would display updated data even when the button was
        // not pressed.
           
        // ! Can be achieved with Shuffleboard call in
        // ! Constructor, per 

        // setLEDColor(); // TODO remove temporarily for testing
    

        // Only needed for diagnostics
        // Shuffleboard.getTab("Note").add("Note Distance", noteDistance.getRange());
        // This is not working as expected. Code crashes saying .add title is already in use, probably because it's being called periodically.
        // Shuffleboard.getTab("Sensors").add("Note Loaded 2", isNoteLoaded());

    }
}
// end of class NoteSensorSubsystem