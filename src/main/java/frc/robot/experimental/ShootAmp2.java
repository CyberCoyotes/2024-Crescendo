package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.IndexConstants;
import frc.robot.util.ShooterConstants;

/* A command that turns on the flywheel with the method `setFlywheelVelocityAmp` with a value of `FLYWHEEL_VELOCITY_AMP` 
 * After a 500ms delay, the command then runs the method `setIndexPower` to advance the game piece forward into the flywheel-shooter
 */

public class ShootAmp2 extends Command {
    private ShooterSubsystem2 shooter2; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootAmp2(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
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
        shooter2.setFlywheelVelocityAmp(ShooterConstants.FLYWHEEL_VELOCITY_AMP);
        index.setIndexPower(IndexConstants.INDEX_POWER);        
    }



    @Override
    public boolean isFinished() {
        return (notesensor.isNoteLoaded());
        // return false; // default command and will run until interrupted by not pressing a button-trigger
    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
        index.stopIndexing();
        // Add any necessary cleanup code here
    }
} // end of class
