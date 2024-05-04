package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.arm.ArmSubsystem;
import frc.robot.index.IndexSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.util.NoteSensor;

public class AutoShoot extends SequentialCommandGroup {
    public AutoShoot (ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem shooter, NoteSensor notesensor) {

        addCommands(
            new Shoot(shooter, index, notesensor).withTimeout(1.0)
        );
    }
}