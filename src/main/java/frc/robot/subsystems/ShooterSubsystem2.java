package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.util.Constants;

// @SuppressWarnings("unused")

public class ShooterSubsystem2 extends SubsystemBase{

    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID, "rio"); // Right
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID, "rio"); // Left

    // Class member variables
    private VelocityVoltage m_velocityVoltage = new VelocityVoltage(0);

    public ShooterSubsystem2() {
        /* Verbose? Absolutely. Effective? I hope so */

        m_primaryMotor.setControl(m_velocityVoltage);
        m_secondaryMotor.setControl(m_velocityVoltage);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        /* Various Configs */
        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
                // https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
                .withKP(0.15)   // TODO Needs tuning
                .withKI(0.0)    // TODO Needs tuning
                .withKS(0.000) // TODO Needs tuning
                .withKV(0.0);  // TODO Needs tuning

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

    } // end of constructor

    public void setFlywheelVelocity(double velocity) {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(velocity));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(velocity));
        }
    
    public StatusSignal<Double> getFlywheelVelocity() {
        return m_primaryMotor.getVelocity();
    }
 
    public boolean isFlywheelNominal() {
        // double setVelocity = Constants.ShooterConstants.SHOOTER_VELOCITY;
        if (m_primaryMotor.getVelocity().getValue() >= (Math.abs(Constants.ShooterConstants.SHOOTER_VELOCITY - (Constants.ShooterConstants.VELOCITY_ERROR_MARGIN)))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void periodic() {
        // super.periodic(); // Suggested by VSCode
        SmartDashboard.putBoolean("Flywheel Nominal", isFlywheelNominal());
        // SmartDashboard.putNumber("Flywheel Velocity", m_primaryMotor.getVelocity());
    }

} // end of class ShooterSubsystem2
