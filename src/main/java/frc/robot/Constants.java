// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;

public final class Constants {

  public static class OperatorConstants {
    public static final int K_DRIVER_CONTROLLER_PORT = 0; // Driver Controller
    public static final int K_OPERATOR_CONTROLLER_PORT = 1; // Operator or Secondary Controller
    public static final int DEFAULT_ARM_INCREMENT_VALUE = 20; // ?

  }

  public static class ArmConstants {

    /* Arm has been set to invert so positive values for arm poses */
    public static final double DEG_TO_ARM_NATIVE = 0.93;// -83.7 / 90
    public static final double ARM_NATIVE_TO_DEG = 1 / DEG_TO_ARM_NATIVE;
    public static final double ARM_MAX_DUTY_CYCLE_OUT = 0.6;
    public static final double ARM_SUPPLY_CURRENT_LIMIT = 5;
    public static final double ARM_STATOR_CURRENT_LIMIT = 20;

    /* Arm poses */
    public static final int ARM_REV_LIMIT = 0;
    public static final double ARM_HOME_POSE = 0;
    public static final double ARM_LOW_POSE = 10;
    public static final double ARM_MID_POSE = 20;
    public static final double ARM_HIGH_POSE = 55;
    public static final double ARM_AMP_POSE = 90;
    public static final int ARM_FWD_LIMIT = 100; // Previous event 91

    public static final int ARM_MAX_ACCEL = 100;
    public static final int ARM_MAX_VEL = 200;
    public static final int ARM_JERK = 0;

    public static final int ARM_STATOR_LIMIT = 15;
    public static final int ARM_SUPPLY_LIMIT = 15;
    public static final double ARM_ROTATION_LIMIT_NATIVE = 0;

    public static final double ARM_MANUAL_POWER = 6; // for Voltage control

    public static final class ConstantsPlus {

      static final Slot0Configs armGains0 = new Slot0Configs().withGravityType(GravityTypeValue.Arm_Cosine)
          .withKP(0.50)
          .withKI(0.00)
          .withKD(0.00)
          .withKV(0.00)
          .withKS(0.00)
          .withKA(0.00)
          .withKG(0.00);
      static final Slot1Configs armGains1 = new Slot1Configs().withGravityType(GravityTypeValue.Arm_Cosine)
          .withKP(0.50)
          .withKI(0.00)
          .withKD(0.00)
          .withKV(0.00)
          .withKS(0.00)
          .withKA(0.00)
          .withKG(0.00);

      /* Gains or configuration of arm motor for config slot 1 */

      // set Motion Magic settings
      static final MotionMagicConfigs armMotionMagic0 = new MotionMagicConfigs()
          .withMotionMagicCruiseVelocity(Constants.ArmConstants.ARM_MAX_VEL) // 80 rps cruise velocity //
                                                                             // FIMXE changed for safety
                                                                             // testing
                                                                             // 160 rps/s acceleration
                                                                             // (0.5 seconds) // FIMXE
                                                                             // changed for safety
                                                                             // testing
          .withMotionMagicAcceleration(Constants.ArmConstants.ARM_MAX_ACCEL)
          .withMotionMagicJerk(Constants.ArmConstants.ARM_JERK);

      static final SoftwareLimitSwitchConfigs armSoftLimit0 = new SoftwareLimitSwitchConfigs()
          .withForwardSoftLimitEnable(true)
          .withForwardSoftLimitThreshold(Constants.ArmConstants.ARM_FWD_LIMIT)
          .withReverseSoftLimitEnable(true)
          .withReverseSoftLimitThreshold(Constants.ArmConstants.ARM_REV_LIMIT);
      static final CurrentLimitsConfigs armSoftLimit1 = new CurrentLimitsConfigs()

          .withStatorCurrentLimitEnable(true)
          .withStatorCurrentLimit(Constants.ArmConstants.ARM_STATOR_LIMIT)
          .withSupplyCurrentLimitEnable(true)
          .withSupplyCurrentLimit(Constants.ArmConstants.ARM_SUPPLY_LIMIT);

      public static final TalonFXConfiguration CONFIG = new TalonFXConfiguration()
          .withSlot0(armGains0)
          .withSlot1(armGains1)
          .withMotionMagic(armMotionMagic0).withSoftwareLimitSwitch(armSoftLimit0).withCurrentLimits(armSoftLimit1);

    }

  }

  public static class IndexConstants {
    public static final double INDEX_POWER = 1.0;
    public static final double INDEX_POWER_REV = -0.75;

  }

  public static class IntakeConstants {
    public static final double INTAKE_POWER = -0.75; // 0.75
    public static final double INTAKE_POWER_REV = 0.75;
    
  }

  public static class ShooterConstants {
    public static final double SHOOTER_VELOCITY = 50; // 60
    public static final double SHOOTER_VELOCITY_LONG_RANGE = 60; // 60
    public static final double SHOOTER_IDLE_VELOCITY = SHOOTER_VELOCITY * 0.30;
    public static final double VELOCITY_ERROR_MARGIN = SHOOTER_VELOCITY * 0.05;
  }

  public static class SystemConstants {

    public static final double MAX_SPEED = 6; // 6 meters per second desired top speed
    public static final double MAX_ANGULAR_RATE = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
  }

  public static class WinchConstants {
    public static final double WINCH_POWER = -0.25; // Adjust this constant as needed, 15% according to Ryker
    public static final double WINCH_POWER_BOOST = -0.35; // Adjust this constant as needed, 15% according to Ryker
  }
  
  public static class CANIDs {

    /*
     * CAN IDs
     * | Object | ID |
     * |------------ |----|
     * | Drive FL | 01 |
     * | Steer FL | 02 |
     * | Drive FR | 03 |
     * | Steer FR | 04 |
     * | Drive RL | 05 |
     * | Steer RL | 06 |
     * | Drive RR | 07 |
     * | Steer RR | 08 |
     * | CANCoder FL | 09 |
     * | CANCoder FR | 10 |
     * | CANCoder RL | 11 |
     * | CANCoder RR | 12 |
     * | -Hard pass- | 13 |
     * | Pidgeon | 14 |
     * | Candle | 15 |
     * 
     * /*
     * Season Specific
     * | Object | ID |
     * |------------ |----|
     * | Intake | 20 |
     * | Index | 21 |
     * | Arm | 22 |
     * | Launcher LT | 23 |
     * | Launcher RT | 24 |
     * | Winch | 25 |
     * | ToF Note | 42 |
     * 
     */

    /* Taz 4: L3 Gear Ratio */

    public static final int CANDLE_ID = 15; // Mini LED and LED strip controller
    public static final int INTAKE_ID = 20;
    public static final int INDEX_ID = 21;
    public static final int ARM_ID = 22;
    public static final int LEFT_FLYWHEEL_ID = 23;
    public static final int RIGHT_FLYWHEEL_ID = 24;
    public static final int WINCH_ID = 25;
    public static final int NOTE_SENSOR_ID = 42; // Time of Flight sensor for the note

  }
}