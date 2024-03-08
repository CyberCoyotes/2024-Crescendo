package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmClimb extends Command {

    ArmSubsystem armSub;

    private double power;
    
    public SetArmClimb(ArmSubsystem armSub, double power) {
        this.armSub = armSub;
        this.power = power;
        addRequirements(armSub);
        }

    @Override
    public void initialize() {
            // Adjust the arm position here
            armSub.setArmForClimb(power);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        armSub.stopArm(0);
    }

    @Override
    public boolean isFinished() {
        return false; // Command finishes immediately 
                    // Does this give enough time for the arm to move?
   }

} // end of class