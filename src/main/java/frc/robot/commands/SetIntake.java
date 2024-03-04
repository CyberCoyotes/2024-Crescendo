package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

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
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }

    @Override
    public void execute() {
        intake.Run(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.Run(0);
    }
}