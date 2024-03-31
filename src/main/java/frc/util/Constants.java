// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

<<<<<<< HEAD:src/main/java/frc/robot/util/Constants.java
package frc.robot.util;
=======
package frc.util;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
>>>>>>> event-ready:src/main/java/frc/util/Constants.java

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
    public static final double ARM_MID_POSE = 20; // 30 // 20
    public static final double ARM_HIGH_POSE = 55;
    public static final double ARM_AMP_POSE = 90;
    public static final double ARM_SAFETY_POSE = 20;
    public static final int ARM_FWD_LIMIT = 100; // Previous event 91

    /* Distances (inches) from front Bumper Speaker Base to front Bumper and arm in canon units
    Pose 0 is at the base, Pose 1 is at the leg of the Stage, i.e. "Safety Pose"

    | Pose  | Dist  | Arm |
    |------ |-----  | --- |
    | 0     | 0     | 0   |
    | 1     | 126   | 20  | Stage / Safety Pose
    | 2     | 150   | 00  |
    | 3     | 180   | 00  |
    | 4     | 200   | 00  |
    
    */


    public static final int ARM_MAX_ACCEL = 100;
    public static final int ARM_MAX_VEL = 200;
    public static final int ARM_JERK = 0;

    public static final int ARM_STATOR_LIMIT = 15;
    public static final int ARM_SUPPLY_LIMIT = 15;
    public static final double ARM_ROTATION_LIMIT_NATIVE = 0;

    public static final double ARM_MANUAL_POWER = 6; // for Voltage control

    /* Consider removing this class
    public static final class ConstantsPlus {

      static final Slot0Configs armGains0 = new Slot0Configs().withGravityType(GravityTypeValue.Arm_Cosine)
          .withKP(0.50)
          .withKI(0.00)
          .withKD(0.00)
          .withKV(0.00)
          .withKS(0.00)
          .withKA(0.00)
          .withKG(0.00);

      

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
    */

  }

  public static class IndexConstants {
    // Tested in Tuner: Negative value is red, and brings in the note
    public static final double INDEX_POWER = -1.0;
    public static final double INDEX_POWER_REV = 0.75;

  }

  public static class IntakeConstants {
    public static final double INTAKE_POWER = -0.75; // 0.75
    public static final double INTAKE_POWER_REV = 0.75;
    
  }

  public static class ShooterConstants {

    // TODO Tune this value
    public static final double SHOOTER_VELOCITY = 60; // rotations per second
    
<<<<<<< HEAD:src/main/java/frc/robot/util/Constants.java
    // public static final double SHOOTER_VELOCITY_SHORT_RANGE = 40; // Not currently in use anywhere
    // public static final double SHOOTER_VELOCITY_LONG_RANGE = 60; // Not currently in use anywhere, Flywheel speed for long range shots only
    // public static final double SHOOTER_IDLE_VELOCITY = SHOOTER_VELOCITY * 0.30; // 30% of max speed
    // public static final double VELOCITY_ERROR_MARGIN = SHOOTER_VELOCITY * 0.10; // 5% of max speed
=======
    // TODO Change this value for shooting "power" adjustments
    public static final double SHOOTER_VELOCITY = 90; // 60

    public static final double SHOOTER_VELOCITY_LONG_RANGE = 60; // 60
    public static final double SHOOTER_IDLE_VELOCITY = SHOOTER_VELOCITY * 0.30;
    public static final double VELOCITY_ERROR_MARGIN = SHOOTER_VELOCITY * 0.05;
>>>>>>> event-ready:src/main/java/frc/util/Constants.java

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
    public static final int CANDLE_ID =         15; // Mini LED and LED strip controller
    public static final int INTAKE_ID =         20;
    public static final int INDEX_ID =          21;
    public static final int ARM_ID =            22;
    public static final int TOP_FLYWHEEL_ID =  23; // TOP
    public static final int BOTTOM_FLYWHEEL_ID = 24; // BOTTOM
    public static final int WINCH_ID =          25;
    public static final int NOTE_SENSOR_ID =    42; // Time of Flight sensor for the note

    /*
     * CAN IDs
     * | Object      | ID |
     * |------------ |----|
     * | Drive FL    | 01 |
     * | Steer FL    | 02 |
     * | Drive FR    | 03 |
     * | Steer FR    | 04 |
     * | Drive RL    | 05 |
     * | Steer RL    | 06 |
     * | Drive RR    | 07 |
     * | Steer RR    | 08 |
     * | CANCoder FL | 09 |
     * | CANCoder FR | 10 |
     * | CANCoder RL | 11 |
     * | CANCoder RR | 12 |
     * | -Hard pass- | 13 |
     * | Pidgeon     | 14 |
     * | Candle      | 15 |
     */
    
     /*
     * Season Specific
     * | Object      | ID |
     * |------------ |----|
     * | Intake      | 20 |
     * | Index       | 21 |
     * | Arm         | 22 |
     * | Launcher LT | 23 | TOP
     * | Launcher RT | 24 | BOTTOM
     * | Winch       | 25 |
     * | ToF Note    | 42 |
     */
  }

} // end of class Constants