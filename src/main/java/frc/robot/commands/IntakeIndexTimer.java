package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.util.IndexConstants;
import frc.robot.util.IntakeConstants;

public class IntakeIndexTimer extends SequentialCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeIndexTimer(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, IntakeConstants.INTAKE_POWER), 
                    new SetIndex(index, IndexConstants.INDEX_POWER).withTimeout(2.5)));
    }

}
