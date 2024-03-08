package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.WinchSubsystem2;

public class SetWinch extends Command {
    private final WinchSubsystem2 winch;
    private double power;

    public SetWinch(WinchSubsystem2 winch, double power) {
        this.winch = winch;
        this.power = power;
        addRequirements(winch); // This command requires the WinchSubsystem2
    }

        @Override
        public void initialize() {
            winch.runWinch(power); // Powers the winch motor
        }

        public void execute() {
        }
        
        @Override
        public void end(boolean interrupted) {
            winch.stopWinch(0);; // Stop the winch motor
        }
        
        @Override
        public boolean isFinished() {
            return false; // This command never ends on its own, but you can change this as needed
        }

    } // end of class SetWinch
