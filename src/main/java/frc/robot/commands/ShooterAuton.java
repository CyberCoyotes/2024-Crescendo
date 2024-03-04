package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShooterAuton extends Command {
    private ShooterSubsystemVelocity shooter;
    private double output;

    public ShooterAuton(ShooterSubsystemVelocity shooter, double output) {
        this.shooter = shooter;
        this.output = output;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.SetOutput(this.output);
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