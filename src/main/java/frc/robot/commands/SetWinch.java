package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.WinchSubsystem;

public class SetWinch extends Command {
    private final WinchSubsystem winch;

    public SetWinch(WinchSubsystem winch) {
        this.winch = winch;
        addRequirements(winch); // This command requires the WinchSubsystem
    }

        @Override
        public void initialize() {
            winch.RunWinch(Constants.WinchConstants.WINCH_POWER); // Powers the winch motor
        }

        public void execute() {
        }
        
        @Override
        public void end(boolean interrupted) {
            winch.RunWinch(0); // Stop the winch motor
        }
        
        @Override
        public boolean isFinished() {
            return false; // This command never ends on its own, but you can change this as needed
        }

    } // end of class SetWinch
