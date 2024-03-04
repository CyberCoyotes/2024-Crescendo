package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;


public class ShootPoseA extends SequentialCommandGroup {
    public ShootPoseA(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE),
            new ParallelCommandGroup(
                new RunCommand(() -> shooter.SetOutput(Constants.ShooterConstants.SHOOTER_VELOCITY), shooter),
                new SequentialCommandGroup(
                    new WaitUntilCommand(() -> shooter.AtVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY))
                ).andThen(
                    new RunCommand(() -> index.SetPower(Constants.IndexConstants.INDEX_POWER), index)
                )
            )
        );
    }
}