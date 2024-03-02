package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

/**
 * RevAndShootCommand
 */
public class RevAndShootCommand extends SequentialCommandGroup {
    // The time to try to and shoot the cargo before executing..
    public int TimeToWait = 1;
    // subsystems
    IndexSubsystem indexer;
    ShooterSubsystemVelocity shooter;
    // component Commands

    private Command revUpShooter;
    private Command indexCommand;

    public RevAndShootCommand(IndexSubsystem indexer) {

        SetupCommands();

    }

    private void SetupCommands() {

        this.addCommands(new ParallelCommandGroup(new RunCommand(() -> shooter.SetOutput(100), shooter),
                new SequentialCommandGroup(new WaitUntilCommand(() -> shooter.AtVelocity(100))).andThen(indexCommand)));

    }
}