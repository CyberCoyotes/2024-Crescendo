package frc.robot.commands;

import java.lang.reflect.Executable;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.ShooterSubsystemVelocity;

public class RevShooterCommand extends Command {
    private ShooterSubsystemVelocity shooter;

    public RevShooterCommand() {

    }

    @Override
    public void execute() {
        shooter.Enable();
    }

    @Override
    public boolean isFinished() {
        return shooter.IsVelocityReqMet();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.Disable();
    }
}
