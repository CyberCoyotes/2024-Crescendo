package frc.robot.util;

public class ShooterConstants {
    
    public static final double FLYWHEEL_VELOCITY = 100; // rotations per second (rps) // 100
    public static final double FLYWHEEL_VELOCITY_STAGE = 120; // Greater than regular
    public static final double FLYWHEEL_VELOCITY_AMP = 55; // Primary-Top amp value, i.e.
    // TODO Tune the top amp, slower than a standard shot
    /* 
    /* 60
    * 55 works when in the middle, not enough if off to one side
     * 50
     * 30 is too low
     * 35 is too low
     * 40
     * 42
     * Starting over now that bottom is 0.2
    */
    public static final double FLYWHEEL_IDLE_VELOCITY = FLYWHEEL_VELOCITY * 0.30; // 30% of max speed
    public static final double FLYWHEEL_MARGIN_ERROR = FLYWHEEL_VELOCITY * 0.10; // 5% of max speed

    /*  Tune this value for nominal flywheel velocity checks
    This value is NOT the same as the FLYWHEEL_VELOCITY value and was determined empirically
    using Phoenix Tuner and the velocity measurement.
    */
    public static final double FLYWHEEL_VELOCITY_CHECK_FACTOR = 2.2; // rotations per second (rps)
    /* 
    |   velocity    |   actual      |
    |   100         |   45.5 +/- 1  |
    |   120         |   54.5 +/- 1  |
     * 
    */
    public static final double FLYWHEEL_VELOCITY_CHECK = (FLYWHEEL_VELOCITY/FLYWHEEL_VELOCITY_CHECK_FACTOR); // rotations per second (rps)
    public static final double FLYWHEEL_MIN = FLYWHEEL_VELOCITY_CHECK * .97;
    public static final double FLYWHEEL_MAX = FLYWHEEL_VELOCITY_CHECK * 1.10;

    public static final double FLYWHEEL_VELOCITY_STAGE_CHECK = (FLYWHEEL_VELOCITY_STAGE/FLYWHEEL_VELOCITY_CHECK_FACTOR); // rotations per second (rps)
    public static final double FLYWHEEL_STAGE_MIN = FLYWHEEL_VELOCITY_STAGE_CHECK * .97;
    public static final double FLYWHEEL_STAGE_MAX = FLYWHEEL_VELOCITY_STAGE_CHECK * 1.10;

    public static final double FLYWHEEL_VELOCITY_AMP_CHECK = (FLYWHEEL_VELOCITY_AMP/FLYWHEEL_VELOCITY_CHECK_FACTOR); // rotations per second (rps)
    public static final double FLYWHEEL_AMP_MIN = FLYWHEEL_VELOCITY_AMP_CHECK * .97;
    public static final double FLYWHEEL_AMP_MAX = FLYWHEEL_VELOCITY_AMP_CHECK * 1.10;
}
