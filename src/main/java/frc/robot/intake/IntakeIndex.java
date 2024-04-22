package frc.robot.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.index.SetIndex;
import frc.robot.util.NoteSensorSubsystem;

public class IntakeIndex extends SequentialCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;
    NoteSensorSubsystem notesensor;

    public IntakeIndex(IndexSubsystem index, IntakeSubsystem intake, NoteSensorSubsystem notesensor) {
        this.index = index;
        this.intake = intake;
        this.notesensor = notesensor;

        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, IntakeConstants.INTAKE_POWER), 
                    new SetIndex(index, IndexConstants.INDEX_POWER)).until(() -> index.hasCargo()));
                    // notesensor.isNoteLoaded()
    }

}
