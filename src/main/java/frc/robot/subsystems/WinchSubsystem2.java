package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WinchSubsystem2 extends SubsystemBase {

    private TalonFX motor;
    private DutyCycleOut dutyCycle;

    public WinchSubsystem2() {

        motor = new TalonFX(Constants.CANIDs.WINCH_ID);

        dutyCycle = new DutyCycleOut(0);
        motor.setControl(dutyCycle);
        // motor.setInverted(false);
    }

    
    public void runWinch(double power) {

        motor.setControl(dutyCycle.withOutput(power));
    }

    public void stopWinch(double power) {

        motor.setControl(dutyCycle.withOutput(0));
    }
}
