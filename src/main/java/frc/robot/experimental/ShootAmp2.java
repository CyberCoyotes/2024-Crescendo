package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.shooter.FlywheelConstants;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.util.NoteSensorSubsystem;

/* A command that turns on the flywheel with the method `setFlywheelVelocityAmp` with a value of `FLYWHEEL_VELOCITY_AMP` 
 * After a 500ms delay, the command then runs the method `setIndexPower` to advance the game piece forward into the flywheel-shooter
 */

public class ShootAmp2 extends Command {
    private ShooterSubsystem shooter; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootAmp2(ShooterSubsystem shooter, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter = shooter;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter, index, notesensor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.setFlywheelVelocityAmp(FlywheelConstants.FLYWHEEL_VELOCITY_AMP);
        index.setIndexPower(IndexConstants.INDEX_POWER);        
    }



    @Override
    public boolean isFinished() {
        return (notesensor.isNoteLoaded());
        // return false; // default command and will run until interrupted by not pressing a button-trigger
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setFlywheelVelocity(0);
        index.stopIndexing();
        // Add any necessary cleanup code here
    }
} // end of class
