package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShooterIndex extends SequentialCommandGroup {
    public ShooterIndex(ShooterSubsystemVelocity shooter, IndexSubsystem index) {
        super(
            new SetShooter(shooter, Constants.ShooterConstants.SHOOTER_VELOCITY),
            new WaitUntilCommand(shooter::isAtSetpoint),
            new ParallelCommandGroup(
                new SetShooter(shooter, Constants.ShooterConstants.SHOOTER_VELOCITY),
                new SetIndex(index, Constants.IndexConstants.INDEX_POWER)
            )
        );
    }

}