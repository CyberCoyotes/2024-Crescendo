package frc.robot.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.arm.ArmSubsystem;
import frc.robot.arm.ArmConstants;

@SuppressWarnings("unused")

public class SetArmStage extends Command {
   
    ArmSubsystem armSub;

    private double armPosition;
    
    public SetArmStage(ArmSubsystem armSub, double armPosition) {
        this.armSub = armSub;
        this.armPosition = armPosition;
        addRequirements(armSub);
        }

    @Override
    public void initialize() {
            // Adjust the arm position here
            armSub.setArmPose(ArmConstants.ARM_MID_POSE);
    }

    @Override
    public boolean isFinished() {
        return true;
   }

} // end of class