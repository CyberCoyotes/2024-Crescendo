package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.experimental.ShooterSubsystem2;
import frc.robot.subsystems.IndexSubsystem;

public class SetShooter extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter;
    private final double velocity;

    public SetShooter(ShooterSubsystem2 shooter, double velocity) {
        this.shooter = shooter;
        this.velocity = velocity;

        addRequirements(shooter); 
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        shooter.setShooterVelocity(velocity);

    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}