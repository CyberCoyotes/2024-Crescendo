package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class SetArmPose extends Command {
    ArmSubsystem arm;
    double pose;

    public SetArmPose(ArmSubsystem arm, double pose) {
        super();
        this.pose = pose;
        addRequirements(this.arm = arm);
    }

    @Override
    public void execute() {
        arm.setArmPose(pose);
    }

    @Override
    public boolean isFinished() {
        return arm.GetArmPos().getValueAsDouble() == pose;
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
