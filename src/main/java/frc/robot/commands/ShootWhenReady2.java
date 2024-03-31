package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.IndexConstants;
import frc.robot.subsystems.NoteSensorSubsystem;

public class ShootWhenReady2 extends Command{
    private ShooterSubsystem2 shooter2;
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootWhenReady2(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter2 = shooter2;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter2, index, notesensor);
    }

    @Override
    public void initialize() {
        // shooter2.setFlywheelToIdle();
    }

    public void execute() {
        // Turn on the shooter with setTargetFlywheelVelocity
        shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_VELOCITY);

        // After the flywheel is at target velocity, index the game piece forward
        if (shooter2.isFlywheelNominal() && notesensor.isNoteLoaded()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        }

        // After index.HasCargo() is false, stop the shooter and index
        else if (!notesensor.isNoteLoaded()) {
            shooter2.setFlywheelVelocity(0);
            index.stopIndexing();;
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
