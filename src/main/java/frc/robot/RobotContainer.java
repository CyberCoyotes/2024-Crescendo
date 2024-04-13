// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
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
import frc.robot.commands.IntakeRevCommandGroup;
import frc.robot.commands.RunShooter;
import frc.robot.commands.SetArmClimb;
import frc.robot.commands.SetIndex;
import frc.robot.commands.SetFlywheel;
import frc.robot.commands.SetWinch;
import frc.robot.commands.Shoot;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutoShootAmp;
import frc.robot.commands.ShootAmp;
import frc.robot.experimental.ShootAmp2;
import frc.robot.experimental.ShootAmp3;

import frc.robot.commands.ShootStage;
import frc.robot.commands.IntakeIndex;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.subsystems.WinchSubsystem2;
import frc.robot.util.Constants;
import frc.robot.util.ArmConstants;
import frc.robot.util.ShooterConstants;
import frc.robot.util.TunerConstants;
import frc.robot.util.WinchConstants;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.experimental.AutoShootStage;
import frc.robot.experimental.AutoShootStage2;
import frc.robot.experimental.IntakeIndexSmartTimer;
import frc.robot.experimental.IntakeIndexTimer;

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
  ShooterSubsystem2 shooter2 = new ShooterSubsystem2();
  IntakeSubsystem intake = new IntakeSubsystem();
  IndexSubsystem index = new IndexSubsystem();
  WinchSubsystem2 winch = new WinchSubsystem2();
  ArmSubsystem arm = new ArmSubsystem();
  NoteSensorSubsystem notesensor = new NoteSensorSubsystem();
  Gyro pidgey = new Gyro();

  // #endregion Subsystems

  // #region commands

  // #endregion

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController m_driverController = new CommandXboxController(0); // My joystick
  private final CommandXboxController m_operatorController = new CommandXboxController(1); // My joystick
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain
  private final CommandXboxController[] Controllers = new CommandXboxController[] { m_driverController,
      m_operatorController

  };

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1)
      .withRotationalDeadband(MaxAngularRate * 0.1)
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  private final IntakeCommandGroup intakeGroup = new IntakeCommandGroup(index, intake);
  private final IntakeRevCommandGroup intakeRevGroup = new IntakeRevCommandGroup(index, intake);

  // Only Sets the flywheel to idle velocity, no index
  private final SetFlywheel setShooterIdle = new SetFlywheel(shooter2, arm, ShooterConstants.FLYWHEEL_IDLE_VELOCITY);

  private final Shoot shoot = new Shoot(shooter2, index, notesensor);
  // TODO Test ShootAmp versions; changed 
  private final ShootAmp shootAmp = new ShootAmp(shooter2, index, notesensor); 
  private final ShootStage shootStage = new ShootStage(shooter2, index, notesensor);

  /* Auton Specific Commands */
  private final AutoShoot autoShoot = new AutoShoot(arm, index, intake, shooter2, notesensor);
  private final AutoShootAmp autoShootAmp = new AutoShootAmp(shooter2, index, notesensor);
  private final AutoShootStage autoShootStage = new AutoShootStage(arm, index, intake, notesensor, shooter2);
  
  /* Autonomous Chooser */
  SendableChooser<Command> autoChooser;

  // Constructor of the class
  public RobotContainer() {
    Limelight lime = new Limelight();

    /* Pathplanner Named Commands */
    NamedCommands.registerCommand("AutoShoot", autoShoot); // AutoShootWhenReady --> AutoShoot
    NamedCommands.registerCommand("AutoShootAmp", autoShootAmp); // shootWhenReadyAmp --> autoShootAmp 
    NamedCommands.registerCommand("AutoShootStage", autoShootStage);
    NamedCommands.registerCommand("Intake", new IntakeIndex(index, intake, notesensor));
    NamedCommands.registerCommand("IntakeTimer", new IntakeIndexTimer(index, intake));
    NamedCommands.registerCommand("IntakeSmartTimer", new IntakeIndexSmartTimer(index, intake));

    /*
     * Auto Chooser
     * Unintended side effect is this will create EVERY auton file from the RIO
     * deploy folder.
     * FTP into the the RIO to delete old auton options
     */
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    // Shuffleboard.getTab("Auton").add("Auto Chooser", autoChooser);

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

    /* DRIVER BINDINGS */
    index.setDefaultCommand(index.run(() -> index.setIndexPower(0)));

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

    /*
     * This button does nothing UNLESS the robot is manual rotated in teleop
     * to the proper "forward" position. THEN and ONLY THEN, the button can be
     * triggered
     * and the forward orientation is properly set. If not triggered, the robot
     * forward and backwards
     * on the joysticks as are left and right
     */
    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    m_driverController.a().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_HOME_POSE)));
    m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_MID_POSE)));
    m_driverController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_AMP_POSE)));
    // m_driverController.y().whileTrue(new InstantCommand(() -> ( )));

    m_driverController.rightBumper().whileTrue(new IntakeCommandGroup(index, intake));
    m_driverController.leftBumper().whileTrue(new IntakeRevCommandGroup(index, intake));
    
    m_driverController.rightTrigger().whileTrue(shoot);
    m_driverController.leftTrigger().whileTrue(shootAmp);
    // m_driverController.leftTrigger().whileTrue(new SetIndex(index, -0.75));

    /* OPERATOR BINDINGS */

    // m_operatorController.a().whileTrue());
    m_operatorController.b().whileTrue(new SetArmClimb(arm, ArmConstants.ARM_MANUAL_POWER));
    // m_operatorController.x().whileTrue());
    m_operatorController.y().whileTrue(new SetWinch(winch, WinchConstants.WINCH_POWER));
    m_operatorController.back().whileTrue(new SetWinch(winch, WinchConstants.WINCH_POWER_BOOST));

    m_operatorController.rightTrigger().whileTrue(shootStage); // Shoot Stage
    // m_operatorController.leftTrigger().whileTrue(new SetFlywheel(shooter2, arm, ShooterConstants.FLYWHEEL_VELOCITY_STAGE)); // Lob shot

  };

  /* Use for Debugging and diagnostics purposes */
  public void DebugMethodSingle() {
    // #region Driving
    var driverDiagnostics = Shuffleboard.getTab("Diagnostics");
    // #endregion Driving
    // #region Testing

    // driverDiagnostics.addDouble("Arm Rot", () ->
    // arm.GetArmPos().getValueAsDouble());
    // driverDiagnostics.addDouble("Arm Rot Deg", () -> arm.GetPositionDegrees());
    // arm.showArmTelemetry("Driver Diagnostics");
    // Shuffleboard.getTab("Arm").add("Arm Output", arm);

    // SmartDashboard.putNumber("Yaw", pidgey.getYaw());
    // SmartDashboard.putNumber("Angle", pidgey.getAngle());
    // SmartDashboard.putNumber("Rotation2d", pidgey.Rotation2d());
    // SmartDashboard.getBoolean("Left Nominal", pidgey.isStageYawNominalLeft());
    // SmartDashboard.getBoolean("Left Nominal", pidgey.isStageYawNominalRight());


    // #endregion Testing
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

} // End of class