package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
// import com.playingwithfusion.TimeOfFlight;
// import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Using a ToF sensor and a single motor
 */
public class IntakeSubsystem extends SubsystemBase {

    private TalonFX motor;
    private DutyCycleOut dutyCycle;
    // The distance at which the tof thinks we have cargo

    public IntakeSubsystem() {

        motor = new TalonFX(Constants.CANIDs.INTAKE_ID);

        dutyCycle = new DutyCycleOut(0);
        motor.setControl(dutyCycle);
        // motor.setInverted(false);
    }

    public void Run(double input) {

        motor.setControl(dutyCycle.withOutput(input));
    }

}
