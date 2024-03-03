package frc.robot.commands;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TurnLL extends Command {

    private VisionSubsystem vision;

        public TurnLL(VisionSubsystem vision) {
        this.vision = vision;
        addRequirements(this.vision);
    }

    //creates table for the limelight
    NetworkTable limelightBack = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry tx = limelightBack.getEntry("tx");
    NetworkTableEntry botpose = limelightBack.getEntry("botpose");

    double degreesToTurn = tx.getDouble(0.0);

    // gets the current pose of the robot in a 2d space
    public Pose2d getRobotPose(){
        double[] temp = {0,0,0,0,0,0};//default for getEntry
        double[] result = botpose.getDoubleArray(temp);
        Rotation2d r2d2 = new Rotation2d(result[3], result[4]); //rotation 2d 2.0 beep
        Pose2d p2d = new Pose2d(result[0],result[1],r2d2);
        return p2d;
    }

    List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(
                getRobotPose() //pose2d of the bot's current location
            );

    PathPlannerPath path = new PathPlannerPath(
            bezierPoints,
            new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI), // The constraints for this path. If using a differential drivetrain, the angular constraints have no effect.
            new GoalEndState(0.0, Rotation2d.fromDegrees(-degreesToTurn)) // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
        );

    @Override
    public void initialize() {
        // Code to run when the command is first initialized
        AutoBuilder.followPath(path);

    }

    @Override
    public void end(boolean interrupted) {
        // Code to run when the command ends or is interrupted
    }

    @Override
    public boolean isFinished() {
        // Return true when the command should end
        return false;
    }
}
