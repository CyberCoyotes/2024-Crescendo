// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DualFlyWheelSubsystem;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  //#region Devices 
  TalonFX flywheelLeft, flywheelRight;   
  //#endregion

  //#region Subsystems
  DualFlyWheelSubsystem flywheel;
  //#endregion Subsystems

  private final CommandXboxController m_driverController =
    new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    flywheelLeft = new TalonFX(Constants.SystemConstants.leftFlywheelCAN);
    flywheelRight = new TalonFX(Constants.SystemConstants.rightFlywheelCAN);
    flywheel = new DualFlyWheelSubsystem(flywheelLeft, flywheelRight);
    flywheel.SetStatePower(0.2);
    flywheel.SetRatio(0.8);


    // Configure the trigger bindings
    configureBindings();
  }

  // tl;dr: Trigger class for simple booleans
  private void configureBindings() {
    
    m_driverController.b().onTrue(new InstantCommand( () -> flywheel.Toggle(), flywheel));
    
  }
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new WaitCommand(2);
  }
}
