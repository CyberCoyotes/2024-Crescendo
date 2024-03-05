package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootClose extends SequentialCommandGroup {
    public ShootClose(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {
        addCommands(
            /* Moving the arm is shouldn't be necessary, but just in case */
            new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE),
            new ShooterIndex(shooter, index)
        );
    }
}