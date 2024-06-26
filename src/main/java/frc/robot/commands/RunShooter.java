package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.util.ShooterConstants;

public class RunShooter extends Command {
    private ShooterSubsystemVelocity shooter;

    public RunShooter(ShooterSubsystemVelocity shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.SetOutput(ShooterConstants.FLYWHEEL_VELOCITY);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.Disable();
    }
}
