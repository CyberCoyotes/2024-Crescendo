package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;

/**
 * Run Once or Continuously
 */
public class IndexAuton extends Command {
    private IndexSubsystem indexSub;

    public IndexAuton(IndexSubsystem indexSub) {
        this.indexSub = indexSub;
        addRequirements(this.indexSub);
    }

    @Override
    public void initialize() {
      indexSub.SetPower(0.75);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      indexSub.SetPower(0);
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }

  }