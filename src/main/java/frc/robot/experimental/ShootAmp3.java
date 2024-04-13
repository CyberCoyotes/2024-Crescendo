package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ShootAmp;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.IndexConstants;
import frc.robot.util.ShooterConstants;

public class ShootAmp3 extends SequentialCommandGroup {
    private ShooterSubsystem2 shooter2;
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public ShootAmp3(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        this.shooter2 = shooter2;
        this.index = index;
        this.notesensor = notesensor;

        addCommands(
            new InstantCommand(() -> shooter2.setFlywheelToIdle(), shooter2).withTimeout(1),            
            // new WaitCommand(200),
            new InstantCommand(() -> index.setIndexPower(IndexConstants.INDEX_POWER), index).withTimeout(3)
            // new WaitUntilCommand(notesensor::isNoteLoaded),
            /* 
            new WaitCommand(0.6),
            new InstantCommand(() -> {
                shooter2.setFlywheelVelocity(0);
                index.stopIndexing();
            }, shooter2, index)
            */
        );
    }
}