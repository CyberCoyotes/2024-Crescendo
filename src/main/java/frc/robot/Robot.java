// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.ctre.phoenix6.SignalLogger;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.drivetrain.Gyro;

@SuppressWarnings("unused")

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    // DO NOT LEAVE THIS UNCOMMENTED DURING COMPETITION
    // SignalLogger.setPath("/media/sda1/");
    // SignalLogger.start();
    CameraServer.startAutomaticCapture();
    // Creates the CvSink and connects it to the UsbCamera
    CvSink cvSink = CameraServer.getVideo();

    NetworkTableInstance.getDefault().getTable("limelight-iludium").getEntry("stream").setNumber(2);

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {

    // gyro.setAutonStartingPose180(180);
    // gyro.setAutonStartingPose(-180); // Seems to impact Teleop more than auton
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    // Shaun's Pre-Field Additions: 4-2-24
    // Some things to try to reset the gyro

    // This one probably won't work; it's equivalent to Rkyer just pressing Start.
    // m_robotContainer.drivetrain.seedFieldRelative();
    
    // This one also probably won't work.
    // m_robotContainer.drivetrain.getPigeon2().setYaw(0);
    
    // I have some hope for this!
    // m_robotContainer.drivetrain.getPigeon2().setYaw(180);
    
    // This one is nasty, and I don't have a great understanding of it; delete this
    // method if we don't use it. It pretty much does the same as seedFieldRelative
    // but with an additional 180 degrees
    // m_robotContainer.drivetrain.saltFieldRelative();

  // new InstantCommand(gyro::setStartingPoseBasedOnAlliance);
  
}

  @Override
  public void teleopPeriodic() {
    // SmartDashboard.putNumber("Gyro Pose", gyro.getYaw());
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
