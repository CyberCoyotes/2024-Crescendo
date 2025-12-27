package frc.robot.subsystemsModernized;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystemsModernized.ArmConfigs;
import frc.robot.subsystemsModernized.Constants;

/**
 * Subsystem for controlling a single-jointed arm using a TalonFX motor.
 * Uses motion magic for position control and voltage for manual control.
 */
public class ArmSubsystem extends SubsystemBase {
    private final TalonFX motor;
    private final MotionMagicVoltage motionMagic;
    private ControlMode currentMode = ControlMode.POSITION;
    
    private enum ControlMode {
        POSITION,
        VOLTAGE,
        DISABLED
    }

    public ArmSubsystem() {
        motor = new TalonFX(Constants.CANIDs.ARM_ID);
        motionMagic = new MotionMagicVoltage(0);
        
        configureMotor();
        setupTelemetry();
    }

    private void configureMotor() {
        ArmConfigs.applyArmConfigs(motor);
    }

    private void setupTelemetry() {
        // Add important values to SmartDashboard
        SmartDashboard.putData("Arm Subsystem", this);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Position", () -> getPosition().getValue(), null);
        builder.addDoubleProperty("Stator Current", () -> motor.getStatorCurrent().getValue(), null);
        builder.addDoubleProperty("Supply Current", () -> motor.getSupplyCurrent().getValue(), null);
        builder.addStringProperty("Control Mode", () -> currentMode.toString(), null);
    }

    public StatusSignal<Double> getPosition() {
        return motor.getPosition();
    }

    /**
     * Creates a command to set the arm to a specific position using motion magic
     * @param targetPosition The target position in native units
     * @return Command that will move the arm to the target position
     */
    public Command setPositionCommand(double targetPosition) {
        return run(() -> {
            currentMode = ControlMode.POSITION;
            motor.setControl(motionMagic.withPosition(targetPosition));
        });
    }

    /**
     * Creates a command to control the arm with manual voltage
     * @param voltage The voltage to apply (-12 to 12)
     * @return Command that will apply the specified voltage
     */
    public Command setVoltageCommand(double voltage) {
        return run(() -> {
            currentMode = ControlMode.VOLTAGE;
            motor.setControl(new VoltageOut(voltage));
        });
    }

    /**
     * Creates a command to stop the arm
     * @return Command that will stop the arm
     */
    public Command stopCommand() {
        return runOnce(() -> {
            currentMode = ControlMode.DISABLED;
            motor.setControl(new VoltageOut(0));
        });
    }

    @Override
    public void periodic() {
        // Log data to NetworkTables
        SmartDashboard.putNumber("Arm/Position", getPosition().getValue());
        SmartDashboard.putNumber("Arm/Stator Current", motor.getStatorCurrent().getValue());
        SmartDashboard.putNumber("Arm/Supply Current", motor.getSupplyCurrent().getValue());
        SmartDashboard.putString("Arm/Control Mode", currentMode.toString());
    }
}