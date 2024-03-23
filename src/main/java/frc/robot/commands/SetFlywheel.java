package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem2;
import frc.robot.subsystems.ArmSubsystem;

public class SetFlywheel extends Command {
    private final ShooterSubsystem2 shooter2;
    private final ArmSubsystem arm;

    private final double velocity;

    public SetFlywheel(ShooterSubsystem2 shooter2, ArmSubsystem arm, double velocity) {
        this.shooter2 = shooter2;
        this.arm = arm;
        this.velocity = velocity;

        addRequirements(shooter2);
         
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Consider adding logic that checks if the arm pose is greater than 21, the flywheel velocity is changed to VELOCITY_LONG_RANGE
        
        
        shooter2.setFlywheelVelocity(velocity); // Add Constant here for shooter velocity to not worry about it later?
    
    }

    @Override
    public void end(boolean interrupted) {
        shooter2.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}