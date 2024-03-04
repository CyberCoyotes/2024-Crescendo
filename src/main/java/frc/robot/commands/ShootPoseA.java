package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;


public class ShootPoseA extends SequentialCommandGroup {
    public ShootPoseA(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            new IndexAuton(index).withTimeout(1) // Assuming IntakeAuton takes IntakeSubsystem as a parameter
        );

    }
}