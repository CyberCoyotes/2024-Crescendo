package frc.robot.intake;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * Run Once or Continuously
 */
public class RunIntake extends Command {
    private IntakeSubsystem intake;

    public RunIntake(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

    @Override
    public void execute() {
        intake.powerIntake(-0.50);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}