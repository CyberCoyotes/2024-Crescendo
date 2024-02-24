package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.IndexSubsystem;

/**
 * RevAndShootCommand
 */
public class RevAndShootCommand extends SequentialCommandGroup {
    // The time to try to and shoot the cargo before executing..
    public int TimeToWait = 1;
    // subsystems
    IndexSubsystem indexer;
    // component Commands
    private IncrementIndexCommand outtaMyWay;
    private Command revUpShooter;
    private Command indexCommand;

    public RevAndShootCommand(IndexSubsystem indexer) {
        // Set up our subsystems
        this.indexer = indexer;
        outtaMyWay = new IncrementIndexCommand(indexer);

        SetupCommands();

    }

    private void SetupCommands() {

        this.addCommands(outtaMyWay, revUpShooter,
                new ParallelDeadlineGroup(revUpShooter, indexCommand, new WaitCommand(TimeToWait)));
    }

}