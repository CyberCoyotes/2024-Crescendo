package frc.robot.climb;

import edu.wpi.first.wpilibj2.command.Command;

public class SetWinch extends Command {
    private final WinchSubsystem winch;
    private double power;

    public SetWinch(WinchSubsystem winch, double power) {
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
