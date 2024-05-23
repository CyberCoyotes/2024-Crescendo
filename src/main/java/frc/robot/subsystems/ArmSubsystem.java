package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Vision.LimelightHelpers;
import frc.robot.util.ArmConfigs;
import frc.robot.util.Constants;

public class ArmSubsystem extends SubsystemBase {

        NetworkTable limelightBack; // table for the limelight
    NetworkTableEntry tv; // Table to see if there are valid targets


    // Declare a variable for the motor to be controlled
    private final TalonFX m_arm;

    // class member variable for an instance of the MotionMagicVoltage class
    final MotionMagicVoltage m_armPose = new MotionMagicVoltage(0); // Initializes to position 0

    public ArmSubsystem() {

        // Initialize the motor in the constructor with the motor ID and optional canbus
        // ID
        m_arm = new TalonFX(Constants.CANIDs.ARM_ID);

        /*
         * Send info about the arm to the Shuffleboard
         * Defaults to percent output
         * Only needed for diagnostics
         */
        // Shuffleboard.getTab("Arm").add("Arm Output", m_arm);

        // Apply configurations from the ArmConfigs file to the motor
        ArmConfigs.applyArmConfigs(m_arm);

    } /* End of the class-method */



    public StatusSignal<Double> getArmPos() {
        /* Reusing from drivetrain subsystem */
        return m_arm.getPosition();
    }

    public void setArmPose(double armPose) {
        m_arm.setControl(m_armPose.withPosition(armPose));
        // showArmTelemetry();
            
    }

    public void setArmForClimb(double power) {
        m_arm.setControl(new VoltageOut (power));
        // showArmTelemetry();


    }
    /* 
    public void showArmTelemetry() {

        Shuffleboard.getTab("Arm").add("Arm Position", m_arm.getPosition().getValue());
        Shuffleboard.getTab("Arm").add("Arm Stator", m_arm.getStatorCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Supply", m_arm.getSupplyCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Voltage", m_arm.getMotorVoltage().getValue());
    }
    */

    public void stopArm(double power) {
        m_arm.setControl(new VoltageOut(0));

    }

    public double getTarget(){
        return tv.getDouble(0.0);
}

    @Override
    public void periodic() {
        /* Only needed for diagnostics */
        /*
        Shuffleboard.getTab("Arm").add("Arm Position", m_arm.getPosition().getValue());
        Shuffleboard.getTab("Arm").add("Arm Stator", m_arm.getStatorCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Supply", m_arm.getSupplyCurrent().getValue());
        Shuffleboard.getTab("Arm").add("Arm Voltage", m_arm.getMotorVoltage().getValue());
         */

        double tid = LimelightHelpers.getFiducialID("limelight-marvin");
      if (tid == 1.0){
         setArmPose(0);
   }
    }

} // end of ArmSubsystem method