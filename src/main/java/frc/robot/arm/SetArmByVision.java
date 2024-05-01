package frc.robot.arm;

import frc.robot.vision.Vision3;
import frc.robot.arm.ArmSubsystem;

public class SetArmByVision {
    ArmSubsystem armSub;
    Vision3 vision;
    
/* Use Vision3.java to return a `tv` value
* If tv shows a valid target, then the arm should move to a certain position. See the `ArmSubsystem.java and `SetArmPosition.java` for references.
* Set the Arm position to a position of 30
* If tv does not show a valid target, then the arm should move to a different position
* Set the Arm position to a position of 10
*/
    public void SetArmByVision() {

        // Create a new ArmSubsystem object called armSub
        ArmSubsystem armSub = new ArmSubsystem();

        vision = new Vision3();
        // If the vision has a valid target
        if (vision.isTargetVisible()) {
            // Set the arm position to 30
            armSub.setArmPose(30);
        } else {
            // Set the arm position to 10
            armSub.setArmPose(10);
        }
        // Create a new Vision3 object called vision
        Vision3 vision = new Vision3();
        // If the vision has a valid target
        if (vision.isTargetVisible()) {
            // Set the arm position to 30
            armSub.setArmPose(30);
        } else {
            // Set the arm position to 10
            armSub.setArmPose(10);
        }
    }
}
