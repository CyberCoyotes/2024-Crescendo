package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetArmPosition;
import frc.robot.commands.ShootWhenReadyStage;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.util.ArmConstants;

public class ShootFromStageMacro extends SequentialCommandGroup {
    public ShootFromStageMacro(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem2 shooter2, NoteSensorSubsystem notesensor) {

        addCommands(
            // Set the arm to the middle position for shooting from the closest Stage leg
            new SetArmPosition(arm, ArmConstants.ARM_MID_POSE),
            
            // Check target velocity and index the game piece forward
            new ShootWhenReadyStage(shooter2, index, notesensor).withTimeout(1), // TODO Tune this timeout for stage
            
            // Set arm back to home position
            new SetArmPosition(arm, ArmConstants.ARM_HOME_POSE)
        );
    }
}