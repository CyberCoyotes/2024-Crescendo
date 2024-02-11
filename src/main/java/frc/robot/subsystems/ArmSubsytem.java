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
import static frc.robot.Constants.SystemConstants.ArmConstants;

public class ArmSubsytem extends SubsystemBase {

    // The angle we start at, relative to the ground. This means that the sensor is
    // 0 when theta = this.

    // private double defaultAngle = 35; // See also CTRE hardware or software
    // limits
    // private double degUpperBound = 55; // See also CTRE hardware or software
    // limits

    // Issue #95
    private double defaultAngle = 55;
    private double degUpperBound = 90;

    // Bounds for encoder

    double rearEncoderLimit = ArmConstants.ARM_ROTATION_LIMIT_NATIVE; // See also CTRE hardware or software limits
    // These two control modes are very subject to change
    private DutyCycleOut outputControl;
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
        config.CurrentLimits.SupplyCurrentLimit = ArmConstants.ARM_SUPPLY_CURRENT_LIMIT;
        config.MotorOutput.PeakReverseDutyCycle = -ArmConstants.ARM_MAX_DUTY_CYCLE_OUT;
        config.MotorOutput.PeakForwardDutyCycle = ArmConstants.ARM_MAX_DUTY_CYCLE_OUT;
        positionControl = new PositionDutyCycle(0);
        outputControl = new DutyCycleOut(0);
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        // niceeeeeee
        config.Slot0 = ArmConstants.ARM_PID_CONFIGS;
        motor.getConfigurator().apply(ArmConstants.ARM_PID_CONFIGS);
        motor.getConfigurator().apply(ArmConstants.LIMIT_CONFIGS);
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
        outputControl.Output = 0;
        motor.setControl(brakeMode);
    }

    public Command DriveCommand(Double speedSupplier) {
        // Stub
        // manualControl.

        return this.run(() -> Drive(speedSupplier));
    }

    private void Drive(Double speed) {
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

    /*
     * Seems unnecessarily complex
     * public double GetPositionDegreesAbsolulte() {
     * return GetPositionEncoder() * Constants.SystemConstants.ARM_ENCODER_TO_DEG +
     * defaultAngle;
     * }
     */

    /*
     * Use the CTRE software limit and hardware limit configuration settings
     * instead.
     * 
     * private double DegToEncoder(double degreePosition) {
     * return (degreePosition - defaultAngle) * ARM_ENCODER_TO_DEG;
     * }
     * 
     * private double GetSensorUpperBound() {
     * 
     * return DegToEncoder(degUpperBound);
     * }
     * 
     * private double GetSensorLowerBound() {
     * 
     * return 0;
     * }
     */

    public double GetPositionEncoder() {
        return motor.getPosition().getValueAsDouble();
    }

}
