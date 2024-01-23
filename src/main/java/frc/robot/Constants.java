// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
