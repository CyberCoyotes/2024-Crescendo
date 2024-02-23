// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class RobotContainer {

  private final ArmSubsystem m_armSub = new ArmSubsystem();

  private final ShooterSubsystem m_shooterSub = new ShooterSubsystem();

  private final IndexSubsystem m_indexSub = new IndexSubsystem();

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

    // reset the field-centric heading on left bumper press
    // joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    joystick.leftBumper().onTrue(m_armSub.runOnce(() -> m_armSub.setArmMotionMagicVoltage(0.0)));

    // joystick.rightBumper().onTrue(m_armSub.runOnce(() -> m_armSub.setArmMotionMagicVoltage(5.0)));

    // joystick.rightBumper().whileTrue(new InstantCommand(
                                      // () -> m_armSub.setArmMotionMagicVoltage(5.0)));


    joystick.rightBumper().whileTrue(new SequentialCommandGroup(
                                            new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(5.0)),
                                            new InstantCommand(() -> m_shooterSub.setShooter(20.0)),
                                            new InstantCommand(() -> m_indexSub.SetPower(0.5))));

    joystick.rightBumper().whileFalse(new SequentialCommandGroup(
                                            // new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(5.0)),
                                            new InstantCommand(() -> m_shooterSub.setShooter(0.0)),
                                            new InstantCommand(() -> m_indexSub.SetPower(0.0))));

    // joystick.x().whileTrue(new InstantCommand(() -> m_shooterSub.setShooter(20)));
    joystick.x().whileFalse(new InstantCommand(() -> m_shooterSub.setShooter(0)));

    joystick.povDown().onTrue(new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(Constants.ArmConstants.ARM_REV_LIMIT)));
    joystick.povLeft().onTrue(new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(Constants.ArmConstants.ARM_LOW_POSE)));
    joystick.povRight().onTrue(new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(Constants.ArmConstants.ARM_MID_POSE)));
    joystick.povUp().onTrue(new InstantCommand(() -> m_armSub.setArmMotionMagicVoltage(Constants.ArmConstants.ARM_HIGH_POSE)));

        
    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }

  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
