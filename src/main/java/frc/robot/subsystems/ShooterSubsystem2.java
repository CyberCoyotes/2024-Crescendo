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

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")

/* Defining a new class named ShooterSubsystem2 in Java.
This class is extending SubsystemBase, which means ShooterSubsystem2 is a subclass of SubsystemBase. */
public class ShooterSubsystem2 extends SubsystemBase{
    private static final double FLYWHEEL_VELOCITY = 60;
    private static final double FLYWHEEL_IDLE_VELOCITY = 5;

    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID, "rio"); // Right
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID, "rio"); // Left

    // Class member variables
    private VelocityVoltage m_velocity = new VelocityVoltage(0);
    private double flywheelVelocity;
    private boolean flywheelDisabled = false;
    private double targetFlywheelSpeed;
    

    public ShooterSubsystem2() {
        /* Verbose? Absolutely. Effective? I hope so */
        m_primaryMotor = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
        m_secondaryMotor = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        m_primaryMotor.setInverted(false);
        m_secondaryMotor.setInverted(false);       

        /* Various Configs */
        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
                // https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java
                .withKP(0.11) 
                .withKI(0.5)
                .withKS(0.0001)
                .withKV(0.12);


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
        

    } // end of constructor

    /* Sets the velocity the motors are using */
    public void setTargetFlywheelVelocity(double flywheelVelocity) {
        this.flywheelVelocity = flywheelVelocity;
        // m_primaryMotor.setControl(m_primaryVelocity.withVelocity(flywheelVelocity));
//        m_secondaryMotor.setControl(m_secondaryVelocity.withVelocity(flywheelVelocity * 0.95));
    }
    
    public void setTargetFlywheelSpeed(double targetFlywheelSpeed) {
        this.targetFlywheelSpeed = targetFlywheelSpeed;
    }
    
    /* Returns the velocity of the flywheel */
    public double getTargetFlywheelSpeed(double targetFlywheelSpeed) {
        return targetFlywheelSpeed;
    }

    // Returns the velocity of the flywheel 
    public double getFlywheelVelocity() {
        return m_primaryMotor.getVelocity() ; // TODO check if this is the right method
    }

    public boolean isFlywheelAtTargetVelocity() {
        return Math.abs(getFlywheelVelocity() - targetFlywheelVelocity) < 5; // TODO arbitrary error for now 
    }

} // end of class ShooterSubsystem2
