package frc.robot.commands;

import java.lang.reflect.Executable;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class RevShooterCommand extends Command {
    private ShooterSubsystem shooter;

    public RevShooterCommand() {

    }

    @Override
    public void execute() {
        shooter.SetVelocity(shooter.MaxVelocity);
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
