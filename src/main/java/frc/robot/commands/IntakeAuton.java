package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * Run Once or Continuously
 */
public class IntakeAuton extends Command {
    private IntakeSubsystem intake;
    private double power;

    public IntakeAuton(IntakeSubsystem intake, double power) {
        this.intake = intake;
        this.power = power;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        intake.Run(this.power);
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