package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.IndexConstants;
import frc.robot.util.ShooterConstants;

/* A command that turns on the flywheel with a method `setFlywheelVelocityAmp` with a value of `FLYWHEEL_VELOCITY_AMP` 
 * After a 0.5 delay, the command then runs the method `setIndexPower` to advance the game piece forward
 * 
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
        shooter2.setFlywheelVelocityAmp(ShooterConstants.FLYWHEEL_VELOCITY_AMP);
        withTimeout(600);
        index.setIndexPower(IndexConstants.INDEX_POWER);
    }

    @Override
    public void execute() {
    }



    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
        index.stopIndexing();
        // Add any necessary cleanup code here
    }
} // end of class
