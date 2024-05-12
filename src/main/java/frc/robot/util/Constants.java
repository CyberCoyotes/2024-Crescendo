// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

public final class Constants {

  // Ports and IDs

    public static final double MAX_SPEED = 6; // 6 meters per second desired top speed
    public static final double MAX_ANGULAR_RATE = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

    
    public static final int CANDLE_ID =         15; // Mini LED and LED strip controller    
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

    public static class OperatorConstants {
      public static final int K_DRIVER_CONTROLLER_PORT = 0; // Driver Controller
      public static final int K_OPERATOR_CONTROLLER_PORT = 1; // Operator or Secondary Controller
      public static final int DEFAULT_ARM_INCREMENT_VALUE = 20; // ?
  
    }

} // end of class Constants

/* 
See also 
https://github.com/FRCTeam2910/2023CompetitionRobot-Public/blob/main/src/main/java/org/frcteam2910/c2023/config/Phantom.java
package org.frcteam2910.c2023.config;
import edu.wpi.first.math.controller.PIDController; 

They had two robots named Phantom and Loki

*/