package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IndexSubsystem;

public class IncrementIndex2Stage extends SequentialCommandGroup {
    public IncrementIndex2Stage(IndexSubsystem index) {
        addRequirements(index);
        // Test this
        addCommands(new IncrementIndex1Stage(index),
                new RunCommand(() -> index.RunIndexing(), index).until(() -> index.HasCargo()));
    }
}
