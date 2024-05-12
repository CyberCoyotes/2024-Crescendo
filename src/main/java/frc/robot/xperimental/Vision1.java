package frc.robot.xperimental;

import java.util.List;

import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Vision1 {

    NetworkTable limelightBack; // table for the limelight

    NetworkTableEntry tx; // Table for the skew of the robot on a horizontal axis
    NetworkTableEntry ty; // Table for the skew of the robot on a vertical axis
    NetworkTableEntry ta; // Table for the area the target takes up
    NetworkTableEntry ts; // Table for the skew/rotation of target
    NetworkTableEntry tv; // Table to see if there are valid targets
    NetworkTableEntry tl; /// Table for latency/delay before data transfer
    NetworkTableEntry tid; // Table for the ID of target

    NetworkTableEntry tshort; // Table for short side length
    NetworkTableEntry tlong; // Table for long side length
    NetworkTableEntry thoriz; // Table for width
    NetworkTableEntry tvert; // Table for height
    NetworkTableEntry ledMode; // Table to set blinking LEDS
    NetworkTableEntry camMode; // Table to set camera mode
    NetworkTableEntry pipeline; // Table to switch pipelines/which pipeline is in use
    NetworkTableEntry solvePNP; // Table to give position in 3D space based on camera
    NetworkTableEntry botpose; // The bot's position

    public void DebugMethodSingle() {
        var tab = Shuffleboard.getTab("Driver Diagnostics");
        tab.addBoolean("Camera Running", () -> true);
    }

    public Vision1() {
        // instantiate the tables (basically make the tables real)
        limelightBack = NetworkTableInstance.getDefault().getTable("limelight");
        // brings in each individual table
        tx = limelightBack.getEntry("tx");
        ty = limelightBack.getEntry("ty");
        ta = limelightBack.getEntry("ta");
        ts = limelightBack.getEntry("ts");
        tv = limelightBack.getEntry("tv");
        tl = limelightBack.getEntry("tl");
        tid = limelightBack.getEntry("tid");

        tshort = limelightBack.getEntry("tshort");
        tlong = limelightBack.getEntry("tlong");
        thoriz = limelightBack.getEntry("thoriz");
        tvert = limelightBack.getEntry("tvert");
        ledMode = limelightBack.getEntry("ledMode");
        camMode = limelightBack.getEntry("camMode");
        pipeline = limelightBack.getEntry("pipeline");
        solvePNP = limelightBack.getEntry("solvePNP");
        botpose = limelightBack.getEntry("botpose");

    }

    // vison constants
    double CAMERA_HEIGHT = 56; //crescendo bot height from floor to middle of LL camera
    
    //gets the ID of the selected apriltag
    public double getID(){
        return tid.getDouble(-1.0);
    }

    // gets the current pose of the robot in a 2d space
    public Pose2d getRobotPose(){
        double[] temp = {0,0,0,0,0,0};//default for getEntry
        double[] result = botpose.getDoubleArray(temp);
        Rotation2d r2d2 = new Rotation2d(result[3], result[4]); //rotation 2d 2.0 beep
        Pose2d p2d = new Pose2d(result[0],result[1],r2d2);
        return p2d;
    }

    //checks if robot has selected an apriltag as target
    public boolean checkTarget() {
        var t = tv.getDouble(0.0);
        if (t == 1.0) {
            return true;
        } else {
            return false;
        }
    }
     //returns the height of the target april tag in centimeters
     //anything that references APRIL_TAG_HEIGHT should only run if checkTarget is true
     public int APRIL_TAG_HEIGHT(){
        double tagID = getID(); //throws less errors this way. don't know why
        if((tagID == 1.0) || (tagID == 2.0) || (tagID == 5.0) || (tagID == 6.0) || (tagID == 9.0) || (tagID == 10.0)){
            return 122;     //ex. tags 1,2,5,6,9 and 10 have a height of 122
        } else if           // so if the tagID = any of those #s, the height of the tag must be 122
        ((tagID == 3.0)||(tagID == 4.0)||(tagID == 7.0)||(tagID == 8.0)){
            return 132;
        } else if
        ((tagID == 11.0)||(tagID == 12.0)||(tagID == 13.0)||(tagID == 14.0)||(tagID == 15.0)||(tagID == 16.0)){
            return 121;
        } else {
            return 1; //null value that doesn't break the code, only reached when no selected tag
        }
    }

    // distance from the bot to the april tag in centimeters using trig
    public double DISTANCE_CALCULATIONS(){
        double angleToGoalRadians = (ty.getDouble(0.0)) * (3.14159/180.0);
        return (APRIL_TAG_HEIGHT() - CAMERA_HEIGHT) / Math.tan(angleToGoalRadians);
    }

    //DIY trajectory so i can get mess with the poins
    // CHECK IF checkTarget() is true before running this code, if false DO NOT RUN
    public void trajectoryLL(){
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

        // Prevent the path from being flipped if the coordinates are already correct
        path.preventFlipping = true;
    }

    public void turnLL(){
        double degreesToTurn = tx.getDouble(0.0);

        List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(
                getRobotPose() //pose2d of the bot's current location
            );

        PathPlannerPath path = new PathPlannerPath(
                bezierPoints,
                new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI), // The constraints for this path. If using a differential drivetrain, the angular constraints have no effect.
                new GoalEndState(0.0, Rotation2d.fromDegrees(-degreesToTurn)) // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
            );
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

// ArmSubsystem arm = new ArmSubsystem();

/*
public void periodic(){
    double TAG_ID = getID();
    double DISTANCE = DISTANCE_CALCULATIONS();
    if((TAG_ID == 6.0)&&(DISTANCE <= 100)){ //5 if red/right, 6 if blue/left
        arm.setArmPose(55);
    }
}
 */

}
// end of class