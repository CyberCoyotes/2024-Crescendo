package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ArmPositionAndShoot extends SequentialCommandGroup {
    // The time to rev the shooter
    double time = 0.2;
    double velo = 60;

    public ArmPositionAndShoot(ArmSubsystem arm, ShooterSubsystemVelocity shooter, IndexSubsystem index, int pose) {
        addCommands(new ArmPose(arm, pose), new IncrementIndex1Stage(index), new ParallelCommandGroup(
                new RunShooter(shooter),
                new WaitUntilCommand(() -> shooter.AtVelocity(velo)).andThen(() -> index.RunIndexing(), index)));
    }
}
