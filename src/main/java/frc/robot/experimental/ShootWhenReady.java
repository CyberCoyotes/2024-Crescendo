package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;

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
        /* 
        Turn on the shooter with setTargetFlywheelVelocity
        After the flywheel is at target velocity, index the game piece forward
        After index.HasCargo() is false, stop the shooter and index
         */
        
         
        shooter.setTargetFlywheelVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY);
        if (// shooter.isFlywheelAtTarget() & 
                index.HasCargo()==true) {
            // index.RunIndexing();
            index.SetPower(Constants.IndexConstants.INDEX_POWER);
        } else {
            index.SetPower(0);
        }

    }

    @Override
    public void end(boolean interrupted) {
        shooter.setTargetFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}