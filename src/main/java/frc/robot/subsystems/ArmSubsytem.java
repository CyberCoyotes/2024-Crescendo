package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.SystemConstants.ArmConstants;

public class ArmSubsytem extends SubsystemBase {

    // The angle we start at, relative to the ground. This means that the sensor is
    // 0 when theta = this.
    private double defaultAngle = 35;
    private double degUpperBound = 90;
    // A notable angle we used with angles.
    private double shootAngleOne = 52;

    // Bounds for encoder

    double rearEncoderLimit = ArmConstants.ARM_ROTATION_LIMIT_NATIVE; // See also CTRE hardware or software limits
    // These two control modes are very subject to change
    private DutyCycleOut outputControl;
    private PositionDutyCycle positionControl;
    private NeutralOut brakeMode;

    TalonFX motor = new TalonFX(Constants.CANIDs.ARM_ID);

    public double NativeRead() {
        return motor.getPosition().getValue();
    }

    public ArmSubsytem() {
        // Set up control modes
        positionControl = new PositionDutyCycle(0);
        outputControl = new DutyCycleOut(0);

        //
        motor.getConfigurator().apply(ArmConstants.MOTOR_CONFIG);

    }

    public void SnapToAbsolutePosition(double degreePosition) {
        // Stub
        // manualControl.
        positionControl.Position = DegToNative(degreePosition);
        motor.setControl(positionControl);
    }

    public void SetToBrake() {
        // This might be redundant, but...
        outputControl.Output = 0;
        motor.setControl(brakeMode);
    }

    public void Drive(Double speed) {
        outputControl.Output = speed;
        motor.setControl(outputControl);
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

        double target = degs * ArmConstants.DEG_TO_ARM_NATIVE;
        motor.setControl(positionControl.withPosition(motor.getPosition().getValueAsDouble() + target));
    }

    // Seems unnecessarily complex -JV

    // Pulling my barely-existent rank; Degrees > using a reference sheet. If
    // nothing else, I'm gonna leave the methods for getting Rotations untouched.
    // -SZ
    public double GetPositionDegreesAbsolulte() {
        return GetPositionNative() * ArmConstants.ARM_NATIVE_TO_DEG +
                defaultAngle;
    }

    // Use the
    // CTRE software
    // limit and
    // hardware limit
    // configuration settings instead.
    // I will die on this hill. -SZ

    private double DegToNative(double degreePosition) {
        return (degreePosition - defaultAngle) * ArmConstants.DEG_TO_ARM_NATIVE;
    }

    private double GetNativeUpperBound() {

        return DegToNative(degUpperBound);
    }

    public double GetPositionNative() {
        return motor.getPosition().getValueAsDouble();
    }

}