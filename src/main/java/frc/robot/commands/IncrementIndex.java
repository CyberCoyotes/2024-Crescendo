package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;

public class IncrementIndex extends Command {

    IndexSubsystem indexer;

    public IncrementIndex(IndexSubsystem indexer) {
        this.indexer = indexer;

        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.setIndexPower(-0.1);
    }

    @Override
    public boolean isFinished() {
        // Check if there is no note loaded. i.e. HasCargo() returns false
        return !indexer.hasCargo();
    }

}
