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
    private NoteSensorSubsystem notey;
    private DutyCycleOut dutyCycle;
    // The distance at which the tof thinks we have cargo

    public IntakeSubsystem() {

        motor = new TalonFX(0);
        dutyCycle = new DutyCycleOut(0);
        motor.setControl(dutyCycle);

        notey.noteDistanceCheck = 3;
        notey = new NoteSensorSubsystem();
        // Every 20ms it updates ()

    }

    public void Run(DoubleSupplier supplier) {

        motor.setControl(dutyCycle.withOutput(supplier.getAsDouble()));
    }

    public boolean HasCargo() {
        return notey.isNoteLoaded();
    }
}
