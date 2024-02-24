package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * Run Once or Continuously
 */
public class SetIntakeRunning extends Command {
    private IntakeSubsystem intake;

    public SetIntakeRunning(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

    @Override
    public void execute() {
        intake.Run(1);
    }
}
