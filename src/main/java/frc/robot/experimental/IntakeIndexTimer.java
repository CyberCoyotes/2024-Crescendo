package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetIndex;
import frc.robot.commands.SetIntake;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.util.Constants;

public class IntakeIndexTimer extends ParallelCommandGroup {
    IndexSubsystem index;
    IntakeSubsystem intake;

    public IntakeIndexTimer(IndexSubsystem index, IntakeSubsystem intake) {
        this.index = index;
        this.intake = intake;

        
        addCommands(
                new ParallelCommandGroup(
                    new SetIntake(intake, Constants.IntakeConstants.INTAKE_POWER).withTimeout(1), 
                    /* 
                    Problem with using a timer with index and intake, it will run as soon as its called
                    at the start of a path follow command. Paths are different lengths and therefore elapsed times.
                    Need to rethink this.
                    */ 
                    new SetIndex(index, Constants.IndexConstants.INDEX_POWER)).withTimeout(1));
    }

}
