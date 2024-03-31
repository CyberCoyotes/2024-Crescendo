package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;


@SuppressWarnings("unused")

public class Gyro {
       
    private final Pigeon2 pidgey;

    private final double startingPose;

    public Gyro() {
        pidgey = new Pigeon2(14); // ID from Change the ID as needed
        
        this.startingPose = 180;
    
        // StatusSignal<Double> yawSignal;

        /* User can change the configs if they want, or leave it empty for factory-default */
        // Mimicing ArmSubsystem.java
        pidgey.getConfigurator().apply(new Pigeon2Configuration());

        var pidgeyConfigs = new Pigeon2Configuration();
        /* Speed up signals to an appropriate rate */
        // pidgey.getYaw().setUpdateFrequency(100);
        // pidgey.getGravityVectorZ().setUpdateFrequency(100);
        // pidgey.getAccelerationX().setUpdateFrequency(100);
        var pidgeyMountConfigs = new MountPoseConfigs();
        // pidgeyMountConfigs.MountPose = MountPoseConfigs.MountedPose.TwoDegreesPitch;

        pidgey.getConfigurator().apply(pidgeyConfigs);
        pidgey.getConfigurator().apply(pidgeyMountConfigs);
        }
    
        /* Not sure what difference is between these three
         * Values will be posted to the Dashboard
         */
        public double getYaw() {
            return pidgey.getYaw().getValue();
        }
        
        public double getAngle() {
            return pidgey.getAngle();
        }

        public double Rotation2d() {
            return pidgey.getRotation2d().getDegrees();
        }
          
        /* 
        * The Pigeon2 class has a default reset method `reset()`
        * So can it be called directly without this method?
        * Maybe, but not 100% sure
        */


        public void setAutonStartingPose(double startingPose) {
            pidgey.setYaw(startingPose);
        }

                
        public void setAutonStartingPose180(double startingPose) {
            pidgey.setYaw(180);
        }



    } // end of class Gyro


    /*
     * Mount Calibration
     * It’s recommended to perform a mount calibration when placement of the Pigeon 2.0 has been finalized. 
     * This can be done via the Calibration page in Tuner X.
     */

    /* Documentation
    https://api.ctr-electronics.com/phoenix6/release/java/com/ctre/phoenix6/hardware/Pigeon2.html
    
    Iterative Example
    https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/Pigeon2/src/main/java/frc/robot/Robot.java

     * Method Summary
    Modifier & Type	| Method            |   Description
    ----------------|---------------    |-------------------
    void            |   close()         |
    double          |   getAngle()      |   Returns the heading of the robot in degrees.
    double          |   getRate()	    |   Returns the rate of rotation of the Pigeon 2.
    Rotation2d      |   getRotation2d() |   Returns the heading of the robot as a Rotation2d.
    Rotation3d      |   getRotation3d() |   Returns the orientation of the robot as a Rotation3d created from the quaternion signals.
    void            |   initSendable​(SendableBuilder builder)	            |
    void            |   reset()         |   Resets the Pigeon 2 to a heading of zero.
     */

     /*
      * public double getAngle()
        Returns the heading of the robot in degrees.
        The angle increases as the Pigeon 2 turns clockwise when looked at from the top. This follows the NED axis convention.
        The angle is continuous; that is, it will continue from 360 to 361 degrees. This allows for algorithms that wouldn't want to see a discontinuity in the gyro output as it sweeps past from 360 to 0 on the second time around.
        Returns: The current heading of the robot in degrees
      */