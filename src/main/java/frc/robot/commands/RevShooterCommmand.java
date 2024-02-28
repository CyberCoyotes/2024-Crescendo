package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class RevShooterCommmand extends Command {

    ShooterSubsystemVelocity shooter;
    int setpoint;

    public RevShooterCommmand(ShooterSubsystemVelocity shooter, int setpoint) {
        this.shooter = shooter;
        this.setpoint = setpoint;
    }

    @Override
    public void execute() {
        shooter.SetOutput(setpoint);
    }

    @Override
    public boolean isFinished() {
        return shooter.IsVelocityReqMet();
    }
}
