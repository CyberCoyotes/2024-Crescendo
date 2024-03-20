package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.NoteSensorSubsystem;

public class ShootWhenReady extends Command {
    // private ShooterSubsystemVelocity shooter;
    private final ShooterSubsystem2 shooter2;
    private final IndexSubsystem index;
    private final NoteSensorSubsystem notesensor;

    public ShootWhenReady(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
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

        if (shooter2.isFlywheelNominal()) { // and unneeded check because in teleop this wouldn't fire without a button press & notesensor.isNoteLoaded()==true) {
            index.SetPower(Constants.IndexConstants.INDEX_POWER);
        } else {
            index.SetPower(0);

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