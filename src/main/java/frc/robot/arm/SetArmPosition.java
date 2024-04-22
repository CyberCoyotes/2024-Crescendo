package frc.robot.arm;

import edu.wpi.first.wpilibj2.command.Command;

public class SetArmPosition extends Command {

    ArmSubsystem armSub;

    private double armPosition;
    
    public SetArmPosition(ArmSubsystem armSub, double armPosition) {
        this.armSub = armSub;
        this.armPosition = armPosition;
        addRequirements(armSub);
        }

    @Override
    public void initialize() {
            // Adjust the arm position here
            armSub.setArmPose(armPosition);
    }

    @Override
    public boolean isFinished() {
        return true;
   }

} // end of class