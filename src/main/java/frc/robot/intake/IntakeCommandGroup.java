package frc.robot.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.index.IndexSubsystem;

public class IntakeCommandGroup extends SequentialCommandGroup {
    // LED to unloaded color
    // Intake + Index in parallel.
    // Note triggers the note sensor (ToF), turn off both intake and index,
    // Set LED status to loaded (and shuffleboard)

    RunIntake runIntake;
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeCommandGroup(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;
        InitSubCommands();

        addCommands(
                new ParallelCommandGroup(
                    runIntake, 
                    (new RunCommand(() -> index.runIndexing(), 
                    index))).until(() -> index.hasCargo()));
                    // new IncrementIndex1Stage(index)); // too finicky at momement

    }

    private void InitSubCommands() {
        runIntake = new RunIntake(intake);
    }
}
