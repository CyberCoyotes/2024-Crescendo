package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ArmPositionAndShoot extends SequentialCommandGroup {
    // The time to rev the shooter
    double time = 0.2;
    double velo = 60;

    public ArmPositionAndShoot(ArmSubsystem arm, ShooterSubsystemVelocity shooter, IndexSubsystem index, int pose) {
        // ORIGINAL addCommands(new ArmPose(arm, pose), new IncrementIndex1Stage(index),
        // new ParallelCommandGroup(

        // Updated arm reference to SetArmPose
        addCommands(
                new SetArmPosition(arm, pose), // Set the arm to the desired pose
                new IncrementIndex1Stage(index), // Reverse index the note, presuming to clear the shooter wheels?
                new ParallelCommandGroup(
                        new RunShooter(shooter),
                        new WaitUntilCommand(() -> shooter.AtVelocity(velo)).andThen(() -> index.RunIndexing(),
                                index)));
    }
}
