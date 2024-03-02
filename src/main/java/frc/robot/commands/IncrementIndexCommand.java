package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;

public class IncrementIndexCommand extends Command {

    IndexSubsystem indexer;

    public IncrementIndexCommand(IndexSubsystem indexer) {
        this.indexer = indexer;

        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.SetPower(-0.25);
    }

    @Override
    public boolean isFinished() {
        // true when the cargo isn't detected
        return !indexer.HasCargo();
    }

}
