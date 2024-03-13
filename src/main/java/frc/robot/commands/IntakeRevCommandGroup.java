package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeRevCommandGroup extends SequentialCommandGroup {
    // LED to unloaded color
    // Intake + Index in parallel.
    // Note triggers the note sensor (ToF), turn off both intake and index,
    // Set LED status to loaded and to dashboard

    SetIntake runIntake;
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeRevCommandGroup(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    // Run Intake in reverse
                
                    new SetIntake(intake, 0.75),  
                    // Run Index in reverse
                    new SetIndex(index, -0.75)
        ));

    }

}
