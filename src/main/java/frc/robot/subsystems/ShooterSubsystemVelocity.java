package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Serves as a base for any flywheel system driven by 2 motors. Fire and forget,
 * enabling and diabling will be done by singular calls.
 * Set configs before creating this class/subsystem.
 * {@link #SetStatePower} is used to set the main power.
 */
public class ShooterSubsystemVelocity extends SubsystemBase {
    private static final double MaxVelo = 20;

    /** As opposed to double */
    private boolean singleMotor;
    // The max rpm of the motor for shooting cargo; realistically 5900.

    private TalonFX m_main;
    private TalonFX m_sub;
    /** The max velo */
    private double runningVoltage = 20;
    // private double maxPower = 1;
    /** The multiplier that converts from "primary speed" to "secondary speed" */
    private double ratio = .95;
    private VelocityVoltage mainVeloCycle;
    private VelocityVoltage subVeloCycle;

    // #region Diagnostics
    GenericEntry isRunning;
    // #endregion

    TalonFXConfiguration motorConfigs = new TalonFXConfiguration()
            .withSlot0(new Slot0Configs()
                    .withKP(0.11)
                    .withKI(0.5)
                    .withKS(0.0001)
                    .withKV(0.12))
            .withVoltage(new VoltageConfigs().withPeakForwardVoltage(8).withPeakReverseVoltage(-8))
            .withCurrentLimits(
                    new CurrentLimitsConfigs().withStatorCurrentLimit(60).withStatorCurrentLimitEnable(true))
            .withMotorOutput(new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Coast));

    // ! Peak output of 8 volts

    public ShooterSubsystemVelocity() {

        singleMotor = false;

        m_main = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
        m_sub = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID);
        m_main.getConfigurator().apply(motorConfigs);
        m_sub.getConfigurator().apply(motorConfigs);
        m_main.setInverted(true);
        m_sub.setInverted(false);
        m_main.setNeutralMode(NeutralModeValue.Coast);
        m_sub.setNeutralMode(NeutralModeValue.Coast);

        // default to off
        mainVeloCycle = new VelocityVoltage(0);
        subVeloCycle = new VelocityVoltage(0);
        m_main.setControl(mainVeloCycle);
        m_sub.setControl(subVeloCycle);

    }

    public ShooterSubsystemVelocity(TalonFX only) {

        this.m_main = only;
        singleMotor = true;
        // default to off
        m_main.setControl(mainVeloCycle = new VelocityVoltage(0));
    }

    /**
     * Sets the power the primary motor is using.
     */
    public void SetOutput(double arg) {

        int invertMulti = 1;

        mainVeloCycle.Velocity = arg * invertMulti;
        m_main.setControl(mainVeloCycle);

        if (!singleMotor) {
            subVeloCycle.Velocity = arg * ratio * invertMulti;
            m_sub.setControl(subVeloCycle);
        }
        ;

    }

    @Override
    public void periodic() {
        // System.out.println(m_main.getVelocity());
        SmartDashboard.putNumber("Velocity", GetVelocity());
    }

    public void SetMaxOutput(double velo) {
        // percent = Math.max(0, Math.min(percent,1));
        this.runningVoltage = velo;

    }

    public void Enable() {
        SetOutput(runningVoltage);
    }

    public void Disable() {
        SetOutput(0);

    }

    public void Toggle() {
        double set = Math.abs(mainVeloCycle.Velocity - runningVoltage);
        SetOutput(set);
    }

    public boolean Running() {
        return mainVeloCycle.Velocity > 0;
    }

    /**
     * 
     * @return The velocity of the motor
     */
    public double GetVelocity() {
        return m_main.getVelocity().getValueAsDouble();
    }

    public Boolean AtVelocity(double velo) {
        return m_main.getVelocity().getValueAsDouble() >= (velo - 0.5);
    }

}

/*
 * NOTES
 * 
 * // An error of 1 rotation per second results in 2V output
 * // An error of 1 rotation per second increases output by 0.5V every second
 * // A change of 1 rotation per second squared results in 0.01 volts output
 * // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12
 * // volts / Rotation per second
 * 
 */