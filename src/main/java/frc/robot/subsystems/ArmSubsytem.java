package frc.robot.subsystems;

import static frc.robot.Constants.SystemConstants.ARM_ENCODER_TO_DEG;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsytem extends SubsystemBase {

    // The angle we start at, relative to the ground. This means that the sensor is
    // 0 when theta = this.

    // private double defaultAngle = 35; // See also CTRE hardware or software limits
    // private double degUpperBound = 55; // See also CTRE hardware or software limits

    // Issue #95
    private double defaultAngle = 35;
    private double degUpperBound = 55;

    // Bounds for encoder
    double forwardEncoderLimit; // See also CTRE hardware or software limits
    double rearEncoderLimit; // See also CTRE hardware or software limits
    // These two control modes are very subject to change
    private VelocityDutyCycle velocityControl;
    private PositionDutyCycle positionControl;
    private NeutralOut brakeMode;

    // ! To consider:
    /*
     * --- when are we facing the horde and doing a god forsaken PID for this
     * monsterous thing? TODAY! (maybe)
     */

    TalonFX motor;

    public ArmSubsytem() {
        // Set up motor and control modes
        TalonFXConfiguration config = new TalonFXConfiguration();
        SoftwareLimitSwitchConfigs limitConfigs = new SoftwareLimitSwitchConfigs();
        Slot0Configs PIDConfigs = new Slot0Configs();

        positionControl = new PositionDutyCycle(0);
        velocityControl = new VelocityDutyCycle(0);

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        limitConfigs.ForwardSoftLimitThreshold = forwardEncoderLimit;
        limitConfigs.ReverseSoftLimitThreshold = rearEncoderLimit;
        limitConfigs.ForwardSoftLimitEnable = true;
        limitConfigs.ReverseSoftLimitEnable = true;

        // #region PID
        PIDConfigs.kP = 0.1;
        PIDConfigs.kI = 0;
        PIDConfigs.kD = 0.01;
        // niceeeeeee
        config.Slot0 = PIDConfigs;
        motor.getConfigurator().apply(PIDConfigs);
        motor.getConfigurator().apply(limitConfigs);
        // #endregion
        motor.getConfigurator().apply(config);

    }

    public void SnapToAbsolutePosition(double degreePosition) {
        // Stub
        // manualControl.
        // positionControl.Position = DegToEncoder(degreePosition);
        motor.setControl(positionControl);
    }

    public void SetToBrake() {
        // This might be redundant, but...
        velocityControl.Velocity = 0;
        motor.setControl(brakeMode);
    }

    public Command DriveCommand(Double speedSupplier) {
        // Stub
        // manualControl.

        return this.run(() -> Drive(speedSupplier));
    }

    private void Drive(Double speedSupplier) {
        velocityControl.Velocity = speedSupplier;
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

    private double DegToEncoder(double degreePosition) {
        return (degreePosition - defaultAngle) * ARM_ENCODER_TO_DEG;
    }

    private double GetSensorUpperBound() {

        return DegToEncoder(degUpperBound);
    }

    private double GetSensorLowerBound() {

        return 0;
    }
    */

    public double GetPositionEncoder() {
        return motor.getPosition().getValueAsDouble();
    }

}
