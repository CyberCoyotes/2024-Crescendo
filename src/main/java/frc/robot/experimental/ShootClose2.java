package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetIndex;
import frc.robot.commands.SetFlywheel;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.util.Constants;


public class ShootClose2 extends ParallelCommandGroup{
    public ShootClose2(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem2 shooter2, NoteSensorSubsystem notesensor) {

        addCommands(
            /* Moving the arm is shouldn't be necessary, but just in case */
            // new SetArmPosition(arm, Constants.ArmConstants.ARM_HOME_POSE),
            // new ShooterIndex(shooter, index).withTimeout(1.0)

            // new SetFlywheel(shooter2, arm, 60),
            new ShootWhenReady(shooter2, index, notesensor)

        );
    }
}