// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.IntakeIndex;
import frc.robot.commands.IntakeRevCommandGroup;
import frc.robot.commands.RevAndShootCommand;
import frc.robot.commands.RunShooter;
import frc.robot.commands.SetArmClimb;
import frc.robot.commands.SetIndex;
import frc.robot.commands.SetWinch;
import frc.robot.commands.ShootClose;
import frc.robot.commands.ShootSafetyPose;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.subsystems.WinchSubsystem2;

// Getting rid of the soft unused warnings
@SuppressWarnings("unused")
public class RobotContainer {

  RunShooter shooterRun;
  SwerveRequest.FieldCentric driveRequest;
  // private final Telemetry logger = new
  // Telemetry(Constants.SystemConstants.MAX_SPEED);
  // #endregion
  // #region Network Tables

  // #endregion Network Tables
  // #region Subsystems

  /* Subsystems */
  ShooterSubsystemVelocity shooter = new ShooterSubsystemVelocity();
  IntakeSubsystem intake = new IntakeSubsystem();
  IndexSubsystem index = new IndexSubsystem();
  WinchSubsystem2 winch = new WinchSubsystem2();
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

  private final IntakeCommandGroup intakeGroup = new IntakeCommandGroup(index, intake);
  private final IntakeRevCommandGroup intakeRevGroup = new IntakeRevCommandGroup(index, intake);
  // ChargeIntakeCommand chargeIntake = new ChargeIntakeCommand(drivetrain, intake, driveRequest);

  private final ShootSafetyPose shootSafetyPose = new ShootSafetyPose(arm, index, intake, shooter);

  /* Autonomous Chooser */
  SendableChooser<Command> autoChooser;

  // Constructor of the class
  public RobotContainer() {
    Limelight lime = new Limelight();

    /* Pathplanner Named Commands */
    NamedCommands.registerCommand("ShootClose", new ShootClose(arm, index, intake, shooter));
    NamedCommands.registerCommand("Intake", new IntakeIndex(index, intake));
    // NamedCommands.registerCommand("ArmMid", new SetArmPosition(arm,
    // Constants.ArmConstants.ARM_MID_POSE));

    /*
     * Auto Chooser
     * Unintended side effect is this will create EVERY auton file from the RIO
     * deploy folder.
     * FTP into the the RIO to delete old auton options
     */
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    // Shuffleboard.getTab("Auton").add("Auto Chooser", autoChooser);

    shooter = new ShooterSubsystemVelocity();

    /* Configure the Button Bindings */
    configureBindings();

    DebugMethodSingle();

  }

  /*
   * Method to check our Alliance status for Red or Blue
   * This is essential for the autonomous mode to know which side of the field we
   * are on
   */
  public static boolean isAllianceRed() {
    return DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red;
  }

  /* Method to configure the button bindings */
  private void configureBindings() {
    index.setDefaultCommand(index.run(() -> index.SetPower(0)));
            driveRequest = drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed)
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
        ;
        drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        // Drive forward with negative Y (forward)
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed)
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
        ));
    // m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    /*
     * m_driverController.b().whileTrue(drivetrain
     * .applyRequest(() -> point
     * .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(),
     * -m_driverController.getLeftX()))));
     */

    // reset the field-centric heading
    /*
     * m_driverController.b().whileTrue(drivetrain.applyRequest(() -> point
     * .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(),
     * -m_driverController.getLeftX()))));
     */

    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    m_driverController.a().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_HOME_POSE)));

    // TODO Testing
    // m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_SAFETY_POSE)));
    m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(20)));
    // 30 shots are too high, 35 is too slow shots at 160 inches.
    m_driverController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_AMP_POSE)));
    
    // FIXME Using this for Testing
    m_driverController.y().whileTrue(shootSafetyPose);

    m_driverController.rightBumper().whileTrue(new IntakeCommandGroup(index, intake));
    m_driverController.leftBumper().whileTrue(new IntakeRevCommandGroup(index, intake));
    m_driverController.rightTrigger().whileTrue(new RevAndShootCommand(index, shooter));
    m_driverController.rightTrigger().whileFalse(new InstantCommand(() -> shooter.SetOutput(0)));
    m_driverController.leftTrigger().whileTrue(new SetIndex(index, -0.75));

    // m_operatorController.a().whileTrue (new));
    m_operatorController.b().whileTrue(new SetArmClimb(arm, Constants.ArmConstants.ARM_MANUAL_POWER));
    // m_operatorController.x().whileTrue (new));
    m_operatorController.y().whileTrue(new SetWinch(winch, Constants.WinchConstants.WINCH_POWER));
    m_operatorController.back().whileTrue(new SetWinch(winch, Constants.WinchConstants.WINCH_POWER_BOOST));
    // m_operatorController.start().whileTrue();

  };

  /* Use for Debugging and diagnostics purposes */
  public void DebugMethodSingle() {
    // #region Driving
    var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");
    // #endregion Driving
    // #region Testing

    // driverDiagnostics.addDouble("Arm Rot", () ->
    // arm.GetArmPos().getValueAsDouble());
    // driverDiagnostics.addDouble("Arm Rot Deg", () -> arm.GetPositionDegrees());
    // arm.showArmTelemetry("Driver Diagnostics");
    // Shuffleboard.getTab("Arm").add("Arm Output", arm);

    // #endregion Testing
  }
  

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }  

} // End of class