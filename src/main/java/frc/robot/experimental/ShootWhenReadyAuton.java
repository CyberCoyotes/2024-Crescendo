package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.ShooterSubsystemVelocity;
import frc.robot.subsystems.NoteSensorSubsystem;

@SuppressWarnings("unused")

public class ShootWhenReadyAuton extends Command {
    private final ShooterSubsystem2 shooter2;
    private final IndexSubsystem index;
    private final NoteSensorSubsystem notesensor;

    public ShootWhenReadyAuton(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter2 = shooter2;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter2, index, notesensor); 

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

        // Turn on the shooter with `setFlywheelVelocity`
        shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_VELOCITY);

        // Check if the flywheel is at target velocity AND if a game piece is loaded
        if (shooter2.isFlywheelNominal3() && notesensor.isNoteLoaded()) {
    
            // If the flywheel is at target velocity AND a game piece is loaded, index the game piece forward
             index.setPower(Constants.IndexConstants.INDEX_POWER);

                /* If a game piece is not loaded,
                * Set flywheel to idle speed
                * Stop the indexer */ 
                /*
                } else if (!notesensor.isNoteLoaded()) {
                    index.setPower(0);
                    shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_IDLE_VELOCITY);
                */

                // Has to be called a second time 
        } else {
            index.stopIndexing();
            
        } // end of if statement

    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        // If a note is not loaded, i.e. it has been fired, then finish the command
        if (!notesensor.isNoteLoaded()) {
            index.setPower(0);
            shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_IDLE_VELOCITY);
            return true;
        }
        // Note is still loaded, keep running the command
        return false;
    }

} // End of AutonShootWhenReady