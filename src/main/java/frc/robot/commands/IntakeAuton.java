package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * Run Once or Continuously
 */
public class IntakeAuton extends Command {
    private IntakeSubsystem intake;

    public IntakeAuton(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        intake.Run(0.75);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      intake.Run(0);
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }

  }