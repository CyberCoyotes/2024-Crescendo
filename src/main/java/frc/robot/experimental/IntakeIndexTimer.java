package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.index.SetIndex;
import frc.robot.intake.IntakeConstants;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.intake.SetIntake;

public class IntakeIndexTimer extends ParallelCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeIndexTimer(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        
        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, IntakeConstants.INTAKE_POWER).withTimeout(1), 
                    /* 
                    Problem with using a timer with index and intake, it will run as soon as its called
                    at the start of a path follow command. Paths are different lengths and therefore elapsed times.
                    Need to rethink this.
                    */ 
                    new SetIndex(index, IndexConstants.INDEX_POWER)).withTimeout(1));
    }

}
