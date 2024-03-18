package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.SetIndex;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;


public class ShootClose2 extends ParallelCommandGroup{
    public ShootClose2(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem2 shooter) {

        addCommands(
            /* Moving the arm is shouldn't be necessary, but just in case */
            // new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE),
            // new ShooterIndex(shooter, index).withTimeout(1.0)

            new SetShooterVelocity(shooter, 60),
            new ShootWhenReady(shooter, index)

        );
    }
}