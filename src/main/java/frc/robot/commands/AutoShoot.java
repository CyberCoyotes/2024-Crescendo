package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.ShooterSubsystem2;

public class AutoShoot extends SequentialCommandGroup {
    public AutoShoot (ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, ShooterSubsystem2 shooter2, NoteSensorSubsystem notesensor) {

        addCommands(
            new Shoot(shooter2, index, notesensor).withTimeout(1.0)
        );
    }
}