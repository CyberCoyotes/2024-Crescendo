package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem2;

public class SetShooterVelocity extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter2;
    private final double velocity;

    public SetShooterVelocity(ShooterSubsystem2 shooter2, double velocity) {
        this.shooter2 = shooter2;
        this.velocity = velocity;

        addRequirements(shooter2);
         
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        shooter2.setFlywheelVelocity(velocity);

    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}