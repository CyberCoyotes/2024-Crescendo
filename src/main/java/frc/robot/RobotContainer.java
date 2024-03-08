// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.IntakeRevCommandGroup;
import frc.robot.commands.RevAndShootCommand;
import frc.robot.commands.RunShooter;
import frc.robot.commands.SetArmClimb;
import frc.robot.commands.SetIndex;
import frc.robot.commands.SetWinch;
import frc.robot.commands.ShootClose;
import frc.robot.commands.ShooterIndex;
import frc.robot.experimental.IntakeIndex;
import frc.robot.experimental.StopShooting;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.subsystems.WinchSubsystem2;

// Getting rid of the soft yelling
@SuppressWarnings("unused")
public class RobotContainer {

  RunShooter shooterRun;

  // private final Telemetry logger = new
  // Telemetry(Constants.SystemConstants.MAX_SPEED);
  // #endregion
  // #region Network Tables

  // Interactable way to change increment distance on arm for High Speed High
  // Fidelity Testing

  // #endregion Network Tables
  // #region Subsystems

  /* Subsystems */
  ShooterSubsystemVelocity shooter = new ShooterSubsystemVelocity();
  IntakeSubsystem intake = new IntakeSubsystem();
  IndexSubsystem index = new IndexSubsystem();
  // OrchestraSubsystem daTunes;
  WinchSubsystem2 winch = new WinchSubsystem2(); // TODO Test
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

  /* Autonomous Chooser*/
  SendableChooser<Command> autoChooser;

  // Constructor of the class
  public RobotContainer() {

    /*Pathplanner Named Commands*/
            
    // NamedCommands.registerCommand("RunShooter", new RunShooter(shooter));
    // NamedCommands.registerCommand("Shoot", new ShooterIndex(shooter, index));
    NamedCommands.registerCommand("ShootClose", new ShootClose(arm, index, intake, shooter));
    // NamedCommands.registerCommand("StopShooting", new StopShooting(arm, index, intake, shooter));;
    NamedCommands.registerCommand("Intake", new IntakeIndex(index, intake));
    // NamedCommands.registerCommand("StopIntake", new StopIntakeIndex(index, intake));
    // NamedCommands.registerCommand("ArmHome", new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE));
    // NamedCommands.registerCommand("ArmLow", new SetArmPosition(arm, Constants.ArmConstants.ARM_LOW_POSE));
    // NamedCommands.registerCommand("ArmMid", new SetArmPosition(arm, Constants.ArmConstants.ARM_MID_POSE));
    
    /* Auto Chooser */
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    /* Unintended side effect is this will create EVERY auton file from the RIO deploy folder. See solution below */

    // FIXME I don't know if this is needed but seems undefined 
    // indexIncrent = new IncrementIndex1Stage(index);
    
    shooter = new ShooterSubsystemVelocity();

    index.setDefaultCommand(index.run(() -> index.SetPower(BumperStatus(1))));

/* 

    arm.setDefaultCommand(
        arm.run(() -> arm.Drive(((m_operatorController.axisLessThan(Axis.kLeftY.value,
            -0.1).getAsBoolean() ||
            (m_operatorController.axisGreaterThan(Axis.kLeftY.value, 0.1))
                .getAsBoolean()) ? m_operatorController.getLeftY() : 0))));
*/

    // // intake run depending on driver bumper status
    // ORIGINAL intake.setDefaultCommand(intake.run(() -> intake.Run(0.75 *
    // -BumperStatus(0))));
    // intake.setDefaultCommand(intakeGroupCommand);

    index.setDefaultCommand(index.run(() -> index.SetPower(BumperStatus(1))));
    /*
     * shooter.setDefaultCommand(shooter.run(() -> shooter.SetOutput(
     * // ! cool but unintuitive
     * Math.max(m_operatorController.getLeftTriggerAxis() * 0.5 * 60,
     * m_operatorController.getRightTriggerAxis() * 60))));
     */
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
    /*
     * m_driverController.b().whileTrue(drivetrain
     * .applyRequest(() -> point
     * .withModuleDirection(new Rotation2d(-m_driverController.getLeftY(),
     * -m_driverController.getLeftX()))));
     */

    // reset the field-centric heading on left bumper press
    m_driverController.start().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    m_driverController.a().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_HOME_POSE)));
    m_driverController.b().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_LOW_POSE)));
    m_driverController.x().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_AMP_POSE)));
    // m_driverController.y().whileTrue(new InstantCommand(() -> arm.setArmPose(Constants.ArmConstants.ARM_MID_POSE)));
    // m_driverController.y().whileTrue(new AutoPoseAToNote3(index, intake, "PoseA-N3"));

    m_driverController.rightBumper().whileTrue(new IntakeCommandGroup(index, intake));
    m_driverController.leftBumper().whileTrue(new IntakeRevCommandGroup(index, intake));
    m_driverController.rightTrigger().whileTrue(new RevAndShootCommand(index, shooter));
    m_driverController.rightTrigger().whileFalse(new InstantCommand(() -> shooter.SetOutput(0)));
    m_driverController.leftTrigger().whileTrue(new SetIndex(index,-0.75));

    // TODO Moves the arm with an open loop, so it shouldn't hold position
    // VoltageOut failed to stop the arm
    // PositionDutyCycle

    m_driverController.povUp().whileTrue(new SetArmClimb(arm,Constants.ArmConstants.ARM_MANUAL_POWER));

    m_operatorController.y().whileTrue(new SetWinch(winch, Constants.WinchConstants.WINCH_POWER));
    m_operatorController.b().whileTrue(new SetWinch(winch, 0.10));

  };

  public void DebugMethodSingle() {
    // #region Driving

    // #endregion Driving
    // #region Testing

    // Less useful logs that we still need to see for testing.

    // var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");
    var driverDiagnostics = Shuffleboard.getTab("Driver Diagnostics");

    driverDiagnostics.add(autoChooser);

    driverDiagnostics.addBoolean("Note Detected", () -> index.HasCargo());
    // driverDiagnostics.addDouble("Arm Rot", () -> arm.GetArmPos().getValueAsDouble());
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