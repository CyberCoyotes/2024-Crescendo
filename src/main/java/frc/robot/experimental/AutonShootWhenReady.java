package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.NoteSensorSubsystem;

public class AutonShootWhenReady extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter2;
    private final IndexSubsystem index;
    private final NoteSensorSubsystem notesensor;

    public AutonShootWhenReady(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
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

        shooter2.setFlywheelVelocity(Constants.ShooterConstants.SHOOTER_VELOCITY);
        if (shooter2.isFlywheelNominal() & notesensor.isNoteLoaded()==true) {
            index.SetPower(Constants.IndexConstants.INDEX_POWER);
        } else { 
            (notesensor.isNoteLoaded()==false); {
                index.SetPower(0);
            }
            // shooter2.setFlywheelVelocity(0); Would this turn off the shooter before getting up to speed?
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
}