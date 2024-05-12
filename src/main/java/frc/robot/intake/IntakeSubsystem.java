package frc.robot.intake;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.intake.IntakeConstants;;

/**
 * Using a ToF sensor and a single motor
 */
public class IntakeSubsystem extends SubsystemBase {

    private TalonFX motor;
    private DutyCycleOut dutyCycle;

    public IntakeSubsystem() {

        motor = new TalonFX(IntakeConstants.INTAKE_ID);

        dutyCycle = new DutyCycleOut(0);
        motor.setControl(dutyCycle);
        // motor.setInverted(false);
    }

    public void powerIntake(double input) {

        motor.setControl(dutyCycle.withOutput(input));
    }

    public void stopIntake() {
        motor.setControl(dutyCycle.withOutput(0));
    }

}
