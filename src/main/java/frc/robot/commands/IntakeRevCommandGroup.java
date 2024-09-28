package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.util.IndexConstants;
import frc.robot.util.IntakeConstants;

public class IntakeRevCommandGroup extends SequentialCommandGroup {
    // LED to unloaded color
    // Intake + Index in parallel.
    // ToF should NOT be part of this command like it does for `IntakeCommandGroup`

    SetIntake runIntake;
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeRevCommandGroup(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        addCommands(
                new ParallelCommandGroup(
                    // Run Intake in reverse
                    // This was set numerically to 0.75 but `INTAKE_POWER` is -0.75
                    // TODO Test reverse intake!
                    new SetIntake(intake, IntakeConstants.INTAKE_POWER),
                    // Run Index in reverse
                    new SetIndex(index, IndexConstants.INDEX_POWER_REV)
        ));

    }

}
