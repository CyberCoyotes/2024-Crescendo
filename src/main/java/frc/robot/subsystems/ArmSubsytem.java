package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsytem extends SubsystemBase {

    // The angle we start at, relative to the ground. This means that the sensor is
    // 0 when theta = this.
    // private double defaultAngle = 35; // See also CTRE hardware or software limits
    // private double degUpperBound = 55; // See also CTRE hardware or software limits
    // Bounds for encoder
    // double forwardEncoderLimit; // See also CTRE hardware or software limits
    // double rearEncoderLimit; // See also CTRE hardware or software limits
    // These two control modes are very subject to change
    private VelocityDutyCycle velocityControl;
    private PositionDutyCycle positionControl;
    private NeutralOut brakeMode;

    // ! To consider:
    /*
     * --- Do we want to alter the motor reading?
     * Change it so that 1 reading from the sensor = 1 rotation of the arm?
     * See line 21
     * --- Do we need some type of resource lock on the motor, to prevent
     * simultaneous driving of it and use of a macro?
     * --- Do we want to use our driven mode as DutyCycleOut or as a velocity mode,
     * thus maintiainging constant velocity?
     * --- Still need to configure our speeds and whatnot for FeedForward and the
     * like
     * --- when are we facing the horde and doing a god forsaken PID for this
     * monsterous thing
     */

    TalonFX motor;

    public ArmSubsytem() {
        // Set up motor and control modes
        TalonFXConfiguration config = new TalonFXConfiguration();

        positionControl = new PositionDutyCycle(0);
        velocityControl = new VelocityDutyCycle(0);

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // config.HardwareLimitSwitch.withForwardLimitAutosetPositionValue(forwardEncoderLimit);
        // config.HardwareLimitSwitch.withForwardLimitAutosetPositionValue(rearEncoderLimit);
        motor.getConfigurator().apply(config);

    }

    public void SnapToAbsolutePosition() {
        // Stub
        // manualControl.

        motor.setControl(positionControl);
    }

    public void SetToBrake() {
        // This might be redundant, but...
        velocityControl.Velocity = 0;
        motor.setControl(brakeMode);
    }

    public void Drive(DoubleSupplier speedSupplier) {
        // Stub
        // manualControl.
        velocityControl.Velocity = speedSupplier.getAsDouble();
        motor.setControl(velocityControl);
    }

    /**
     * 
     * @param target the target incrementation. Currently in encoder ticks as of 2/1
     *               (Thursday, Week 4)
     */
    public void IncrementNativeUnits(double target) {
        // Stub

        motor.setControl(positionControl.withPosition(motor.getPosition().getValueAsDouble() + target));
    }

    /**
     * 
     * @param target the target incrementation. In degrees.
     *               (Thursday, Week 4)
     */
    public void IncrementDegrees(double degs) {
        // Stub

        double target = degs * Constants.SystemConstants.DEG_TO_ARM_ENCODER;
        motor.setControl(positionControl.withPosition(motor.getPosition().getValueAsDouble() + target));
    }

    /* Seems unnecessarily complex 
    public double GetPositionDegreesAbsolulte() {
        return GetPositionEncoder() * Constants.SystemConstants.ARM_ENCODER_TO_DEG + defaultAngle;
    }
    */

    /* Use the CTRE software limit and hardware limit configuration settings instead.
    private double GetSensorUpperBound() {
        return (degUpperBound - defaultAngle) * Constants.SystemConstants.DEG_TO_ARM_ENCODER;
    }

    private double GetSensorLowerBound() {
        return (defaultAngle - defaultAngle) * Constants.SystemConstants.DEG_TO_ARM_ENCODER;
    }
    */

    public double GetPositionEncoder() {
        return motor.getPosition().getValueAsDouble();
    }
}
