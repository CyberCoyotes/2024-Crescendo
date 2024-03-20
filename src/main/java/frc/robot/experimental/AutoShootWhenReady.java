package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.NoteSensorSubsystem;

public class AutoShootWhenReady extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter2;
    private final IndexSubsystem index;
    private final NoteSensorSubsystem notesensor;

    public AutoShootWhenReady(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
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

        // Turn on the shooter with setTargetFlywheelVelocity
        shooter2.setFlywheelVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY);

        // Check if the flywheel is at target velocity AND if a game piece is loaded
        if (shooter2.isFlywheelNominal()==true && notesensor.isNoteLoaded()==true) {
    
            // If the flywheel is at target velocity AND a game piece is loaded, index the game piece forward
             index.SetPower(Constants.IndexConstants.INDEX_POWER);

                // If a game piece is not loaded, slow down flywheel to idle and stop the indexer   
                } if (notesensor.isNoteLoaded()==false) {
                    index.SetPower(0);
                    shooter2.setFlywheelVelocity(Constants.ShooterConstants.SHOOTER_IDLE_VELOCITY);

                    // Has to be called a second time 
                    } if (shooter2.isFlywheelNominal()==true && notesensor.isNoteLoaded()==true) {
                            index.SetPower(Constants.IndexConstants.INDEX_POWER);  
            
        } // end of if statement

    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

} // End of AutonShootWhenReady.java