package frc.robot.util;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

/* Verbose? Probably. Effective? I hope so */
public class FlywheelConfigs {

    public static void applyFlywheelConfigs(TalonFX primaryMotor, TalonFX secondaryMotor) {
        
        // TODO I think this is not needed because of control modes being set in the ShooterSubsystem2
        // VelocityVoltage m_velocityVoltage = new VelocityVoltage(0);
        // primaryMotor.setControl(m_velocityVoltage);
        // secondaryMotor.setControl(m_velocityVoltage);

       //  m_primaryMotor.setNeutralMode(coast);

        // any unmodified configs in a configuration object are *automatically* factory-defaulted;
        // user can perform a full factory default by passing a new device configuration object
        primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
            .withKP(0.10) // <-
            .withKI(0.00)
            .withKS(0.00)
            .withKV(0.004); // <-
        
        var flywheelConfigs1 = new Slot1Configs();        
        flywheelConfigs1
            .withKP(0.00) // <-
            .withKI(0.00)
            .withKS(0.00) 
            .withKV(0.00); // <-
        
        var flywheelVelocityConfig = new VoltageConfigs();

            flywheelVelocityConfig
            .withPeakForwardVoltage(12)
            .withPeakReverseVoltage(-12); 
                
        var flywheelCurrentConfigs = new CurrentLimitsConfigs();
        flywheelCurrentConfigs
            .withStatorCurrentLimit(60) 
            .withStatorCurrentLimitEnable(true);

        primaryMotor.setInverted(true);
        secondaryMotor.setInverted(true); 

        /* Apply Configs */
        primaryMotor.getConfigurator().apply(flywheelConfigs0, 0.050);
        primaryMotor.getConfigurator().apply(flywheelVelocityConfig, 0.050);
        primaryMotor.getConfigurator().apply(flywheelCurrentConfigs, 0.050);

        secondaryMotor.getConfigurator().apply(flywheelConfigs0, 0.050);
        secondaryMotor.getConfigurator().apply(flywheelVelocityConfig, 0.050);
        secondaryMotor.getConfigurator().apply(flywheelCurrentConfigs, 0.050);

    } // end of constructor

} // end of class
