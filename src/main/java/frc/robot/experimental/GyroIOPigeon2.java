/* 
* This is from FRC 2910, 2023 season with some 
* updates for this year's version of the CTRE Phoenix library.
*/

package frc.robot.experimental;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.GyroTrimConfigs;
import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.util.Units;
import frc.robot.util.Constants;


@SuppressWarnings("unused")

/**
 * Implementation of the gyroscope class. Uses the Pigeon2 gyroscope to receive data about the robots yaw, pitch, and roll values.
 */
public class GyroIOPigeon2 implements GyroIO {

    /**
     * Pigeon2 gyroscope object. Receives direct data from the gyroscope about the yaw, pitch, and roll.
     */
   private final Pigeon2 gyro;

    /**
     * Pigeon2 gyroIO implementation.
     *
     * @param canId  Is the unique identifier to the gyroscope used on the robot.
     * @param canBus The name of the CAN bus the device is connected to.
     */
    private StatusSignal<Double> yawSignal;

    private StatusSignal<Double> angularVelocitySignal;

    private BaseStatusSignal[] signals;
    
    public GyroIOPigeon2(int mountPose, double error, String canBus) {
        
        gyro = new Pigeon2(mountPose, canBus);

        Pigeon2Configuration config = new Pigeon2Configuration();
        MountPoseConfigs mountPoseConfigs = new MountPoseConfigs();
        mountPoseConfigs.MountPoseYaw = mountPose;
        GyroTrimConfigs gyroTrimConfigs = new GyroTrimConfigs();
        gyroTrimConfigs.GyroScalarZ = error;
        config.MountPose = mountPoseConfigs;
        config.GyroTrim = gyroTrimConfigs;
        gyro.getConfigurator().apply(config);

        yawSignal = gyro.getYaw();
        // angularVelocitySignal = gyro.getAngularVelocityZ();
        angularVelocitySignal = gyro.getAngularVelocityZDevice(); // Not sure if this is the correct method to use or ZWorld

        signals = new BaseStatusSignal[2];
        signals[0] = yawSignal;
        signals[1] = angularVelocitySignal;
    } 


    /*
    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.connected = true;

        inputs.yaw = Units.degreesToRadians(
                BaseStatusSignalValue.getLatencyCompensatedValue(yawSignal, angularVelocitySignal));
        inputs.pitch = Units.degreesToRadians(gyro.getPitch().getValue());
        inputs.roll = Units.degreesToRadians(gyro.getRoll().getValue());
        inputs.angularVelocity = Units.degreesToRadians(angularVelocitySignal.getValue());
    }
 */
    @Override
    public BaseStatusSignal[] getSignals() {
        return signals;
    }
}
