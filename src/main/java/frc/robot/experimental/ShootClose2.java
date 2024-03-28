package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetArmPosition;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.util.Constants.ArmConstants;


public class ShootClose2 extends SequentialCommandGroup{
    public ShootClose2(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem2 shooter2, NoteSensorSubsystem notesensor) {

        addCommands(
            new SetArmPosition(arm, ArmConstants.ARM_HOME_POSE),
            new ShootWhenReady(shooter2, index, notesensor)

        );
    }
}