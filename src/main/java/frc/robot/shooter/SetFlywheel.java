package frc.robot.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.arm.ArmSubsystem;

@SuppressWarnings("unused")

public class SetFlywheel extends Command {
    private final ShooterSubsystem shooter;
    private final ArmSubsystem arm;

    private final double velocity;

    public SetFlywheel(ShooterSubsystem shooter, ArmSubsystem arm, double velocity) {
        this.shooter = shooter;
        this.arm = arm;
        this.velocity = velocity;

        addRequirements(shooter);
         
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Consider adding logic that checks if the arm pose is greater than 21, the flywheel velocity is changed to VELOCITY_LONG_RANGE
        
        
        shooter.setFlywheelVelocity(velocity); // Add Constant here for shooter velocity to not worry about it later?
    
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setFlywheelVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}