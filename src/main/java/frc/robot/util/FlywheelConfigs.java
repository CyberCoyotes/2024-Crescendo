package frc.robot.util;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class FlywheelConfigs {
    
    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.BOTTOM_FLYWHEEL_ID, "rio"); // Right
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.TOP_FLYWHEEL_ID, "rio"); // Left

    // TODO Tune this value
    public final double FLYWHEEL_VELOCITY = 100; // rotations per second (rps)
    public final double FLYWHEEL_VELOCITY_SHORT_RANGE = 40; // Not currently in use anywhere
    public final double FLYWHEEL_VELOCITY_LONG_RANGE = 60; // Not currently in use anywhere, Flywheel speed for long range shots only
    public final double FLYWHEEL_IDLE_VELOCITY = FLYWHEEL_VELOCITY * 0.30; // 30% of max speed
    public final double FLYWHEEL_MARGIN_ERROR = FLYWHEEL_VELOCITY * 0.10; // 5% of max speed
    public final double FLYWHEEL_MIN = FLYWHEEL_VELOCITY * .95;
    public final double FLYWHEEL_MAX = FLYWHEEL_VELOCITY * 1.05;

    // Class member variables
    private VelocityVoltage m_velocityVoltage = new VelocityVoltage(0);

    private double currentFlywheelVel = m_primaryMotor.getVelocity().getValue();

    public FlywheelConfigs() {
        /* Verbose? Absolutely. Effective? I hope so */

        m_primaryMotor.setControl(m_velocityVoltage);
        m_secondaryMotor.setControl(m_velocityVoltage);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
                .withKP(0.20)
                .withKI(0.00)
                .withKS(0.05)
                .withKV(0.00);

    
        var flywheelVelocityConfig = new VoltageConfigs();
            flywheelVelocityConfig
                .withPeakForwardVoltage(12)  // FRC 2910 running 12
                .withPeakReverseVoltage(12); // Originally -8, with negative the "helper" text goes away
                
        var flywheelCurrentConfigs = new CurrentLimitsConfigs();
        flywheelCurrentConfigs
                .withStatorCurrentLimit(60) 
                .withStatorCurrentLimitEnable(true);

        var flywheelMotorOutput = new MotorOutputConfigs();
        flywheelMotorOutput
                .withNeutralMode(NeutralModeValue.Coast);
                
        /* Apply Configs */
        m_primaryMotor.getConfigurator().apply(flywheelConfigs0);
        m_secondaryMotor.getConfigurator().apply(flywheelConfigs0);
       
        m_primaryMotor.getConfigurator().apply(flywheelVelocityConfig);
        m_secondaryMotor.getConfigurator().apply(flywheelVelocityConfig);
       
        m_primaryMotor.getConfigurator().apply(flywheelCurrentConfigs);
        m_secondaryMotor.getConfigurator().apply(flywheelCurrentConfigs);
       
        m_primaryMotor.getConfigurator().apply(flywheelMotorOutput);
        m_secondaryMotor.getConfigurator().apply(flywheelMotorOutput);

        m_primaryMotor.setInverted(true);
        m_secondaryMotor.setInverted(true);
    
    }
}
