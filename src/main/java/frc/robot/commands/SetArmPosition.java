package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

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
        return true; // Command finishes immediately 
                    // Does this give enough time for the arm to move?
   }

} // end of class