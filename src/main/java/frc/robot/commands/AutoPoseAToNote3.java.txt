package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.Constants;
import frc.robot.commands.SetIntake;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.auto.*;
import com.pathplanner.lib.util.*;
import com.pathplanner.lib.path.*;
import com.pathplanner.lib.controllers.*;

public class AutoPoseAToNote3 extends ParallelCommandGroup {

    IntakeSubsystem intake;
    IndexSubsystem index;

    public AutoPoseAToNote3(IndexSubsystem index, IntakeSubsystem intake, String path) {
        this.index = index;
        this.intake = intake;

        addCommands(
            new ParallelCommandGroup(
                new SetIntake(intake, Constants.IntakeConstants.INTAKE_POWER), 
                new SetIndex(index, Constants.IndexConstants.INDEX_POWER)).until(() -> index.HasCargo()),
               new InstantCommand(() -> PathPlannerPath.fromPathFile("PoseA-N3"))
                );
    }

   
}
