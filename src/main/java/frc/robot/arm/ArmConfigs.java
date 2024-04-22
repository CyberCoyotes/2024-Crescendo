package frc.robot.arm;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

public class ArmConfigs {

    public static void applyArmConfigs(TalonFX m_arm) {

        // Set the default configuration for the arm motor
        m_arm.getConfigurator().apply(new TalonFXConfiguration());

        m_arm.setInverted(true); // Set to true if you want to invert the motor direction

        /* Gains or configuration of arm motor for config slot 0 */
        var armGains0 = new Slot0Configs();
        armGains0.GravityType = GravityTypeValue.Arm_Cosine; /* .Elevator_Static | .Arm_Cosine */
        armGains0.kP = 0.50; /* Proportional Gain */
        armGains0.kI = 0.00; /* Integral Gain */
        armGains0.kD = 0.00; /* Derivative Gain */
        armGains0.kV = 0.00; /* Velocity Feed Forward Gain */
        armGains0.kS = 0.00; /* Static Feed Forward Gain */
        armGains0.kA = 0.00; /* Acceleration Feedforward */
        armGains0.kG = 0.00; /* Gravity Feedfoward */

        // set Motion Magic settings
        var armMotionMagic0 = new MotionMagicConfigs();
        armMotionMagic0.MotionMagicCruiseVelocity = ArmConstants.ARM_MAX_VEL; 
        armMotionMagic0.MotionMagicAcceleration = ArmConstants.ARM_MAX_ACCEL; 
        armMotionMagic0.MotionMagicJerk = ArmConstants.ARM_JERK;

        var armSoftLimit0 = new SoftwareLimitSwitchConfigs();
        armSoftLimit0.ForwardSoftLimitEnable = true;
        armSoftLimit0.ForwardSoftLimitThreshold = ArmConstants.ARM_FWD_LIMIT;
        armSoftLimit0.ReverseSoftLimitEnable = true;
        armSoftLimit0.ReverseSoftLimitThreshold = ArmConstants.ARM_REV_LIMIT;

        var armCurrent0 = new CurrentLimitsConfigs();
        armCurrent0.StatorCurrentLimitEnable = true;
        armCurrent0.StatorCurrentLimit = ArmConstants.ARM_STATOR_LIMIT;
        armCurrent0.SupplyCurrentLimitEnable = true;
        armCurrent0.SupplyCurrentLimit = ArmConstants.ARM_SUPPLY_LIMIT;

        /* Apply Configs */
        m_arm.getConfigurator().apply(armGains0, 0.050);
        m_arm.getConfigurator().apply(armMotionMagic0, 0.050);
        m_arm.getConfigurator().apply(armSoftLimit0, 0.050);
        m_arm.getConfigurator().apply(armCurrent0, 0.050);
    }        
} // end of class ArmIO