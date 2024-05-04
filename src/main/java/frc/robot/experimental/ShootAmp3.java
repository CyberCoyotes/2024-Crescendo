package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.shooter.ShootAmp;
import frc.robot.shooter.ShooterConstants;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.util.NoteSensor;

@SuppressWarnings("unused")

public class ShootAmp3 extends SequentialCommandGroup {
    private ShooterSubsystem shooter;
    private IndexSubsystem index;
    private NoteSensor notesensor;

    public ShootAmp3(ShooterSubsystem shooter, IndexSubsystem index, NoteSensor notesensor) {
        this.shooter = shooter;
        this.index = index;
        this.notesensor = notesensor;

        addCommands(
            new InstantCommand(() -> shooter.setFlywheelToIdle(), shooter).withTimeout(1),            
            // new WaitCommand(200),
            new InstantCommand(() -> index.setIndexPower(IndexConstants.INDEX_POWER), index).withTimeout(3)
            // new WaitUntilCommand(notesensor::isNoteLoaded),
            /* 
            new WaitCommand(0.6),
            new InstantCommand(() -> {
                shooter.setFlywheelVelocity(0);
                index.stopIndexing();
            }, shooter, index)
            */
        );
    }
}