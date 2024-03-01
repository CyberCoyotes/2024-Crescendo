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
        // Check if there is no note loaded
        // Will return true if indexer.HasCargo() is false (i.e., there is no cargo)... if I understand this correctly?
        return !indexer.HasCargo();
    }

}
