// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LauncherSubsystem;

import frc.robot.subsystems.OrchestraSubsystem;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import com.ctre.phoenix6.Utils;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;

public class RobotContainer {

  // ? can we move the below 2 fields under System Constants?
  private double MaxSpeed = 6; // 6 meters per second desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  // #region Devices
  TalonFX shooterMotorMain;
  TalonFX shooterMotorSub;
  // #endregion

  // #region Subsystems
  LauncherSubsystem shooter;
  IntakeSubsystem intake;
  OrchestraSubsystem daTunes;
  // #endregion Subsystems

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController = new CommandXboxController(
      OperatorConstants.kOperatorControllerPort);

  public RobotContainer() {

    shooterMotorMain = new TalonFX(Constants.CANIDs.rightFlywheelCAN);
    shooterMotorSub = new TalonFX(Constants.CANIDs.leftFlywheelCAN);

    // #region some configs

    shooterMotorMain.setInverted(false);
    shooterMotorSub.setInverted(true);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);

    shooterMotorSub = new TalonFX(Constants.CANIDs.leftFlywheelCAN);
    shooterMotorMain = new TalonFX(Constants.CANIDs.rightFlywheelCAN);
    // #endregion

    shooter = new LauncherSubsystem(shooterMotorMain, shooterMotorSub);
    shooter.SetStatePower(1);
    shooter.SetRatio(1);

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward
                                                                                                     // with
            // negative Y (forward)
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
        ));

    // Configure the trigger bindings
    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    configureBindings();
  }

  // tl;dr: Trigger class for simple booleans
  private void configureBindings() {
    // WOW This is bad but oh well
    m_driverController.y().onTrue(new InstantCommand(() -> shooter.Toggle(),
        shooter));

    m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    m_driverController.b().whileTrue(drivetrain.applyRequest(() -> point
        .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));

    // reset the field-centric heading on left bumper press

    m_driverController.back().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    m_operatorController.back().onTrue(drivetrain.runOnce(() -> drivetrain.getPigeon2().setYaw(0)));

    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> intake.Run(() -> -1)));
    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> intake.Run(() -> 1)));
  }

  public void DebugMethodSingle() {
    var tab = Shuffleboard.getTab("Driver Diagnostics");
    tab.addBoolean("Shooter Running", () -> shooter.Running());
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new WaitCommand(3603);
  } // end of Autonomous

} // end of class
