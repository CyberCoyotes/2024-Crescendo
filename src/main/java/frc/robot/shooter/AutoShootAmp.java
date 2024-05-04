package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.util.Constants;
import frc.robot.util.NoteSensor;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")

public class AutoShootAmp extends SequentialCommandGroup {
    public AutoShootAmp(ShooterSubsystem shooter, IndexSubsystem index, NoteSensor notesensor) {
        addCommands(
            new ShootAmp(shooter, index, notesensor).withTimeout(1));
            // the index doesn't seem to stop spinning
    }

} // End of class AutoShootAmp 