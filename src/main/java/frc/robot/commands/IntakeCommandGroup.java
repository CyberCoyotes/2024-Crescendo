package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LedSubsystem;

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

        addCommands(new InstantCommand((() -> index.NoteSensor.setLEDColor()), index),
                new ParallelCommandGroup(runIntake, (new RunCommand(() -> index.RunIndexing(), index)))
                        .until(() -> index.HasCargo()),
                new InstantCommand((() -> index.NoteSensor.setLEDColor()), index));

    }

    private void InitSubCommands() {
        runIntake = new RunIntake(intake);
    }
}
