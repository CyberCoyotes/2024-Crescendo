package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootAutonGroup extends SequentialCommandGroup {
    public ShootAutonGroup(ShooterSubsystemVelocity shooter, IndexSubsystem index, double shooterOutput, double indexPower) {
        super(
            new ShooterAuton(shooter, shooterOutput),
            new WaitUntilCommand(shooter::isAtSetpoint),
            new ParallelCommandGroup(
                new ShooterAuton(shooter, shooterOutput),
                new IndexAuton(index, indexPower)
            )
        );
    }
}