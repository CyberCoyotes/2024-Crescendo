// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.RatioMotorSubsystem;

import frc.robot.subsystems.OrchestraSubsystem;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.TalonFX;
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
import frc.robot.subsystems.OrchestraSubsystem.Song;

public class RobotContainer {
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
  RatioMotorSubsystem shooter;
  OrchestraSubsystem daTunes;
  // #endregion Subsystems

  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  public RobotContainer() {

    shooterMotorMain = new TalonFX(Constants.mainShooterCAN);
    shooterMotorSub = new TalonFX(Constants.subShooterCAN);

    // #region some configs

    shooterMotorMain.setInverted(false);
    shooterMotorSub.setInverted(true);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);
    shooterMotorMain.setNeutralMode(NeutralModeValue.Coast);

    flywheelLeft = new TalonFX(Constants.CANIDs.leftFlywheelCAN);
    flywheelRight = new TalonFX(Constants.CANIDs.rightFlywheelCAN);
    //Orchestra
    bassGuitar = new TalonFX(Constants.CANIDs.bassGuitar);


    // #endregion

    shooter = new RatioMotorSubsystem(shooterMotorMain, shooterMotorSub);
    shooter.SetStatePower(1);
    shooter.SetRatio(1);

    // Configure the trigger bindings

    configureBindings();
  }

  // tl;dr: Trigger class for simple booleans
  private void configureBindings() {


    // WOW This is bad but oh well
    m_driverController.y().onTrue(new InstantCommand(() -> shooter.Toggle(),
        shooter));

  }

  public void DebugMethodSingle() {
    var tab = Shuffleboard.getTab("Driver Diagnostics");
    tab.addBoolean("Shooter Running", () -> shooter.Running());
  }

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
    drivetrain.applyRequest(() -> drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with
                                                                                       // negative Y (forward)
        .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
        .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
    ));

    m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
    m_driverController.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))));

    // reset the field-centric heading on left bumper press
    m_driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

        //WOW This is bad but oh well
        m_driverController.y().onTrue(new InstantCommand( () -> flywheel.Toggle(), flywheel));
        m_driverController.b().onTrue(new InstantCommand( () -> daTunes.Play(), daTunes));
        m_driverController.a().onTrue(new InstantCommand( () -> daTunes.Shud(), daTunes));   
    
    }


  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new WaitCommand(2);
  } // end of Autonomous

} // end of class
