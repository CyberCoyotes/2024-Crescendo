package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.experimental.ShooterSubsystem2;
import frc.robot.subsystems.IndexSubsystem;

public class ShootWhenReady extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter;
    private final IndexSubsystem index;


    public ShootWhenReady(ShooterSubsystem2 shooter, IndexSubsystem index) {
        this.shooter = shooter;
        this.index = index;
        addRequirements(shooter); 
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (shooter.isShooterAtTargetVelocity() & index.HasCargo()) {
            index.RunIndexing();
        } else {
            index.SetPower(0);
        }

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