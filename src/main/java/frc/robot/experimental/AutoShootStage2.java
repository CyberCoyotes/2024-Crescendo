package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.arm.ArmConstants;
import frc.robot.arm.ArmSubsystem;
import frc.robot.arm.SetArmPosition;
import frc.robot.index.IndexSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.shooter.ShootStage;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.util.NoteSensor;

public class AutoShootStage2 extends SequentialCommandGroup {
    public AutoShootStage2(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem shooter, NoteSensor notesensor) {

        addCommands(
            // Set the arm to the middle position for shooting from the closest Stage leg
            new SetArmPosition(arm, ArmConstants.ARM_MID_POSE),
            
            // Check target velocity and index the game piece forward
            new ShootStage(shooter, index, notesensor).withTimeout(1), // Tune this timeout for stage
            
            // Set arm back to home position
            new SetArmPosition(arm, ArmConstants.ARM_HOME_POSE)
        );
    }
}