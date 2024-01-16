// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DualFlyWheelSubsystem;
import frc.robot.subsystems.OrchestraSubsystem;
import frc.robot.subsystems.OrchestraSubsystem.Song;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  //#region Devices 
  TalonFX flywheelLeft, flywheelRight, bassGuitar;   
  //#endregion

  //#region Subsystems
  DualFlyWheelSubsystem flywheel;
  OrchestraSubsystem daTunes;
  //#endregion Subsystems

  private final CommandXboxController m_driverController =
    new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    flywheelLeft = new TalonFX(Constants.SystemConstants.leftFlywheelCAN);
    flywheelRight = new TalonFX(Constants.SystemConstants.rightFlywheelCAN);
    //Orchestra
    bassGuitar = new TalonFX(Constants.SystemConstants.bassGuitar);

    flywheel = new DualFlyWheelSubsystem(flywheelLeft, flywheelRight);
    flywheel.SetStatePower(0.2);
    flywheel.SetRatio(0.8);

    daTunes = new OrchestraSubsystem(new TalonFX[]{bassGuitar});
    daTunes.SetTune(Song.ONE_ONE_FIVE );
    

    // Configure the trigger bindings
    configureBindings();
  }

  // tl;dr: Trigger class for simple booleans
  private void configureBindings() {
    
    //WOW This is bad but oh well
    m_driverController.y().onTrue(new InstantCommand( () -> flywheel.Toggle(), flywheel));
    m_driverController.b().onTrue(new InstantCommand( () -> daTunes.Play(), daTunes));
    m_driverController.a().onTrue(new InstantCommand( () -> daTunes.Shud(), daTunes));
    
  }
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new WaitCommand(2);
  }
}
