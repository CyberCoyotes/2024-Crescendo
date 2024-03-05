package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class StopIntakeIndex extends SequentialCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public StopIntakeIndex(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, 0), 
                    new SetIndex(index, 0)
                    ));
    }

}
