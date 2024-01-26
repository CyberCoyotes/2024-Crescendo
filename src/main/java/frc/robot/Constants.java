// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import static frc.robot.Constants.SystemConstants.PID;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SystemConstants {

    public static double MaxSpeed = 6; // 6 meters per second desired top speed
    public static double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    public static class PID {
      public static double driveP = 0.3f;
      public static double driveI = 0;
      public static double driveD = 0.1;
      public static double steerP = 0.3;
      public static double steerI = 0;
      public static double steerD = 0.1;
    }

    public static HolonomicPathFollowerConfig PathPlannerConfig = new HolonomicPathFollowerConfig(
        new PIDConstants(PID.driveP, PID.driveI, PID.driveD), // Translation PID constants
        new PIDConstants(PID.steerP, PID.steerI, PID.steerD), // Rotation PID constants
        MaxSpeed, // Max module speed, in m/s
        0.4, // Drive base radius in meters. Distance from robot center to furthest module.
        new ReplanningConfig() // Default path replanning config. See the API for the options here

    );

  }

  public static class CANIDs {
    // ! DO NOT use CAN IDs 1-9; they are reserved for the modules and Pigeon

    public static final int intakeCAN = 10;
    public static final int indexCAN = 11;

    public static final int timeOfFlightID = 12;

    public static final int bassGuitar = 13;

    public static final int rightFlywheelCAN = 15;
    public static final int leftFlywheelCAN = 16;
  }
}
