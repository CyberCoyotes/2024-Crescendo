package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.experimental.ShootWhenReady;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootFromStage extends SequentialCommandGroup {
    public ShootFromStage(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {

        addCommands(
            new SetArmPosition(arm, Constants.ArmConstants.ARM_MID_POSE),
            // new ShooterIndex(shooter, index).withTimeout(1.0)
            new ShootWhenReady(null, index, null),
            new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE)
        );
    }
}