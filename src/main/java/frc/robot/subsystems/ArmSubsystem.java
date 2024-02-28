package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

/* Needs the static use references without including Constants. every time */

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    // class member variable
    // final MotionMagicVoltage m_armPose = new MotionMagicVoltage(0); //
    // Initializes to position 0
    // final MotionMagicVoltage m_armPose = new MotionMagicVoltage(0); //
    // Initializes to position 0

    public ArmSubsystem() {
        manualControl = new DutyCycleOut(0);
        positionControl = new MotionMagicVoltage(0);

        m_motor = new TalonFX(Constants.CANIDs.ARM_ID);
        // Factory Default; apply configs
        m_motor.getConfigurator().apply(new TalonFXConfiguration());
        m_motor.getConfigurator().apply(Constants.ArmConstants.ArmConstantsExtension.config);

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

    /* An example of a motor intilization method */

    /*
     * private void initializeArmTalonFX(TalonFXConfigurator cfg) {
     * 
     * var toApply = new TalonFXConfiguration();
     * 
     * /* Configure current limits
     */
    // }

    /**
     * This calls a 'long form' of MotionMagicVoltage
     * 
     * @deprecated This creates a new MotionMagic object, which is
     *             processor-intensive. Please find another way, if possible.
     */
    @Deprecated()

    public void setArmMotionMagicVoltage(double armPose) {
        m_motor.setControl(
                new MotionMagicVoltage(
                        armPose,
                        false,
                        armPose,
                        Constants.CANIDs.ARM_ID,
                        false,
                        false,
                        false));
    }

    public void setArmPose(double armPose) {

        /* Documentation */ // m_arm.Slot = 0;
        /* Documentation version is Iterative */
        // m_arm.setControl(m_arm.withPosition(200));
        /* Command Based implementation */
        m_motor.setControl(
                /*
                 * 'long form' of MotionMagicVoltage motor travels at full speed & coast like a
                 * velocity output
                 * 'short form' of MotionMagicVoltage motor sets to position
                 */
                positionControl.withPosition(armPose));

        // SmartDashboard.putNumber(m_arm.getPosition().getValue());

        // SmartDashboard.putNumber("Arm Position", m_arm.getPosition().getValue());
        // SmartDashboard.putNumber("Arm Stator", m_arm.getStatorCurrent().getValue());

    }

    public void setArmToScorePose1() {
        m_motor.setControl(positionControl.withPosition(10));
    }

    public void setArmToScorePose2() {
        m_motor.setControl(positionControl.withPosition(20));
    }

    public void setArmToScorePose3() {
        m_motor.setControl(positionControl.withPosition(30));
    }

    public void setArmToScorePose4() {
        m_motor.setControl(positionControl.withPosition(40));
    }

    public void stopRotation() {
        m_motor.setControl(manualControl.withOutput(0));
    }

    public void Drive(Double speed) {
        manualControl.Output = speed;
        m_motor.setControl(manualControl);
    }

    /*
     * Currently only being called in subsystem-command;
     * inspite of my efforts it only appears once it's triggered at least once
     * 
     * Changed to Shuffleboard. Feel free to delete these comments if you wanna,
     * Scoyoc - SZ
     */
    public void showArmTelemetry(String tableName) {
        var table = Shuffleboard.getTab(tableName);

        table.addNumber("Arm Position", (DoubleSupplier) m_motor.getPosition().asSupplier());
        table.addNumber("Arm Stator", (DoubleSupplier) m_motor.getStatorCurrent().asSupplier());
        table.addNumber("Arm Supply", (DoubleSupplier) m_motor.getSupplyCurrent().asSupplier());
        table.addNumber("Arm Voltage", (DoubleSupplier) m_motor.getMotorVoltage().asSupplier());
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
