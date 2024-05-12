package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class KillShooterCommand extends Command {
    private ShooterSubsystemVelocity shooter;

    public KillShooterCommand(ShooterSubsystemVelocity shooterArg) {
        shooter = shooterArg;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.Disable();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.Disable();
    }
}
