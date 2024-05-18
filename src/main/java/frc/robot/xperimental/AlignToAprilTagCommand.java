package frc.robot.xperimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.drivetrain.CommandSwerveDrivetrain;
import frc.robot.xperimental.VisionChatAimSubsystem;

public class AlignToAprilTagCommand extends Command {
    private final CommandSwerveDrivetrain swerve;
    private final VisionChatAimSubsystem visionAlign;
    private static final double kP = 0.05; // Proportional control constant
    private static final double ALIGN_THRESHOLD = 1.0; // Threshold for alignment in degrees

    public AlignToAprilTagCommand(CommandSwerveDrivetrain swerve, VisionChatAimSubsystem visionAlign) {
        this.swerve = swerve;
        this.visionAlign = visionAlign;
        addRequirements(swerve, visionAlign);
    }

    @Override
    public void initialize() {
        // Initialization logic, if any
    }

    @Override
    public void execute() {
        if (visionAlign.isTargetVisible()) {
            double horizontalOffset = visionAlign.getHorizontalOffset();
            double correction = kP * horizontalOffset;
            swerve.drive(0, 0, correction);
        } else {
            swerve.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return visionAlign.isTargetVisible() && Math.abs(visionAlign.getHorizontalOffset()) < ALIGN_THRESHOLD;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }
}

