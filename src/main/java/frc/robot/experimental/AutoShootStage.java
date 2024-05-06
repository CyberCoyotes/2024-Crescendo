package frc.robot.experimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.arm.ArmSubsystem;
import frc.robot.index.IndexConstants;
import frc.robot.index.IndexSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.shooter.FlywheelConstants;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.util.NoteSensorSubsystem;

@SuppressWarnings("unused")

public class AutoShootStage extends Command {
    private ShooterSubsystem shooter; 
    private IndexSubsystem index;
    private NoteSensorSubsystem notesensor;

    public AutoShootStage(ArmSubsystem arm, IndexSubsystem index, IntakeSubsystem intake, NoteSensorSubsystem notesensor, ShooterSubsystem shooter) {
        this.shooter = shooter;
        this.index = index;
        this.notesensor = notesensor;
        addRequirements(shooter, index, notesensor); 
    }

    @Override
    public void initialize() {
        shooter.setFlywheelToIdle();
    }

    @Override
    public void execute() {
        /* 
        * Turn on the flywheel `setTargetFlywheelVelocity`
        * Check if flywheel has reached target velocity `isFlywheelNominal`
        * If true, index the game piece forward
        * Else, setPower of Index to zero and continue running the flywheel until `isFlywheelNominal` is true
         */

        shooter.setFlywheelVelocity(FlywheelConstants.FLYWHEEL_VELOCITY_STAGE);

        if (shooter.isFlywheelNominal()) {
            index.setIndexPower(IndexConstants.INDEX_POWER);
        } else {
            index.stopIndexing();
            // shooter.setFlywheelVelocity(shooter.FLYWHEEL_VELOCITY);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
} // end of class ShootWhenReady