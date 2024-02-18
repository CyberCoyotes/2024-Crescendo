package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Serves as a base for any flywheel system driven by 2 motors. Fire and forget,
 * enabling and diabling will be done by singular calls.
 * Set configs before creating this class/subsystem.
 * {@link #SetStatePower} is used to set the main power.
 */
public class ShooterSubsystem extends SubsystemBase {
    /** As opposed to double */
    private boolean singleMotor;
    // The max rpm of the motor for shooting cargo; realistically 5900.
    private int maxVelocity = 5900;
    private TalonFX m_main;
    private TalonFX m_sub;
    /** The state percentage */
    private double maxPercentage = 1;
    // private double maxPower = 1;
    /** The multiplier that converts from "primary speed" to "secondary speed" */
    private double ratio = .95;
    private DutyCycleOut mainDutyCycle;
    private DutyCycleOut subDutyCycle;

    // #region Diagnostics
    GenericEntry isRunning;
    // #endregion

    /**
     * Serves as a base for any flywheel system driven by 2 motors. Fire and forget,
     * enabling and diabling will be done by singular calls.
     * Set configs before creating this class/subsystem.
     * {@link #SetStatePower} is used to set the main power.
     */
    public ShooterSubsystem() {

        singleMotor = false;

        m_main = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
        m_sub = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID);
        m_main.setInverted(true);
        m_sub.setInverted(false);
        m_main.setNeutralMode(NeutralModeValue.Coast);
        m_sub.setNeutralMode(NeutralModeValue.Coast);

        // default to off
        mainDutyCycle = new DutyCycleOut(0);
        subDutyCycle = new DutyCycleOut(0);
        m_main.setControl(mainDutyCycle);
        m_sub.setControl(subDutyCycle);

    }

    /**
     * Serves as a base for any flywheel system driven by 1 motor. Fire and forget,
     * enabling and diabling will be done by singular calls.
     * Set configs before creating this class/subsystem.
     * {@link #SetStatePower} is used to set the main power.
     */
    public ShooterSubsystem(TalonFX only) {

        this.m_main = only;
        singleMotor = true;
        // default to off
        m_main.setControl(mainDutyCycle = new DutyCycleOut(0));
    }

    /**
     * Sets the power the primary motor will use. Does not enable or disable.
     */
    public void SetOutput(double arg) {

        int invertMulti = 1;

        mainDutyCycle.Output = arg * invertMulti;
        m_main.setControl(mainDutyCycle);

        if (!singleMotor) {
            subDutyCycle.Output = arg * ratio * invertMulti;
            m_sub.setControl(subDutyCycle);
        }
        ;

    }

    public void SetMaxOutput(double percent) {
        // percent = Math.max(0, Math.min(percent,1));
        this.maxPercentage = percent;

    }

    public void Enable() {
        SetOutput(maxPercentage);
    }

    public void Disable() {
        SetOutput(0);
    }

    public void Toggle() {
        double set = Math.abs(mainDutyCycle.Output - maxPercentage);
        SetOutput(set);
    }

    public boolean Running() {
        return mainDutyCycle.Output > 0;
    }

    @Override
    public void periodic() {
        // System.out.println(this.Running() + "; " + maxPercentage);

    }

    public Command RunShooter(double input)

    {
        return this.run(() -> SetOutput(input));

    }

    /**
     * 
     * @return The velocity of the motor
     */
    public double GetVelocity() {
        return m_main.getVelocity().getValueAsDouble();
    }

    public boolean IsVelocityReqMet() {
        return GetVelocity() / maxVelocity >= 0.8;
    }
}
