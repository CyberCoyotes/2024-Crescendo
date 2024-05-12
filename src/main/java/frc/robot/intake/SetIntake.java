package frc.robot.intake;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Run Once or Continuously
 */
public class SetIntake extends Command {
    private IntakeSubsystem intake;
    private double power;

    public SetIntake(IntakeSubsystem intake, double power) {
        this.intake = intake;
        this.power = power;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        intake.powerIntake(this.power);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      intake.stopIntake();
    }
  
    @Override
    public boolean isFinished() {
      return false;
    }
}