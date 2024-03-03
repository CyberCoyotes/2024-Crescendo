// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IncrementIndex1Stage;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.IntakeRevCommandGroup;
import frc.robot.commands.RevAndShootCommand;
import frc.robot.commands.RunShooter;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.SetIndex;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

// Getting rid of the soft yelling
@SuppressWarnings("unused")
public class RobotContainer {

  RunShooter shooterRun;
  IncrementIndex1Stage indexIncrent;

  // private final Telemetry logger = new
  // Telemetry(Constants.SystemConstants.MAX_SPEED);
  // #endregion
  // #region Network Tables

  // Interactable way to change increment distance on arm for High Speed High
  // Fidelity Testing
  GenericEntry incrementDistanceEntry;
  // #endregion Network Tables
  // #region Subsystems

  /* Subsystems */
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


 
  private final IntakeCommandGroup intakeGroup = new IntakeCommandGroup(index, intake);
  private final IntakeRevCommandGroup intakeRevGroup = new IntakeRevCommandGroup(index, intake);
  //final SetIndex indexing = new SetIndex(index, power);

  /* Manual testing autonomous files built with PathPlanner */
  // private Command autonTesting = drivetrain.getAutoPath("S3-N1-N8-ShotC-N7");

  /* Autonomous Chooser*/
  SendableChooser<Command> autoChooser;

  // Constructor of the class
  public RobotContainer() {

    /*Pathplanner Named Commands. 
    See notes at end of class for more information */
    NamedCommands.registerCommand("Shoot", new RevAndShootCommand(index, shooter));
    NamedCommands.registerCommand("IntakeOn", new IntakeCommandGroup(index, intake));
    NamedCommands.registerCommand("ArmHome", new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE));
    NamedCommands.registerCommand("ArmLow", new SetArmPosition(arm, Constants.ArmConstants.ARM_LOW_POSE)); 

    /* Autos */
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    /* Unintended side effect is this will create EVERY auton file from the RIO deploy folder. See solution below */

    indexIncrent = new IncrementIndex1Stage(index);

    shooter = new ShooterSubsystemVelocity();


 

    index.setDefaultCommand(index.run(() -> index.SetPower(BumperStatus(1))));
 
    configureBindings();
    DebugMethodSingle();
  }

  private void configureBindings() {

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
      // Drive forward with negative Y (forward)
        drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) 
            .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
        ));

    // m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    /* m_driverController.b().whileTrue(drivetrain
        .applyRequest(() -> point
            .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));
    */
    
    // reset the field-centric heading
    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    /* This command call works now. Not sure if there are advantages/disadvantages to one or the other */
    /* m_driverController.y().whileTrue(new SetArmPosition(arm, 15)); */
    m_driverController.a().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_HOME_POSE)));
    m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_LOW_POSE)));
    m_driverController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_AMP_POSE)));
    m_driverController.y().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_MID_POSE)));

    m_driverController.rightBumper().whileTrue(new IntakeCommandGroup(index, intake));
    m_driverController.leftBumper().whileTrue(new IntakeRevCommandGroup(index, intake));

    m_driverController.rightTrigger().whileTrue(new RevAndShootCommand(index, shooter));
    m_driverController.rightTrigger().whileFalse(new InstantCommand(() -> shooter.SetOutput(0)));
    m_driverController.leftTrigger().whileTrue(new SetIndex(index,-0.75));

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
    return autoChooser.getSelected();
  }
} // End of class



/* NOTES & REFERENCES
/* Pathplanner
/* Named commands must be registered before the creation of any 
  PathPlanner Autos or Paths. It is recommended to do this in RobotContainer, 
  after subsystem initialization, but before the creation of any other commands.
  REFERENCE https://pathplanner.dev/pplib-named-commands.html
  */

  /* SSH into Rio
  Using the easy button for auton has side effects.
  (https://pathplanner.dev/pplib-build-an-auto.html#create-a-sendablechooser-with-all-autos-in-project)
  Remedy is to SSH into the Rio and delete the autos you don't want.
  https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-ssh.html
  */