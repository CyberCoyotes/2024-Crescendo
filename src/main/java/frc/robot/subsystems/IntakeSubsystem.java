package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Using a ToF sensor and a single motor
 */
public class IntakeSubsystem extends SubsystemBase {

    private TalonFX motor;
    private TimeOfFlight sensor;
    private DutyCycleOut dutyCycle;
    // The distance at which the tof thinks we have cargo
    private final double tofCutoff = 50; // 5cm

    public IntakeSubsystem() {

        motor = new TalonFX(0);
        dutyCycle = new DutyCycleOut(0);
        motor.setControl(dutyCycle);

        sensor = new TimeOfFlight(Constants.CANIDs.timeOfFlightID);
        // Every 20ms it updates ()
        sensor.setRangingMode(RangingMode.Short, 1);
    }

    public void Run(DoubleSupplier supplier) {

        motor.setControl(dutyCycle.withOutput(supplier.getAsDouble()));
    }

    public boolean HasCargo() {
        return sensor.getRange() < tofCutoff;
    }
}
