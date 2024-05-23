package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.util.IndexConstants;
import frc.robot.util.IntakeConstants;

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
