package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants;

public class ShooterSubsystem2 {
    
    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor; // Right
    private TalonFX m_secondaryMotor; // Left

    // Class member variables
    private VelocityVoltage m_primaryVelocity = new VelocityVoltage(0);
    private VelocityVoltage m_secondaryVelocity = new VelocityVoltage(0);

    int shooterVelocity = 60;

    public ShooterSubsystem2() {
        /* Verbose? Absolutely. Effective? I hope so */
        m_primaryMotor = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
        m_secondaryMotor = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        m_primaryMotor.setInverted(true);
        m_secondaryMotor.setInverted(false);

        /* Various Configs */
        var shooterConfigs0 = new Slot0Configs();        
            shooterConfigs0
                .withKP(0.11)   // TODO where did these come from anyway?
                .withKI(0.5)    //
                .withKS(0.0001) //
                .withKV(0.12);  //

        var shooterVelocityConfig = new VoltageConfigs();
            shooterVelocityConfig
                .withPeakForwardVoltage(8)  // FRC 2910 running 12
                .withPeakReverseVoltage(8); // Originally -8, with negative the "helper" text goes away
        
        var shooterCurrentConfigs = new CurrentLimitsConfigs();
            shooterCurrentConfigs
                .withStatorCurrentLimit(60) 
                .withStatorCurrentLimitEnable(true);

        var shooterMotorOutputConfigs = new MotorOutputConfigs();
            shooterMotorOutputConfigs
                .withNeutralMode(NeutralModeValue.Coast);
                
        /* Apply Configs */
        m_primaryMotor.getConfigurator().apply(shooterConfigs0);
        m_secondaryMotor.getConfigurator().apply(shooterConfigs0);
        m_primaryMotor.getConfigurator().apply(shooterVelocityConfig);
        m_secondaryMotor.getConfigurator().apply(shooterVelocityConfig);
        m_primaryMotor.getConfigurator().apply(shooterCurrentConfigs);
        m_secondaryMotor.getConfigurator().apply(shooterCurrentConfigs);
        m_primaryMotor.getConfigurator().apply(shooterMotorOutputConfigs);
        m_secondaryMotor.getConfigurator().apply(shooterMotorOutputConfigs);
        
    } // end of constructor

    /* Sets the velocity the motors are using */
    public void setShooterVelocity(int shooterVelocity) {
        m_primaryMotor.setControl(m_primaryVelocity.withVelocity(shooterVelocity));
        m_secondaryMotor.setControl(m_secondaryVelocity.withVelocity(shooterVelocity * 0.95));
    }

} // end of class ShooterSubsystem2
