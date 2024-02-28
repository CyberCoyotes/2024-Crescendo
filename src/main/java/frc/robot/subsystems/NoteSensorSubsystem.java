package frc.robot.subsystems;

/* Subsystem class to primarily use a Time of Flight sensor from 'Playing with Fusion'.
 * It will read the distance from the sensor to the 'note' and determine if the note is in a load position.
 * It should return an actual distance reading to the SmartDashboard and a boolean for a method 'isNoteLoaded'
 * This sensor data will change LED status and enable/disable intake and index motors
 */

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class NoteSensorSubsystem extends SubsystemBase {

    /* Set ID in web interface http://172.22.11.2:5812/ */
    private TimeOfFlight noteDistance = new TimeOfFlight(Constants.CANIDs.NOTE_SENSOR_ID);

    /*
     * This line declares a private instance variable called `m_ledSubsystem` of
     * type `LedSubsystem`
     * It is initialized with a new instance of the `LedSubsystem` class
     * It is needed to control the LEDs based on the method `isNoteLoaded()`
     */
    private LedSubsystem m_ledSubsystem = new LedSubsystem();

    /*
     * Set the distance to the note to be considered load position.'
     * Measure in (mm) to determine an appropriate value.
     */
    public int noteDistanceCheck = 375;

    /* Constructor */
    public NoteSensorSubsystem() {
        /*
         * Initialize the sensor, and '.setRangingMode(RangingMode.Short)' for this
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
        if (isNoteLoaded() == true) {
            // Decided loaded color = green

            m_ledSubsystem.ColorGreen();

        } else if (isNoteLoaded() == false) {
            // Decided loaded color = yellow
            m_ledSubsystem.ColorYellow();
        }
    }

    public void periodic() {

        setLEDColor();

    }
}
// end of class NoteSensorSubsystem