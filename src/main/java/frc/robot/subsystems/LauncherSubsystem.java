package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Serves as a base for any flywheel system driven by 2 motors. Fire and forget,
 * enabling and diabling will be done by singular calls.
 * Set configs before creating this class/subsystem.
 * {@link #SetStatePower} is used to set the main power.
 */
public class LauncherSubsystem extends SubsystemBase {
    /** As opposed to double */
    private boolean singleMotor;

    private TalonFX m_main; // The main motor
    private TalonFX m_secondary; // The secondary motor
    /** The state percentage */
    private double percentage;
    /** The multiplier that converts from "primary speed" to "secondary speed" */
    private double ratio = 1;
    private DutyCycleOut mainDutyCycle;
    private DutyCycleOut secondaryDutyCycle;

    // #region Diagnostics
    GenericEntry isRunning;
    // #endregion

    /**
     * Serves as a base for any flywheel system driven by 2 motors. Fire and forget,
     * enabling and diabling will be done by singular calls.
     * Set configs before creating this class/subsystem.
     * {@link #SetStatePower} is used to set the main power.
     */
    public LauncherSubsystem(TalonFX main, TalonFX secondary) {

        singleMotor = false;
        this.m_main = main;
        this.m_secondary = secondary;
        // default to off
        m_main.setControl(mainDutyCycle = new DutyCycleOut(0));
        m_secondary.setControl(secondaryDutyCycle = new DutyCycleOut(0));

    }

    /**
     * Serves as a base for any flywheel system driven by 1 motor. Fire and forget,
     * enabling and diabling will be done by singular calls.
     * Set configs before creating this class/subsystem.
     * {@link #SetStatePower} is used to set the main power.
     */
    public LauncherSubsystem(TalonFX only) {

        this.m_main = only;
        singleMotor = true;
        // default to off
        m_main.setControl(mainDutyCycle = new DutyCycleOut(0));
    }

    /**
     * Sets the power the primary motor will use. Does not enable or disable.
     */
    private void SetMotorPowers(double arg) {

        int invertMulti = 1;

        mainDutyCycle.Output = arg * invertMulti;
        m_main.setControl(mainDutyCycle);

        if (!singleMotor) {
            secondaryDutyCycle.Output = arg * ratio * invertMulti;
            m_secondary.setControl(secondaryDutyCycle);
        }
        ;

    }

    /**
     * Sets the multiplier that converts from "primary speed" to "secondary speed".
     * Inversion is automatic; supplying a value of -1 here will make both motors
     * identical.
     */
    public void SetRatio(double arg) {
        ratio = arg;

    }

    public void SetStatePower(double percent) {
        // percent = Math.max(0, Math.min(percent,1));
        this.percentage = percent;

    }

    public void Enable() {
        SetMotorPowers(percentage);
    }

    public void Disable() {
        SetMotorPowers(0);
    }

    public void Toggle() {
        double set = Math.abs(mainDutyCycle.Output - percentage);
        SetMotorPowers(set);
    }

    public boolean Running() {
        return mainDutyCycle.Output > 0;
    }

    @Override
    public void periodic() {
        System.out.println(this.Running());

    }

    public Command RunLauncher(double input)

    {
        return this.run(() -> SetStatePower(input));
    }
}
