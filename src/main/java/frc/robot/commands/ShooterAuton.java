package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShooterAuton extends Command {
    private ShooterSubsystemVelocity shooter;

    public ShooterAuton(ShooterSubsystemVelocity shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.SetOutput(1);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
