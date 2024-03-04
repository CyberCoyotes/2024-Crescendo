package frc.robot.subsystems;

/* Needs the static use references without including Constants. every time */

import com.ctre.phoenix6.StatusSignal;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {

    // Declare a variable for the motor you want to control
    private final TalonFX m_motor;
    private final DutyCycleOut manualControl;
    private final MotionMagicVoltage positionControl;
    // reference
    // https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/CommanddDrive/src/main/java/frc/robot/subsystems/DriveSubsystem.java

    public ArmSubsystem() {
        manualControl = new DutyCycleOut(0);
        positionControl = new MotionMagicVoltage(0);

        m_motor = new TalonFX(Constants.CANIDs.ARM_ID);
        // Factory Default; apply configs
        m_motor.getConfigurator().apply(new TalonFXConfiguration());
        m_motor.getConfigurator().apply(Constants.ArmConstants.ConstantsPlus.CONFIG);

    } /* End of the class-method */

    public StatusSignal<Double> GetArmPos() {

        return m_motor.getPosition();
    }

    public void EnableLock() {
        m_motor.setControl(positionControl.withPosition(m_motor.getPosition().getValueAsDouble()));
    }

    public double GetPositionDegrees() {
        return GetArmPos().getValueAsDouble() * ArmConstants.ARM_NATIVE_TO_DEG;
    }

    private double DegToCanon(double degreePosition) {
        return (degreePosition) * ArmConstants.DEG_TO_ARM_NATIVE;
    }

    @Override
    public void periodic() {
        

    }
    public void setArmPose(double armPose) {
        m_motor.setControl(
                positionControl.withPosition(armPose));
    }

    public void stopRotation() {
        m_motor.setControl(manualControl.withOutput(0));
    }

    public void Drive(Double speed) {
        manualControl.Output = -speed;
        m_motor.setControl(manualControl);
    }

    public void showArmTelemetry(String tableName) {
        var table = Shuffleboard.getTab(tableName);

        table.addNumber("Arm Position (Deg)", () -> GetPositionDegrees());
        table.addNumber("Arm Position (Canon)", () -> GetArmPos().getValueAsDouble());
        table.addNumber("Arm Stator", () -> m_motor.getStatorCurrent().getValueAsDouble());
        table.addNumber("Arm Supply", () -> m_motor.getSupplyCurrent().getValueAsDouble());
        table.addNumber("Arm Voltage", () -> m_motor.getMotorVoltage().getValueAsDouble());
    }

} // end of ArmSubsystem method

/***************************
 * NOTES
 ***************************/
/*
 * TalonFXConfiguration configuration = new TalonFXConfiguration();
 * configuration.motionCruiseVelocity = FAST_MOTION_CONSTRAINTS.maxVelocity /
 * SENSOR_VELOCITY_COEFFICIENT;
 * configuration.motionAcceleration = FAST_MOTION_CONSTRAINTS.maxAcceleration /
 * SENSOR_VELOCITY_COEFFICIENT;
 * configuration.slot0.kP = 0.25;
 * configuration.slot0.kI = 0.0;
 * configuration.slot0.kD = 0.0;
 * configuration.primaryPID.selectedFeedbackSensor =
 * TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
 * configuration.voltageCompSaturation = 12.0;
 */
/*
 * https://github.com/FRCTeam2910/2023CompetitionRobot-Public/blob/main/src/main
 * /java/org/frcteam2910/c2023/subsystems/arm/ArmIOFalcon500.java
 */