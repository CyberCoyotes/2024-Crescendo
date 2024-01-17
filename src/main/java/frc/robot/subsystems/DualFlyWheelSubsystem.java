package frc.robot.subsystems;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Serves as a base for any flywheel system driven by 2 motors. Fire and forget, enabling and diabling will be done by singular calls.\
 * Set configs before creating this class/subsystem.
 * {@link #SetStatePower} is used to set the main power.
 */
public class DualFlyWheelSubsystem extends SubsystemBase {
    private TalonFX m_main;
    private TalonFX m_sub;
    /**The state percentage */
    private double percentage;
    /**The multiplier that converts from "primary speed" to "secondary speed"*/
    private double ratio = 1;
    private DutyCycleOut mainDutyCycle;
    private DutyCycleOut subDutyCycle;

    public DualFlyWheelSubsystem(TalonFX main, TalonFX sub) {
        this.m_main = main;
        this.m_sub = sub;
        //default to off
        m_main.setControl(mainDutyCycle = new DutyCycleOut(0));
        m_sub.setControl(subDutyCycle = new DutyCycleOut(0)); 
    }
    /**
     * Sets the power the primary motor will use. Does not enable or disable.
     */
    private void SetMotorPowers(double arg)
    {
        mainDutyCycle.Output = arg;
        subDutyCycle.Output = -arg * ratio;
        
        m_main.setControl(mainDutyCycle);
        m_sub.setControl(subDutyCycle);
    }
    /**
     *Sets the multiplier that converts from "primary speed" to "secondary speed". Inversion is automatic; supplying a value of -1 here will make both motors identical.
     */
    public void SetRatio(double arg)
    {
       ratio = arg;
    }
    public void SetStatePower(double percent)
    {
        // percent = Math.max(0, Math.min(percent,1));
        this.percentage = percent;
        mainDutyCycle = new DutyCycleOut(percentage);
    }
    public void Enable()
    {
       SetMotorPowers(percentage);
    }
    public void Disable()
    {
       SetMotorPowers(0);
    }
    public void Toggle()
    {
        double set =  Math.abs( mainDutyCycle.Output - percentage);
       SetMotorPowers(set);
    }
}
