package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.Constants;
import frc.robot.util.IndexConstants;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.util.ShooterConstants;

@SuppressWarnings("unused")

public class ShootStage extends Command {
    private ShooterSubsystem2 shooter2; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootStage(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
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

        shooter2.setFlywheelVelocity(ShooterConstants.FLYWHEEL_VELOCITY_STAGE);

        if (shooter2.isFlywheelNominal()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        } else {
            index.stopIndexing();
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
} // end of class 