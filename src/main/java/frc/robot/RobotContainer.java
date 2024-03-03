// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.TrackLL;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.OrchestraSubsystem;
import frc.robot.subsystems.WinchSubsystem;

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
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;

import static frc.robot.Constants.SystemConstants.MAX_SPEED;

public class RobotContainer {

  // #region Drivetrain
  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MAX_SPEED * 0.1)
      .withRotationalDeadband(Constants.SystemConstants.MAX_ANGULAR_RATE * 0.1) // Add a 10%
      // deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Computer! I want field-centric
                                                               // driving in open loop!
  // private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  // private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(Constants.SystemConstants.MAX_SPEED);
  // #endregion
  // #region Network Tables
  SendableChooser<Command> autoChooser;
  // Interactable way to change increment distance on arm for High Speed High
  // Fidelity Testing
  GenericEntry incrementDistanceEntry;
  // #endregion Network Tables
  // #region Devices
  TalonFX shooterMotorMain;
  TalonFX shooterMotorSub;
  // #endregion
  // #region Subsystems
  ShooterSubsystem shooter;
  IntakeSubsystem intake;
  OrchestraSubsystem daTunes;
  WinchSubsystem winch;
  ArmSubsystem arm;
  VisionSubsystem visionSub;

  // #endregion Subsystems

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.K_DRIVER_CONTROLLER_PORT);
  private final CommandXboxController m_operatorController = new CommandXboxController(
      OperatorConstants.K_OPERATOR_CONTROLLER_PORT);
  // schizophrenic way to access controllers; might have to get taken out back and
  // "deprecated"
  private final CommandXboxController[] Controllers = new CommandXboxController[] { m_driverController,
      m_operatorController

  };

  public RobotContainer() {

    // Set shuffleboard autos

    autoChooser = AutoBuilder.buildAutoChooser();

    Shuffleboard.getTab("Autos").add("Auton", autoChooser);

    shooterMotorMain = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
    shooterMotorSub = new TalonFX(Constants.CANIDs.LEFT_FLYWHEEL_ID);

    // #region some configs

    shooterMotorMain.setInverted(false);
    shooterMotorSub.setInverted(true);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);

    shooterMotorSub = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
    shooterMotorMain = new TalonFX(Constants.CANIDs.RIGHT_FLYWHEEL_ID);
    // #endregion

    shooter = new ShooterSubsystem(shooterMotorMain, shooterMotorSub);
    shooter.SetStatePower(1);
    shooter.SetRatio(1);
    //arm.setDefaultCommand(arm.DriveCommand(m_driverController.getLeftY()));
    // intake run depending on driver bumper status
    //intake.setDefaultCommand(intake.run(() -> intake.Run(() -> BumperStatus(0))));
    shooter.setDefaultCommand(shooter.RunShooter(m_operatorController.getRightTriggerAxis()));
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

    // m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    // m_driverController.b().whileTrue(drivetrain.applyRequest(() -> point
        // .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));
  m_driverController.x().onTrue(new InstantCommand(() -> visionSub.turnLL()));
    // reset the field-centric heading on back press
    // Instant command because it doesn't set a requirment. In short, creating a
    // requirement may block this or cancel another (say, driving)
    m_driverController.back().onTrue(new InstantCommand(() -> drivetrain.seedFieldRelative()));
    // ! Delete this below if we know that Gyro is working; close issue #81
    // m_operatorController.back().onTrue(new InstantCommand(() ->
    // drivetrain.getPigeon2().setYaw(0)));
    // -------------------//-------------------//-------------------//-------------------//-------------------//-------------------
    // m_operatorController.povUp().onTrue(winch.run(() -> winch.Drive(() -> 1)));
    // m_operatorController.povDown().onTrue(winch.run(() -> winch.Drive(() -> -1)));
    
    /* This ride is closed and undergoing maintence 
    m_operatorController.y()
        .onTrue(
            arm.run(() -> arm.IncrementNativeUnits(incrementDistanceEntry.getInteger(DEFAULT_ARM_INCREMENT_VALUE))));
    m_operatorController.a()
        .onTrue(
            arm.run(() -> arm.IncrementNativeUnits(incrementDistanceEntry.getInteger(-DEFAULT_ARM_INCREMENT_VALUE))));

    */ // End of comment for incremental arm
  }

  public void DebugMethodSingle() {
    // #region Driving
    // More useful logs that the drivers will probably want
    var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");
    // driverDiagnostics.addDouble("Net Arm Angle", () -> arm.GetPositionDegreesAbsolulte());
    // #endregion Driving
    // #region Testing

    // Less useful logs that we still need to see for testing.

    var testerDiagnostics = Shuffleboard.getTab("Driver Diagnostics");

    incrementDistanceEntry = testerDiagnostics.add("Increment Distance (Control)", 20).getEntry();
    testerDiagnostics.addBoolean("Shooter Running", () -> shooter.Running());
    // testerDiagnostics.addDouble("Net Arm Angle", () -> arm.GetPositionDegreesAbsolulte());
    //testerDiagnostics.addDouble("Net Arm Encoder", () -> arm.GetPositionEncoder());
    testerDiagnostics.addDouble("Winch Input", () -> winch.GetControl());
    //testerDiagnostics.addBoolean("Note Detected", () -> intake.HasCargo());
    testerDiagnostics.addDouble("Shooter Danger Level", () -> shooter.GetDanger());
    testerDiagnostics.addBoolean("Fatal Danger", () -> shooter.IsDangerous());
    // #endregion Testing
  }

  /**
   * Schizophrenic method to quickly get an int indicating driver bumper status. 1
   * if RB,-1 if LB, 0 if both or none
   * 
   * @param port controller port, 0 is driver, 1 is operaator
   * @return use your brain pretty please
   */
  private double BumperStatus(int port) {
    return (Controllers[port].rightBumper().getAsBoolean() ? 1 : 0)
        + (Controllers[port].leftBumper().getAsBoolean() ? -1 : 0);
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  } // end of Autonomous

} // end of class
