package frc.robot.experimental;

import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;

public class ShootWhenReadyV3 {
    private ShooterSubsystem2 shooter;
    private IndexSubsystem index;

    public ShootWhenReadyV3(ShooterSubsystem2 shooter, IndexSubsystem index) {
        this.shooter = shooter;
        this.index = index;
    }

    public void execute() {
        // Turn on the shooter with setTargetFlywheelVelocity
        shooter.setTargetFlywheelVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY);

        // After the flywheel is at target velocity, index the game piece forward
        if (shooter.isFlywheelAtTarget() && index.HasCargo()) {
            index.SetPower(Constants.IndexConstants.INDEX_POWER);
        }

        // After index.HasCargo() is false, stop the shooter and index
        else if (!index.HasCargo()) {
            shooter.setTargetFlywheelVelocity(0);
            index.SetPower(0);
        }
    }
}