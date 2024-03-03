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
import frc.robot.subsystems.NoteSensorSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TrackLL extends Command {

    //creates table for the limelight
    NetworkTable limelightBack = NetworkTableInstance.getDefault().getTable("limelight");

    NetworkTableEntry tx = limelightBack.getEntry("tx");
    NetworkTableEntry botpose = limelightBack.getEntry("botpose");
    NetworkTableEntry tid = limelightBack.getEntry("tid");

    double degreesToTurn = tx.getDouble(0.0);

    // gets the current pose of the robot in a 2d space
    public Pose2d getRobotPose(){
        double[] temp = {0,0,0,0,0,0};//default for getEntry
        double[] result = botpose.getDoubleArray(temp);
        Rotation2d r2d2 = new Rotation2d(result[3], result[4]); //rotation 2d 2.0 beep
        Pose2d p2d = new Pose2d(result[0],result[1],r2d2);
        return p2d;
    }

    //gets the ID of the selected apriltag
    public double getID(){
        return tid.getDouble(-1.0);
    }

        //retrieves coordinates of selected tag
    public Pose2d getTargetPose(){
        double tagID = getID();
        double[] temp = {0,0,0,0,0,0}; //botpose code to maintain rotation
        double[] result = botpose.getDoubleArray(temp);

        if (tagID == 1){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]); //rotation 2d 2.0 beep
            Pose2d p2d = new Pose2d(14.67,1.00,r2d2);
            return p2d;
        } else if (tagID == 2){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]); 
            Pose2d p2d = new Pose2d(14.67,1.67,r2d2);
            return p2d;
        } else if (tagID == 3){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(15.75,4.02,r2d2);
            return p2d;
        } else if (tagID == 4){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(15.75,7.05,r2d2);
            return p2d;
        } else if (tagID == 5){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(14.65,7.06,r2d2);
            return p2d;
        } else if (tagID == 6){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(1.90,7.08,r2d2);
            return p2d;
        }  else if (tagID == 7){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(0.90,7.02,r2d2);
            return p2d;
        } else if (tagID == 8){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(0.79,4.02,r2d2);
            return p2d;
        } else if (tagID == 9){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(0.72,1.73,r2d2);
            return p2d;
        } else if (tagID == 10){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(1.61,1.22,r2d2);
            return p2d;
        } else if (tagID == 11){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(12.45,2.70,r2d2);
            return p2d;
        } else if (tagID == 12){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(12.58,5.42,r2d2);
            return p2d;
        } else if (tagID == 13){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(10.10,4.10,r2d2);
            return p2d;
        } else if (tagID == 14){
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(6.40,4.10,r2d2);
            return p2d;
        } else if (tagID == 15){ //BAD
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(3.84,5.53,r2d2);
            return p2d;
        } else { // TAGID = 16 in this case, no other tags
            Rotation2d r2d2 = new Rotation2d(result[3], result[4]);
            Pose2d p2d = new Pose2d(4.08,2.64,r2d2);
            return p2d;
        }
    }

    // Create a list of bezier points from poses. Each pose represents one waypoint.
    // The rotation component of the pose should be the direction of travel. Do not use holonomic rotation.
    List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(
                getRobotPose(), //pose2d of the bot's current location
                getTargetPose() //pose2d of the selected tag location)
            );

        // Create the path using the bezier points created above
        PathPlannerPath path = new PathPlannerPath(
                bezierPoints,
                new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI), // The constraints for this path. If using a differential drivetrain, the angular constraints have no effect.
                new GoalEndState(0.0, Rotation2d.fromDegrees(-90)) // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
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
