package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootPoseB extends SequentialCommandGroup {
    public ShootPoseB(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            new SetArmPosition(arm, Constants.ArmConstants.ARM_LOW_POSE),
            new RevAndShootCommand(index, shooter)
        );
    }
}
