package frc.robot.climb;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

public class WinchSubsystem extends SubsystemBase {

    private TalonFX motor;
    private DutyCycleOut dutyCycle;

    public WinchSubsystem() {

        motor = new TalonFX(Constants.WINCH_ID);

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
