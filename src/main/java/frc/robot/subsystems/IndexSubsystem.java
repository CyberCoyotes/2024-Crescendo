package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*For indexing cargo. That is, taking cargo from a stored position and proceeding it to the shooter.*/
public class IndexSubsystem extends SubsystemBase {
    private TalonFX motor;

    private DutyCycleOut dutyCycle;

    public IndexSubsystem(TalonFX motor) {
        this.motor = motor;
        dutyCycle = new DutyCycleOut(0);

    }

    public void RunIndexing() {
        motor.setControl(dutyCycle.withOutput(1));
    }

    public void StopIndexing() {
        motor.setControl(dutyCycle.withOutput(0));
    }

}
