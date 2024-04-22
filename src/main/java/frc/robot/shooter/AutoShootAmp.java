package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants;
import frc.robot.util.NoteSensorSubsystem;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")

public class AutoShootAmp extends SequentialCommandGroup {
    public AutoShootAmp(ShooterSubsystem2 shooter2, IndexSubsystem index, NoteSensorSubsystem notesensor) {
        addCommands(
            new ShootAmp(shooter2, index, notesensor).withTimeout(1));
            // the index doesn't seem to stop spinning
    }

} // End of class AutoShootAmp 