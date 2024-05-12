package frc.robot.xperimental;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.index.SetIndex;
import frc.robot.intake.IntakeConstants;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.intake.SetIntake;

public class IntakeIndexSmartTimer extends SequentialCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeIndexSmartTimer(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, IntakeConstants.INTAKE_POWER), 
                    new SetIndex(index, IndexConstants.INDEX_POWER).withTimeout(2.5)));
    }

}
