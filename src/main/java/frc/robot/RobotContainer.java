// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IncrementIndex1Stage;
import frc.robot.commands.RunShooter;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.subsystems.WinchSubsystem;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerTrajectory;

public class RobotContainer {
  RunShooter shooterRun;
  IncrementIndex1Stage indexIncrent;

  // private final Telemetry logger = new
  // Telemetry(Constants.SystemConstants.MAX_SPEED);
  // #endregion
  // #region Network Tables
  SendableChooser<Command> autoChooser;
  // Interactable way to change increment distance on arm for High Speed High
  // Fidelity Testing
  GenericEntry incrementDistanceEntry;
  // #endregion Network Tables
  // #region Subsystems
  ShooterSubsystemVelocity shooter = new ShooterSubsystemVelocity();
  IntakeSubsystem intake = new IntakeSubsystem();
  IndexSubsystem index = new IndexSubsystem();
  // OrchestraSubsystem daTunes;
  // WinchSubsystem winch;
  ArmSubsystem arm = new ArmSubsystem();
  // #endregion Subsystems

  // #region commands

  // #endregion
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController m_driverController = new CommandXboxController(0); // My joystick
  private final CommandXboxController m_operatorController = new CommandXboxController(1); // My joystick

  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final CommandXboxController[] Controllers = new CommandXboxController[] { m_driverController,
      m_operatorController

  };

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  /* TODO For testing autonomous files built with PathPlanner */
  private Command autonTesting = drivetrain.getAutoPath("Start1.0-3-4-5");

  public RobotContainer() {
    indexIncrent = new IncrementIndex1Stage(index);
    shooter = new ShooterSubsystemVelocity();

    // Set up our pathplanenr stuff
    NamedCommands.registerCommand("RunShooter", new RunShooter(shooter));

    // arm.setDefaultCommand(
    // arm.run(() -> arm(((m_operatorController.axisLessThan(Axis.kLeftY.value,
    // -0.1).getAsBoolean() ||
    // (m_operatorController.axisGreaterThan(Axis.kLeftY.value, 0.1))
    // .getAsBoolean()) ? m_operatorController.getLeftY() : 0))));

    // // intake run depending on driver bumper status
    intake.setDefaultCommand(intake.run(() -> intake.Run(0.25 * -BumperStatus(0))));
    index.setDefaultCommand(index.run(() -> index.SetPower(BumperStatus(1))));
    shooter.setDefaultCommand(shooter.run(() -> shooter.SetOutput(
        // ! cool but unintuitive
        Math.max(m_operatorController.getLeftTriggerAxis() * 0.5 * 60,
            m_operatorController.getRightTriggerAxis() * 60))));

    configureBindings();
    DebugMethodSingle();
  }

  private void configureBindings() {

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward
                                                                                                     // with
            // negative Y (forward)
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
        ));

    m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    m_driverController.b().whileTrue(drivetrain
        .applyRequest(() -> point
            .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));

    // reset the field-centric heading on left bumper press
    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    m_operatorController.y().whileTrue(new InstantCommand(() -> arm.setArmPose(0)));
    m_operatorController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(10)));

  };

  public void DebugMethodSingle() {
    // #region Driving
    // More useful logs that the drivers will probably want
    // driverDiagnostics.addDouble("Net Arm Angle", () ->
    // arm.GetPositionDegreesAbsolulte());S
    // #endregion Driving
    // #region Testing

    // Less useful logs that we still need to see for testing.

    // var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");
    var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");

    // driverDiagnostics.addBoolean("Note Detected", () -> index.HasCargo());
    // driverDiagnostics.addDouble("Arm Rot", () ->
    // arm.GetArmPos().getValueAsDouble());
    // driverDiagnostics.addDouble("Arm Rot Deg", () -> arm.GetPositionDegrees());
    // arm.showArmTelemetry("Driver Diagnostics");

    // #endregion Testing
  }

  /**
   * Schizophrenic method to quickly get an int indicating driver bumper status. 1
   * if RB,-1 if LB, 0 if both or none
   * 
   * @param port controller port, 0 is driver, 1 is operaator
   * @return an int indicating driver bumper status.
   */
  private double BumperStatus(int port) {
    return (Controllers[port].rightBumper().getAsBoolean() ? 1 : 0)
        + (Controllers[port].leftBumper().getAsBoolean() ? -1 : 0);
  }

  public Command getAutonomousCommand() {
    /* irst put the drivetrain into auto run mode, then run the auto */
    return indexIncrent;
  }
}