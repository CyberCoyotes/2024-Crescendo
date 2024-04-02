package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.Constants;
import frc.robot.util.IndexConstants;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.util.ShooterConstants;

@SuppressWarnings("unused")

public class ShootWhenReadyAmp extends Command {
    private ShooterSubsystem2 shooter2; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootWhenReadyAmp(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter2 = shooter2;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter2, index, notesensor); 
    }

    @Override
    public void initialize() {
        shooter2.setFlywheelToIdle();
    }

    @Override
    public void execute() {
        /* 
        * Turn on the flywheel `setTargetFlywheelVelocity`
        * Check if flywheel has reached target velocity `isFlywheelNominal`
        * If true, index the game piece forward
        * Else, setPower of Index to zero and continue running the flywheel until `isFlywheelNominal` is true
         */

        shooter2.setFlywheelVelocityAmp(ShooterConstants.FLYWHEEL_VELOCITY_AMP);

        if (shooter2.isFlywheelNominalAmp()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        } else {
            index.stopIndexing();
            // shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_VELOCITY);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
} // end of class ShootWhenReady