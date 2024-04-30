package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants;
import frc.robot.util.NoteSensorSubsystem;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;

@SuppressWarnings("unused")

public class ShootAmp extends Command {
    private ShooterSubsystem shooter; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootAmp(ShooterSubsystem shooter, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter = shooter;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter, index, notesensor); 
    }

    @Override
    public void initialize() {
        shooter.setFlywheelToIdle();
    }

    @Override
    public void execute() {
        /* 
        * Turn on the flywheel `setTargetFlywheelVelocity`
        * Check if flywheel has reached target velocity `isFlywheelNominal`
        * If true, index the game piece forward
        * Else, setPower of Index to zero and continue running the flywheel until `isFlywheelNominal` is true
         */

        shooter.setFlywheelVelocityAmp(ShooterConstants.FLYWHEEL_VELOCITY_AMP);

        if (shooter.isFlywheelNominalAmp()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        } else {
            index.stopIndexing();
            // shooter.setFlywheelVelocity(shooter.FLYWHEEL_VELOCITY);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
} // end of class AutoShootAmp