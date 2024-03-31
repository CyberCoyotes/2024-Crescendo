package frc.robot.util;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class FlywheelConfigs {
    
    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.BOTTOM_FLYWHEEL_ID, "rio"); // Right
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.TOP_FLYWHEEL_ID, "rio"); // Left

    // Class member variables
    private VelocityVoltage m_velocityVoltage = new VelocityVoltage(0);

    // private double currentFlywheelVel = m_primaryMotor.getVelocity().getValue();

    public FlywheelConfigs() {
        /* Verbose? Absolutely. Effective? I hope so */
    
        m_primaryMotor.setControl(m_velocityVoltage);
        m_secondaryMotor.setControl(m_velocityVoltage);

       //  m_primaryMotor.setNeutralMode(coast);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
                .withKP(0.10) // <-
                .withKI(0.00)
                .withKS(0.00) // Should low for a light flywheel? Maybe the pulley strength would impact it though?
                .withKV(0.005); // <-
        
        var flywheelConfigs1 = new Slot1Configs();        
        flywheelConfigs1
            .withKP(0.00) // <-
            .withKI(0.00)
            .withKS(0.00) // Should low for a light flywheel? Maybe the pulley strength would impact it though?
            .withKV(0.00); // <-
    
        var flywheelConfigs2 = new Slot2Configs();        
        flywheelConfigs2
            .withKP(0.00) // <-
            .withKI(0.00)
            .withKS(0.00) // Should low for a light flywheel? Maybe the pulley strength would impact it though?
            .withKV(0.00); // <-
        
        var flywheelVelocityConfig = new VoltageConfigs();

            flywheelVelocityConfig
            .withPeakForwardVoltage(12)
            .withPeakReverseVoltage(-12); 
                
        var flywheelCurrentConfigs = new CurrentLimitsConfigs();
        flywheelCurrentConfigs
                .withStatorCurrentLimit(60) 
                .withStatorCurrentLimitEnable(true);

        /* TODO Test neutral mode with the class being called in Configs file */
        /*
        var flywhevelMotorOutput = new MotorOutputConfigs();
        flywheelMotorOutput
                .withNeutralMode(NeutralModeValue.Coast);
        */

        /* Apply Configs */
        m_primaryMotor.getConfigurator().apply(flywheelConfigs0);
        m_secondaryMotor.getConfigurator().apply(flywheelConfigs0);
       
        m_primaryMotor.getConfigurator().apply(flywheelVelocityConfig);
        m_secondaryMotor.getConfigurator().apply(flywheelVelocityConfig);
       
        m_primaryMotor.getConfigurator().apply(flywheelCurrentConfigs);
        m_secondaryMotor.getConfigurator().apply(flywheelCurrentConfigs);
       
        // m_primaryMotor.getConfigurator().apply(flywheelMotorOutput);
        // m_secondaryMotor.getConfigurator().apply(flywheelMotorOutput);

        m_primaryMotor.setInverted(true);
        m_secondaryMotor.setInverted(true); 
        
    }
}
