package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
// import frc.robot.experimental.IncrementIndex;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

@SuppressWarnings("unused")

/**
 * RevAndShootCommand
 */
public class RevAndShootEndsCommand extends SequentialCommandGroup {
    // The time to try to and shoot the cargo before executing..
    // subsystems
    IndexSubsystem indexer;
    ShooterSubsystemVelocity shooter;
    // component Commands
    // private IncrementIndex outtaMyWay;
    private Command revUpShooter;
    private Command indexCommand;

    public RevAndShootEndsCommand(IndexSubsystem indexer) {
        // Set up our subsystems
        this.indexer = indexer;
        // outtaMyWay = new IncrementIndex(indexer);
    }

    public RevAndShootEndsCommand(IndexSubsystem indexer, ShooterSubsystemVelocity shooter) {
        addRequirements(indexer, shooter);
        indexCommand = new RunCommand(() -> indexer.runIndexing(), indexer);
        this.shooter = shooter;
        SetupCommands();

    }

    private void SetupCommands() {
        this.addCommands(
                new ParallelCommandGroup(
                        new RunCommand(() -> shooter.SetOutput(Constants.ShooterConstants.SHOOTER_VELOCITY), shooter),
                        new SequentialCommandGroup(
                                new WaitUntilCommand(
                                        () -> shooter.AtVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY - 1)))
                                .andThen(indexCommand))
                        .until(() -> !indexer.hasCargo()).andThen(new WaitCommand(.1)));

    }
}