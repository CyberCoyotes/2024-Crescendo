package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootNote0 extends SequentialCommandGroup {
    public ShootNote0(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            /* Moving the arm is probably unnecessary */
            new SetArmPosition(arm, 0),
            new RevAndShootCommand(index, shooter)
        );
    }
}