package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

// @SuppressWarnings("unused")

public class ShooterSubsystem2 extends SubsystemBase{

    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID, "rio"); // Right
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID, "rio"); // Left

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
    // TODO Something to try
    private MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(0);

    private double currentFlywheelVel = m_primaryMotor.getVelocity().getValue();

    public ShooterSubsystem2() {
        /* Verbose? Absolutely. Effective? I hope so */

        m_primaryMotor.setControl(m_velocityVoltage);
        m_secondaryMotor.setControl(m_velocityVoltage);

        m_primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        m_secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        /* Various Configs */
        /*
        | Ks    |   output to overcome static friction (output)
        | Kv    |   output per unit of velocity (output/rps)
        | Ka    |   output per unit of acceleration; unused, as there is no target acceleration (output/(rps/s))
        | Kp    |   output per unit of error (output/rps)
        | Ki    |   output per unit of integrated error in velocity (output/rotation)
        | kd    |   output per unit of error derivative in velocity (output/(rps/s))
        */

        // https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java

        var flywheelConfigs0 = new Slot0Configs();        
            flywheelConfigs0
                .withKP(0.10)
                .withKI(0.00)
                .withKS(0.00) // Should low for a light flywheel? Maybe the pulley strength would impact it though?
                .withKV(0.00);

    
        var flywheelVelocityConfig = new VoltageConfigs();
            flywheelVelocityConfig
                .withPeakForwardVoltage(12)  
                .withPeakReverseVoltage(12);
                
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

    public void setFlywheelToIdle() {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(FLYWHEEL_IDLE_VELOCITY));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(FLYWHEEL_IDLE_VELOCITY));
    }
    public void stopFlywheel() {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(0));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(0));
    }

    /* A set of methods using MotionMagicVelocityVoltage */
    public void setFlywheelVelocityMM(double velocity) {
        m_primaryMotor.setControl(mmVelocityVoltage.withVelocity(velocity));
        m_secondaryMotor.setControl(mmVelocityVoltage.withVelocity(velocity));
    }
    public void setFlywheelToIdleMM() {
        m_primaryMotor.setControl(mmVelocityVoltage.withVelocity(FLYWHEEL_IDLE_VELOCITY));
        m_secondaryMotor.setControl(mmVelocityVoltage.withVelocity(FLYWHEEL_IDLE_VELOCITY));
    }
    
    
    // See also `var currentFlywheelVel = m_primaryMotor.getVelocity().getValue()` in the Constructor

    public StatusSignal<Double> getFlywheelVelocity() {
        return m_primaryMotor.getVelocity();
    }
 
    public boolean isFlywheelNominal() {
        // double setVelocity = Constants.ShooterConstants.SHOOTER_VELOCITY;
        if (getFlywheelVelocity().getValue() >= (Math.abs(FLYWHEEL_VELOCITY - (FLYWHEEL_MARGIN_ERROR)))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFlywheelNominal2() {
        // Method to check if the flywheel is at target velocity within a range of error of 3% of the target velocity
        double targetVelocity = FLYWHEEL_VELOCITY; // replace with your target velocity
        double currentVelocity = getFlywheelVelocity().getValue();
        double threePercentOfTarget = targetVelocity * 0.03;
        return Math.abs(currentVelocity - targetVelocity) <= threePercentOfTarget;
        }

    public boolean isFlywheelNominal3() { 
        if (getFlywheelVelocity().getValue() >= FLYWHEEL_MIN && getFlywheelVelocity().getValue() <= FLYWHEEL_MAX ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFlywheelNominal4() {
        if (currentFlywheelVel >= FLYWHEEL_MIN && getFlywheelVelocity().getValue() <= FLYWHEEL_MAX ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void periodic() {
        // super.periodic(); // Suggested by VSCode
        SmartDashboard.putBoolean("Flywheel 1.0 version", isFlywheelNominal());
        SmartDashboard.putBoolean("Flywheel 2.0 version", isFlywheelNominal2());
        SmartDashboard.putBoolean("Flywheel 3.0 version", isFlywheelNominal3());
        SmartDashboard.putBoolean("Flywheel 4.0 version", isFlywheelNominal4());

        // SmartDashboard.putNumber("Flywheel Velocity", m_primaryMotor.getVelocity());

        // acquire a refreshed TalonFX rotor position signal
        // var rotorPosSignal = m_talonFX.getRotorPosition();

        // because we are calling getRotorPosition() every loop,
        // we do not need to call refresh()
        //rotorPosSignal.refresh();

        // retrieve position value that we just refreshed
        // units are rotations
        // var rotorPos = rotorPosSignal.getValue();

        // get latency of the signal
        // var rotorPosLatency = rotorPosSignal.getTimestamp().getLatency();

        // synchronously wait 20 ms for new data
        // rotorPosSignal.waitForUpdate(0.020);

        // var currentFlywheelVel = m_primaryMotor.getVelocity().getValue();

        // var currentFlywheelVel2 = flywheelVel.getValue();


    }

} // end of class ShooterSubsystem2
