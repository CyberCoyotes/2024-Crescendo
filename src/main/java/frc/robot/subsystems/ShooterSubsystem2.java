package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;
import frc.robot.util.FlywheelConfigs;
import frc.robot.util.ShooterConstants;

// @SuppressWarnings("unused")

public class ShooterSubsystem2 extends SubsystemBase{

    // Declare variables for the motors to be controlled
    private TalonFX m_primaryMotor = new TalonFX(Constants.CANIDs.BOTTOM_FLYWHEEL_ID, "rio"); // BOTTOM / RIGHT
    private TalonFX m_secondaryMotor = new TalonFX(Constants.CANIDs.TOP_FLYWHEEL_ID, "rio"); // TOP / LEFT

    /* 
    This is an instance of the `VelocityVoltage` class being created and assigned 
    to the `m_velocityVoltage` variable with parameters being passed to the `VelocityVoltage` constructor.
    */
    private VelocityVoltage m_velocityVoltage = new VelocityVoltage(
            0, // Velocity to drive toward in rotations per second
            0, // Acceleration to drive toward in rotations per second squared
            true, // EnableFOC
            0, // Feedforward to apply in volts
            0, // Slot Select which gains are applied by selecting the slot
            false, // OverrideBrakeDurNeutral Set to false to use the NeutralMode configuration setting
            false, // LimitForwardMotion
            false // LimitReverseMotion
        );

    // Motion Magic Vel Volt - something to try; does not work when tested 3/30, not essential
    private MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(
            0, // Velocity to drive toward in rotations per second
            0, // Acceleration to drive toward in rotations per second squared
            true, // EnableFOC
            0, // Feedforward to apply in volts
            1, // Slot Select which gains are applied by selecting the slot
            false, // OverrideBrakeDurNeutral Set to false to use the NeutralMode configuration setting
            false, // LimitForwardMotion
            false // LimitReverseMotion
        );

        // Torque Current - Something to try, not essential
        private TorqueCurrentFOC m_TorqueCurrent = new TorqueCurrentFOC(
            0, // Amount of motor current in Amperes
            0, /* MaxAbsDutyCycle 
            * Maximum absolute motor output that can be applied, which effectively limits the velocity.
            * For example, 0.50 means no more than 50% output in  either direction. 
            * This is useful for preventing the motor from spinning to its terminal velocity
            */
            0, // Deadband
            /* Set to true to coast the rotor when output is zero (or within deadband).
             * Set to false to use the NeutralMode configuration setting (default).
             */
            false, // OverrideCoastDurNeutral
            false, // LimitForwardMotion
            false // LimitReverseMotion
        );

    public ShooterSubsystem2() {

        // Apply configurations from the FlywheelConfigs file to the two motors
        FlywheelConfigs.applyFlywheelConfigs(m_primaryMotor, m_secondaryMotor);

        // Apply configurations from FlywheelConfig2 to motors; using FlywheelConfigs.java approach for now
        // FlywheelConfigs2.applyFlywheelConfigs(m_primaryMotor, m_secondaryMotor); // Second approach to applying configurations

    } // end of constructor
    
    public void setFlywheelVelocity(double velocity) {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(velocity));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(velocity));
    }

    public void setFlywheelVelocityAmp(double velocity) {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(velocity));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(velocity*0.80));
    }

    public void setFlywheelToIdle() {
        m_primaryMotor.setControl(m_velocityVoltage.withVelocity(ShooterConstants.FLYWHEEL_IDLE_VELOCITY));
        m_secondaryMotor.setControl(m_velocityVoltage.withVelocity(ShooterConstants.FLYWHEEL_IDLE_VELOCITY));
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
        m_primaryMotor.setControl(mmVelocityVoltage.withVelocity(ShooterConstants.FLYWHEEL_IDLE_VELOCITY));
        m_secondaryMotor.setControl(mmVelocityVoltage.withVelocity(ShooterConstants.FLYWHEEL_IDLE_VELOCITY));
    }
    
    public void setFlywheelTorqueCurrent(double amps) {
        m_primaryMotor.setControl(m_TorqueCurrent.withOutput(amps));
        m_secondaryMotor.setControl(m_TorqueCurrent.withOutput(amps));
    }
    
    public StatusSignal<Double> getFlywheelVelocity() {
        return m_primaryMotor.getVelocity();
    }
    
    public StatusSignal<Double> getFlywheelVelocitySecondary() {
        return m_primaryMotor.getVelocity();
    }

    public double getFlywheelVelocityAverage() {
        return (getFlywheelVelocity().getValue() + getFlywheelVelocitySecondary().getValue()) / 2;
    }

    /*
    public boolean isFlywheelNominal() {
        // double setVelocity = Constants.ShooterConstants.SHOOTER_VELOCITY;
        if (getFlywheelVelocity().getValue() >= (Math.abs(ShooterConstants.FLYWHEEL_VELOCITY - (ShooterConstants.FLYWHEEL_MARGIN_ERROR)))) {
            return true;
        } else {
            return false;
        }
    }
    */

    /*
    public boolean isFlywheelNominal2() {
        // Method to check if the flywheel is at target velocity within a range of error of 3% of the target velocity
        double targetVelocity = ShooterConstants.FLYWHEEL_VELOCITY; // replace with your target velocity
        double currentVelocity = getFlywheelVelocity().getValue();
        double threePercentOfTarget = targetVelocity * 0.03;
        return Math.abs(currentVelocity - targetVelocity) <= threePercentOfTarget;
        }
    */

    public boolean isFlywheelNominal() { 
        if (getFlywheelVelocity().getValue() >= ShooterConstants.FLYWHEEL_MIN && getFlywheelVelocity().getValue() <= ShooterConstants.FLYWHEEL_MAX ) {
            return true;
        } else {
            return false;
        }
    }

        public boolean isFlywheelNominalAmp() { 
        if (getFlywheelVelocity().getValue() >= (ShooterConstants.FLYWHEEL_AMP_MIN-0.5) && getFlywheelVelocity().getValue() <= ShooterConstants.FLYWHEEL_AMP_MAX) {
            return true;
        } else {
            return false;
        }
    }

    /*
    public boolean isFlywheelNominal4() {
        if (currentFlywheelVel >= ShooterConstants.FLYWHEEL_MIN && currentFlywheelVel <= ShooterConstants.FLYWHEEL_MAX ) {
            return true;
        } else {
            return false;
        }
    } 
    */

    @Override
    public void periodic() {
        // super.periodic(); // Suggested by VSCode
        // SmartDashboard.putBoolean("Flywheel 1.0 version", isFlywheelNominal());
        // SmartDashboard.putBoolean("Flywheel 2.0 version", isFlywheelNominal2());
        SmartDashboard.putBoolean("Flywheel 3.0 version", isFlywheelNominal());
        // SmartDashboard.putBoolean("Flywheel 4.0 version", isFlywheelNominal4());
        SmartDashboard.putNumber("RIGHT Flywheel Velocity", getFlywheelVelocity().getValue());
        SmartDashboard.putNumber("LEFT Flywheel Velocity", getFlywheelVelocitySecondary().getValue());
        SmartDashboard.putNumber("AVE Flywheel Velocity", getFlywheelVelocityAverage());

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