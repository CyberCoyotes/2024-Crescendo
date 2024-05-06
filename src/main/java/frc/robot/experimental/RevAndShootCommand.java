package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.index.IndexSubsystem;
import frc.robot.shooter.FlywheelConstants;
import frc.robot.shooter.ShooterSubsystem;

@SuppressWarnings("unused")

/**
 * RevAndShootCommand
 */
public class RevAndShootCommand extends SequentialCommandGroup {
    // The time to try to and shoot the cargo before executing..
    // subsystems
    IndexSubsystem indexer;
    ShooterSubsystem shooter;
    // component Commands
    private IncrementIndex outtaMyWay;
    private Command revUpShooter;
    private Command indexCommand;

    public RevAndShootCommand(IndexSubsystem indexer) {
        // Set up our subsystems
        this.indexer = indexer;
        outtaMyWay = new IncrementIndex(indexer);
    }
   

    public RevAndShootCommand(IndexSubsystem indexer, ShooterSubsystem shooter) {
        addRequirements(indexer, shooter);
        indexCommand = new RunCommand(() -> indexer.runIndexing(), indexer);
        this.shooter = shooter;
        SetupCommands();

    }
    

    private void SetupCommands() {
        this.addCommands(
                new ParallelCommandGroup(
                        new RunCommand(() -> shooter.setFlywheelVelocity(FlywheelConstants.FLYWHEEL_VELOCITY))),
                        new SequentialCommandGroup(
                                // new WaitUntilCommand(() -> shooter.AtVelocity(ShooterConstants.FLYWHEEL_VELOCITY-1))).andThen(indexCommand)
                                ));

    }
}