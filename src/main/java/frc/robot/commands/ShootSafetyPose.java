package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystemVelocity;

public class ShootSafetyPose extends SequentialCommandGroup {
    public ShootSafetyPose(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystemVelocity shooter) {

        addCommands(
            /* Moving the arm is shouldn't be necessary, but just in case */
            new SetArmPosition(arm, Constants.ArmConstants.ARM_SAFETY_POSE),
            // new ShooterIndex(shooter, index).withTimeout(1.0)
            new RevAndShootCommand(index, shooter).withTimeout(1.25).andThen(new InstantCommand(() -> shooter.Disable(), shooter)),
            new SetIndex(index, 0).withTimeout(.2)
        );
    }
}