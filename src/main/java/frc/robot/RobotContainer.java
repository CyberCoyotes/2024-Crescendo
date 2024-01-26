// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.RatioMotorSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.OrchestraSubsystem;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;

import static frc.robot.Constants.SystemConstants.MAX_SPEED;

public class RobotContainer {

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MAX_SPEED * 0.1)
      .withRotationalDeadband(Constants.SystemConstants.MAX_ANGULAR_RATE * 0.1) // Add a 10%
      // deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Computer! I want field-centric
                                                               // driving in open loop!
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SystemConstants.MAX_SPEED);
  SendableChooser<Command> autoChooser;

  // #region Devices
  TalonFX shooterMotorMain;
  TalonFX shooterMotorSub;
  // #endregion

  // #region Subsystems
  RatioMotorSubsystem shooter;
  OrchestraSubsystem daTunes;
  // #endregion Subsystems

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.K_DRIVER_CONTROLLER_PORT);

  public RobotContainer() {

    // Set shuffleboard autos

    autoChooser = AutoBuilder.buildAutoChooser();

    Shuffleboard.getTab("Autos").add("Auton", autoChooser);

    shooterMotorMain = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_CAN);
    shooterMotorSub = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_CAN);

    // #region some configs

    shooterMotorMain.setInverted(false);
    shooterMotorSub.setInverted(true);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);

    shooterMotorSub = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_CAN);
    shooterMotorMain = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_CAN);
    // #endregion

    shooter = new RatioMotorSubsystem(shooterMotorMain, shooterMotorSub);
    shooter.SetStatePower(1);
    shooter.SetRatio(1);

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain
            .applyRequest(
                () -> drive.withVelocityX(-m_driverController.getLeftY() * Constants.SystemConstants.MAX_SPEED) // Drive
                                                                                                                // forward
                    // with
                    // negative Y (forward)
                    .withVelocityY(-m_driverController.getLeftX() * MAX_SPEED) // Drive left with
                                                                               // negative X (left)
                    .withRotationalRate(-m_driverController.getRightX() * Constants.SystemConstants.MAX_ANGULAR_RATE) // Drive
            // counterclockwise
            // with
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

    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

  }

  public void DebugMethodSingle() {
    var tab = Shuffleboard.getTab("Driver Diagnostics");
    tab.addBoolean("Shooter Running", () -> shooter.Running());
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  } // end of Autonomous

} // end of class
