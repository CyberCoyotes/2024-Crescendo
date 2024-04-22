package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.arm.ArmSubsystem;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.shooter.ShooterConstants;
import frc.robot.shooter.ShooterSubsystem2;
import frc.robot.util.NoteSensorSubsystem;

@SuppressWarnings("unused")

public class AutoShootStage extends Command {
    private ShooterSubsystem2 shooter2; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public AutoShootStage(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, NoteSensorSubsystem notesensor, ShooterSubsystem2 shooter2) {
        this.shooter2 = shooter2;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter2, index, notesensor); 
    }

    @Override
    public void initialize() {
        shooter2.setFlywheelToIdle();
    }

    @Override
    public void execute() {
        /* 
        * Turn on the flywheel `setTargetFlywheelVelocity`
        * Check if flywheel has reached target velocity `isFlywheelNominal`
        * If true, index the game piece forward
        * Else, setPower of Index to zero and continue running the flywheel until `isFlywheelNominal` is true
         */

        shooter2.setFlywheelVelocity(ShooterConstants.FLYWHEEL_VELOCITY_STAGE);

        if (shooter2.isFlywheelNominal()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        } else {
            index.stopIndexing();
            // shooter2.setFlywheelVelocity(shooter2.FLYWHEEL_VELOCITY);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
} // end of class ShootWhenReady