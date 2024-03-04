package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;

/**
 * Run Once or Continuously
 */
public class IndexAuton extends Command {
    private IndexSubsystem index;
    private double power;

    public IndexAuton(IndexSubsystem index, double power) {
        this.index = index;
        this.power = power;
        addRequirements(this.index);
    }

    @Override
    public void initialize() {
      index.SetPower(this.power);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      index.SetPower(0);
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }

}