package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class SetShooter extends Command {
    private ShooterSubsystemVelocity shooter;
    private double velocity;

    public SetShooter(ShooterSubsystemVelocity shooter, double velocity) {
        this.shooter = shooter;
        this.velocity = velocity;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.SetOutput(this.velocity);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        shooter.SetOutput(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}