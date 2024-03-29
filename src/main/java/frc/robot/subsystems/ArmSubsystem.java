package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;

public class ArmSubsystem extends SubsystemBase {

    // Declare a variable for the motor you want to control
    private final TalonFX m_arm;

    // class member variable
    final MotionMagicVoltage m_armPose = new MotionMagicVoltage(0); // Initializes to position 0

    public ArmSubsystem() {

        // Initialize the motor in the constructor with the motor ID and optional canbus
        // ID
        m_arm = new TalonFX(Constants.CANIDs.ARM_ID);

        /*
         * any unmodified configs in a configuration object are *automatically*
         * factory-defaulted;
         * user can perform a full factory default by passing a new device configuration
         * object.
         * This line should do a full factory reset...?
         */
        m_arm.getConfigurator().apply(new TalonFXConfiguration());

        /* Gains or configuration of arm motor for config slot 0 */
        var armGains0 = new Slot0Configs();
        armGains0.GravityType = GravityTypeValue.Arm_Cosine; /* .Elevator_Static | .Arm_Cosine */
        m_arm.setInverted(true); // Set to true if you want to invert the motor direction
        armGains0.kP = 0.50; /* Proportional Gain */
        armGains0.kI = 0.00; /* Integral Gain */
        armGains0.kD = 0.00; /* Derivative Gain */
        armGains0.kV = 0.00; /* Velocity Feed Forward Gain */
        armGains0.kS = 0.00; /*
                              * Static Feed Forward Gain // NOTE, not MY notes Approximately 0.25V to get the
                              * mechanism moving
                              */
        armGains0.kA = 0.00; /* Acceleration Feedforward */
        armGains0.kG = 0.00; /* Gravity Feedfoward */

        // set Motion Magic settings
        var armMotionMagic0 = new MotionMagicConfigs();
        armMotionMagic0.MotionMagicCruiseVelocity = Constants.ArmConstants.ARM_MAX_VEL; 
        armMotionMagic0.MotionMagicAcceleration = Constants.ArmConstants.ARM_MAX_ACCEL; 
        armMotionMagic0.MotionMagicJerk = Constants.ArmConstants.ARM_JERK;

        var armSoftLimit0 = new SoftwareLimitSwitchConfigs();
        armSoftLimit0.ForwardSoftLimitEnable = true;
        armSoftLimit0.ForwardSoftLimitThreshold = Constants.ArmConstants.ARM_FWD_LIMIT;
        armSoftLimit0.ReverseSoftLimitEnable = true;
        armSoftLimit0.ReverseSoftLimitThreshold = Constants.ArmConstants.ARM_REV_LIMIT;

        var armCurrent0 = new CurrentLimitsConfigs();
        armCurrent0.StatorCurrentLimitEnable = true;
        armCurrent0.StatorCurrentLimit = Constants.ArmConstants.ARM_STATOR_LIMIT;
        armCurrent0.SupplyCurrentLimitEnable = true;
        armCurrent0.SupplyCurrentLimit = Constants.ArmConstants.ARM_SUPPLY_LIMIT;

        /*
         * Long form (better for my learning): Applies gains with an optional 50 ms
         * timeout (I think)
         */
        m_arm.getConfigurator().apply(armGains0, 0.050);
        m_arm.getConfigurator().apply(armMotionMagic0, 0.050);
        m_arm.getConfigurator().apply(armSoftLimit0, 0.050);
        m_arm.getConfigurator().apply(armCurrent0, 0.050);

        /*
         * Send info about the arm to the Shuffleboard
         * Defaults to percent output
         * Only needed for diagnostics
         */
        // Shuffleboard.getTab("Arm").add("Arm Output", m_arm);

    } /* End of the class-method */

    public StatusSignal<Double> getArmPos() {
        /* Reusing from drivetrain subsystem */
        return m_arm.getPosition();
    }

    public void setArmPose(double armPose) {
        m_arm.setControl(m_armPose.withPosition(armPose));
        
        // less effecient approach, shouldn't use this
        // m_arm.setControl(new MotionMagicVoltage(armPose));

        // showArmTelemetry();
            
    }

    // public void setArmForWinch(double armPose, Slot1Configs armGains1) {

        // PositionVoltage armPose = new PositionVoltage(armPose);
    public void setArmForClimb(double power) {
        m_arm.setControl(new VoltageOut (power));
        // showArmTelemetry();


    }
    /* 
    public void showArmTelemetry() {

        Shuffleboard.getTab("Arm").add("Arm Position", m_arm.getPosition().getValue());
        Shuffleboard.getTab("Arm").add("Arm Stator", m_arm.getStatorCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Supply", m_arm.getSupplyCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Voltage", m_arm.getMotorVoltage().getValue());
    }
    */

    public void stopArm(double power) {
        m_arm.setControl(new VoltageOut(0));

    }

    @Override
    public void periodic() {
        /* Only needed for diagnostics */
        /*
        Shuffleboard.getTab("Arm").add("Arm Position", m_arm.getPosition().getValue());
        Shuffleboard.getTab("Arm").add("Arm Stator", m_arm.getStatorCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Supply", m_arm.getSupplyCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Voltage", m_arm.getMotorVoltage().getValue());
         */
    }

} // end of ArmSubsystem method

/***************************
 * NOTES
 ***************************/
/*
 * TalonFXConfiguration configuration = new TalonFXConfiguration();
 * configuration.motionCruiseVelocity = FAST_MOTION_CONSTRAINTS.maxVelocity /
 * SENSOR_VELOCITY_COEFFICIENT;
 * configuration.motionAcceleration = FAST_MOTION_CONSTRAINTS.maxAcceleration /
 * SENSOR_VELOCITY_COEFFICIENT;
 * configuration.slot0.kP = 0.25;
 * configuration.slot0.kI = 0.0;
 * configuration.slot0.kD = 0.0;
 * configuration.primaryPID.selectedFeedbackSensor =
 * TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
 * configuration.voltageCompSaturation = 12.0;
 */
/*
 * https://github.com/FRCTeam2910/2023CompetitionRobot-Public/blob/main/src/main
 * /java/org/frcteam2910/c2023/subsystems/arm/ArmIOFalcon500.java
 */

// REREFENCES
// https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/CommanddDrive/src/main/java/frc/robot/subsystems/DriveSubsystem.java
// See also
// https://github.com/FRCTeam2910/2023CompetitionRobot-Public/blob/main/src/main/java/org/frcteam2910/c2023/subsystems/intake/IntakeIOHardware.java