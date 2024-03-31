package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.util.Constants;

public class IntakeIndex extends SequentialCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeIndex(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, Constants.IntakeConstants.INTAKE_POWER), 
                    new SetIndex(index, Constants.IndexConstants.INDEX_POWER)).until(() -> index.hasCargo()));
    }

}
