package frc.robot.experimental;

import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;

public class ShootWhenReady3 {
    private ShooterSubsystem2 shooter2;
    private IndexSubsystem index;

    public ShootWhenReady3(ShooterSubsystem2 shooter2, IndexSubsystem index) {
        this.shooter2 = shooter2;
        this.index = index;
    }

    public void execute() {
        // Turn on the shooter with setTargetFlywheelVelocity
        shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_VELOCITY);

        // After the flywheel is at target velocity, index the game piece forward
        if (shooter2.isFlywheelNominal() && index.hasCargo()) {
            index.setPower(Constants.IndexConstants.INDEX_POWER);
        }
        // After index.HasCargo() is false, stop the shooter and index
        else if (!index.hasCargo()) {
            shooter2.setFlywheelVelocity(0);
            index.setPower(0);
        }
    }
}
