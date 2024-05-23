package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;

public class SetIndex extends Command {
    private IndexSubsystem index;
    private double power;

    public SetIndex(IndexSubsystem index, double power) {
        this.index = index;
        this.power = power;
        addRequirements(this.index);
    }

    @Override
    public void initialize() {
      index.setIndexPower(this.power);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      index.setIndexPower(0);
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }

}
