// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

   

package frc.robot;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

  /*Real world unit of millimeters
  * When less than this, the note status is considered "loaded"
  */
  public static int NOTE_DISTANCE_CHECK = 100;

  public static class OperatorConstants {
    public static final int K_DRIVER_CONTROLLER_PORT = 0; // Driver Controller
    public static final int K_OPERATOR_CONTROLLER_PORT = 1; // Operator or Secondary Controller

    public static final int DEFAULT_ARM_INCREMENT_VALUE = 20; // ?

  }

  public static class SystemConstants {

    public static final double MAX_SPEED = 6; // 6 meters per second desired top speed
    public static final double MAX_ANGULAR_RATE = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    public static final double DEG_TO_ARM_ENCODER = 1;
    public static final double ARM_ENCODER_TO_DEG = 1 / DEG_TO_ARM_ENCODER;
    // For every 488 rotations of our driver motor, the arm makes 1 revolution.
    public static final double NET_ARM_RATIO = 458;

    // Is this standard or added by us?
    public static class PID {
      public static final double DRIVE_P = 0.3f;
      public static final double DRIVE_I = 0;
      public static final double DRIVE_D = 0.1;
      public static final double STEER_P = 0.3;
      public static final double STEER_I = 0;
      public static final double STEER_D = 0.1;
    }

    public static final HolonomicPathFollowerConfig PATH_PLANNER_CONFIG = new HolonomicPathFollowerConfig(
        new PIDConstants(PID.DRIVE_P, PID.DRIVE_I, PID.DRIVE_D), // Translation PID constants
        new PIDConstants(PID.STEER_P, PID.STEER_I, PID.STEER_D), // Rotation PID constants
        MAX_SPEED, // Max module speed, in m/s
        0.4, // Drive base radius in meters. Distance from robot center to furthest module.
        new ReplanningConfig() // Default path replanning config. See the API for the options here

    );
  }

  public static class CANIDs {
   /* CAN IDs 
    | Object      | ID  | 
    |------------ |-----|
    | Drive FL    | 01  |
    | Steer FL    | 02  |
    | Drive FR    | 03  |
    | Steer FR    | 04  |
    | Drive RL    | 05  |
    | Steer RL    | 06  |
    | Drive RR    | 07  |
    | Steer RR    | 08  |
    | CANCoder FL | 09  |
    | CANCoder FR | 10  |
    | CANCoder RL | 11  |
    | CANCoder RR | 12  |
    | -Hard pass- | 13  |
    | Pidgeon     | 14  |
    | Candle      | 15  |
    */

    /* Season Specific 
    | Object      | ID  | 
    |------------ |-----|
    | Intake      | 20  |
    | Index       | 21  |
    | Arm         | 22  |  
    | Launcher LT | 23  |
    | Launcher RT | 24  |
    | Winch       | 25  |
    | ToF Note    | 42  | 
    | Bass Guitar | | 
    */

    /* Taz 4: L3 Gear Ratio */
    public static final int CANDLE_ID = 15; // Mini LED and LED strip controller
    public static final int INTAKE_ID = 20;
    public static final int INDEX_ID = 21;
    public static final int ARM_ID = 22;
    public static final int RIGHT_FLYWHEEL_ID = 23; // Right, when looking from back?
    public static final int LEFT_FLYWHEEL_ID = 24; // 
    public static final int WINCH_ID = 25;
    public static final int NOTE_SENSOR_ID = 42; // Time of Flight sensor for the note

    // Extra CAN IDs
    public static final int BASS_GUITAR = 13;
  }
}
