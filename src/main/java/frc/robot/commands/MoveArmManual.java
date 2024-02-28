package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArmManual extends Command {

    ArmSubsystem arm;
    Double input;

    public MoveArmManual(ArmSubsystem arm, Double input) {
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.Drive(input);
    }
}