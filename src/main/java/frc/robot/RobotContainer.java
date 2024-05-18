// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.arm.ArmConstants;
import frc.robot.arm.ArmSubsystem;
import frc.robot.arm.SetArmClimb;
import frc.robot.climb.SetWinch;
import frc.robot.climb.WinchConstants;
import frc.robot.climb.WinchSubsystem;
import frc.robot.drivetrain.CommandSwerveDrivetrain;
import frc.robot.drivetrain.TunerConstants;
import frc.robot.util.NoteSensor;
import frc.robot.xperimental.AutoShootStage;
import frc.robot.xperimental.IntakeIndexSmartTimer;
import frc.robot.xperimental.IntakeIndexTimer;
import frc.robot.xperimental.VisionArmPose;
import frc.robot.index.IndexSubsystem;
import frc.robot.intake.IntakeCommandGroup;
import frc.robot.intake.IntakeIndex;
import frc.robot.intake.IntakeRevCommandGroup;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.shooter.AutoShoot;
import frc.robot.shooter.AutoShootAmp;
import frc.robot.shooter.SetFlywheel;
import frc.robot.shooter.Shoot;
import frc.robot.shooter.ShootAmp;
import frc.robot.shooter.ShootStage;
import frc.robot.shooter.FlywheelConstants;
import frc.robot.shooter.ShooterSubsystem;

// Getting rid of the soft unused warnings
@SuppressWarnings("unused")

// The RobotContainer is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, the robot is defined here.
public class RobotContainer {

  SwerveRequest.FieldCentric driveRequest;
  // private final Telemetry logger = new
  // Telemetry(Constants.SystemConstants.MAX_SPEED);

  /* Subsystems */
  ShooterSubsystem shooter = new ShooterSubsystem();
  IntakeSubsystem intake = new IntakeSubsystem();
  IndexSubsystem index = new IndexSubsystem();
  WinchSubsystem winch = new WinchSubsystem();
  ArmSubsystem arm = new ArmSubsystem();
  NoteSensor notesensor = new NoteSensor();
  VisionArmPose visionArmPose = new VisionArmPose(null, arm);

  // VisionFeedback limelightLedFeedback = new VisionFeedback(null, null);

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
  private final SetFlywheel setShooterIdle = new SetFlywheel(shooter, arm, FlywheelConstants.FLYWHEEL_IDLE_VELOCITY);
  private final Shoot shoot = new Shoot(shooter, index, notesensor);
  private final ShootAmp shootAmp = new ShootAmp(shooter, index, notesensor);
  private final ShootStage shootStage = new ShootStage(shooter, index, notesensor);

  /* Auton Specific Commands */
  private final AutoShoot autoShoot = new AutoShoot(arm, index, intake, shooter, notesensor);
  private final AutoShootAmp autoShootAmp = new AutoShootAmp(shooter, index, notesensor);
  private final AutoShootStage autoShootStage = new AutoShootStage(arm, index, intake, notesensor, shooter);

  /* Autonomous Chooser */
  SendableChooser<Command> autoChooser;

  // Constructor of the class
  public RobotContainer() {

    /* Autonomous - Pathplanner Named Commands */
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
    configureBindings(); //

    /* Needed to display the custom debug method */
    // debug();

  } // end of constructor

  /*
   * Method to check our Alliance status for Red or Blue
   * This is essential for the autonomous mode to know which side of the field we
   * are on
   */
  public static boolean isAllianceRed() {
    return DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red;
  }

  /*
   * This method sets up the button bindings for the robot;
   * defines what each button on the robot's controller should do when pressed.
   */
  private void configureBindings() {

    /* DRIVER BINDINGS */
    index.setDefaultCommand(index.run(() -> index.setIndexPower(0)));

    driveRequest = drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed)
        // Drive left with negative X (left)    
        .withVelocityY(-m_driverController.getLeftX() * MaxSpeed)
        // Drive counterclockwise with negative X (left)
        .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) 
    ;

    // Drivetrain will execute this command periodically
    drivetrain.setDefaultCommand( 
        // Drive forward with negative Y (forward)
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed)
            // Drive left with negative X (left)
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) 
            // Drive counterclockwise with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) 
        ));

    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    m_driverController.a().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_HOME_POSE)));
    m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_MID_POSE)));
    m_driverController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(ArmConstants.ARM_AMP_POSE)));
    // m_driverController.y().whileTrue(new InstantCommand(() -> ( ))); // Y-Button

    m_driverController.rightBumper().whileTrue(new IntakeCommandGroup(index, intake));
    m_driverController.leftBumper().whileTrue(new IntakeRevCommandGroup(index, intake));
    m_driverController.rightTrigger().whileTrue(shoot);
    m_driverController.leftTrigger().whileTrue(shootAmp);

    /* OPERATOR BINDINGS */
    // m_operatorController.a().whileTrue());
    m_operatorController.b().whileTrue(new SetArmClimb(arm, ArmConstants.ARM_MANUAL_POWER));
    // m_operatorController.x().whileTrue());
    m_operatorController.y().whileTrue(new SetWinch(winch, WinchConstants.WINCH_POWER));
    m_operatorController.back().whileTrue(new SetWinch(winch, WinchConstants.WINCH_POWER_BOOST));
    m_operatorController.rightTrigger().whileTrue(shootStage); // Shoot Stage

  };

  /* Use for Debugging and diagnostics purposes */
  public void debug() {
    // var driverDiagnostics = Shuffleboard.getTab("Diagnostics");
    // driverDiagnostics.addDouble("Arm Rot", () ->
    // arm.GetArmPos().getValueAsDouble());
    // driverDiagnostics.addDouble("Arm Rot Deg", () -> arm.GetPositionDegrees());
    // arm.showArmTelemetry("Driver Diagnostics");
    // Shuffleboard.getTab("Arm").add("Arm Output", arm);

  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

} // End of class