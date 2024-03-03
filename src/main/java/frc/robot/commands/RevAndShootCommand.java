package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

/**
 * RevAndShootCommand
 */
public class RevAndShootCommand extends SequentialCommandGroup {
    // The time to try to and shoot the cargo before executing..
    // subsystems
    IndexSubsystem indexer;
    ShooterSubsystemVelocity shooter;
    // component Commands

    private Command indexCommand;

    public RevAndShootCommand(IndexSubsystem indexer, ShooterSubsystemVelocity shooter) {
        addRequirements(indexer, shooter);
        indexCommand = new RunCommand(() -> indexer.RunIndexing(), indexer);
        this.shooter = shooter;
        SetupCommands();

    }

    private void SetupCommands() {
        this.addCommands(new ParallelCommandGroup(new RunCommand(() -> shooter.SetOutput(60), shooter),
                new SequentialCommandGroup(new WaitUntilCommand(() -> shooter.AtVelocity(59))).andThen(indexCommand)));

    }
}