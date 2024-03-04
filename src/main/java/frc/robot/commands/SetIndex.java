package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;


public class SetIndex extends Command {
    final IndexSubsystem indexSub;
    private final double power;

    public SetIndex(IndexSubsystem indexSub, double power) {
        this.indexSub = indexSub;
        this.power = power;
        addRequirements(indexSub);
    }

    @Override
    public void execute() {
        indexSub.SetPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        indexSub.SetPower(0);
    }
} // End of class Indexing