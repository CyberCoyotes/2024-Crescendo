package frc.robot.util;

public class ShooterConstants {
     // TODO Tune this value
     public static final double SHOOTER_VELOCITY = 60; // rotations per second
    
     // public static final double SHOOTER_VELOCITY_SHORT_RANGE = 40; // Not currently in use anywhere
     // public static final double SHOOTER_VELOCITY_LONG_RANGE = 60; // Not currently in use anywhere, Flywheel speed for long range shots only
     // public static final double SHOOTER_IDLE_VELOCITY = SHOOTER_VELOCITY * 0.30; // 30% of max speed
     // public static final double VELOCITY_ERROR_MARGIN = SHOOTER_VELOCITY * 0.10; // 5% of max speed
    
    // TODO Tune this value
    public static final double FLYWHEEL_VELOCITY = 100; // rotations per second (rps)
    
    public static final double FLYWHEEL_VELOCITY_SHORT_RANGE = 40; // Not currently in use anywhere
    public static final double FLYWHEEL_VELOCITY_LONG_RANGE = 60; // Not currently in use anywhere, Flywheel speed for long range shots only
    public static final double FLYWHEEL_IDLE_VELOCITY = FLYWHEEL_VELOCITY * 0.30; // 30% of max speed
    public static final double FLYWHEEL_MARGIN_ERROR = FLYWHEEL_VELOCITY * 0.10; // 5% of max speed

    // TODO Tune this value for nominal flywheel checks
    public static final double FLYWHEEL_CONSTANT = 45.5; // rotations per second (rps)
    public static final double FLYWHEEL_MIN = FLYWHEEL_CONSTANT * .95;
    public static final double FLYWHEEL_MAX = FLYWHEEL_CONSTANT * 1.20;
}
