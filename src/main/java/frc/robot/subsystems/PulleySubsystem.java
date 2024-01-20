package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Made of a motor, connected to a pulley. Contains manual drive for now.
 */
public class PulleySubsystem extends SubsystemBase {
    private TalonFX m_motor;
    private DutyCycleOut controlRequest;

    public PulleySubsystem(TalonFX motor) {
        m_motor = motor;
        controlRequest = new DutyCycleOut(0);
        m_motor.setControl(controlRequest);
    }

    // Drives with continued input. 0 or 1. sign-sensitive.
    public void Drive(DoubleSupplier supplier) {
        controlRequest.Output = Math.round(supplier.getAsDouble());
    }
}
