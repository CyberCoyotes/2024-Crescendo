package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.ShooterSubsystemVelocity;

public class RunShooter extends Command {
    private ShooterSubsystemVelocity shooter;

    public RunShooter(ShooterSubsystemVelocity shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.SetOutput(1);
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
