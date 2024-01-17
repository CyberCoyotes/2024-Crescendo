// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.RatioMotorSubsystem;
import frc.robot.subsystems.OrchestraSubsystem;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  // #region Devices
  TalonFX shooterMotor;
  // #endregion

  // #region Subsystems
  RatioMotorSubsystem shooter;
  OrchestraSubsystem daTunes;
  // #endregion Subsystems

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    shooterMotor = new TalonFX(Constants.SystemConstants.shooterCAN);

    shooter = new RatioMotorSubsystem(shooterMotor);
    shooter.SetStatePower(0.2);

    // Configure the trigger bindings
    configureBindings();

  }

  // tl;dr: Trigger class for simple booleans
  private void configureBindings() {

    // WOW This is bad but oh well
    m_driverController.y().onTrue(new InstantCommand(() -> shooter.Toggle(), shooter));

  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new WaitCommand(2);
  }

}
