package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class PresetA_CG extends SequentialCommandGroup {
    public PresetA_CG(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            new SetArmPosition(arm, 20),
            new RevAndShootCommand(index, shooter)
        );
    }
}