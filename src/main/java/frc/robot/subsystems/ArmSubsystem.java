package frc.robot.subsystems;

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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase{

    private static final int ARM_MAX_ACCEL = 80;
    private static final int ARM_MAX_VEL = 160;
    private static final int ARM_JERK = 0;
    private static int ARM_ID = 22; // Initialize the armMotorID variable
    private static final int ARM_FWD_LIMIT = 50;
    private static final int ARM_REV_LIMIT = 0;
    private static final int ARM_STATOR_LIMIT = 40;
    private static final int ARM_SUPPLY_LIMIT = 40;
    
    
    // Declare a variable for the motor you want to control
    private final TalonFX m_arm;
    // reference
    // https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/CommanddDrive/src/main/java/frc/robot/subsystems/DriveSubsystem.java

    // class member variable
    final MotionMagicVoltage m_armPose = new MotionMagicVoltage(0); // Initializes to position 0

    
    public ArmSubsystem () {

        // Initialize the motor in the constructor with the motor ID and optional canbus ID
        m_arm = new TalonFX(ARM_ID);

        /* any unmodified configs in a configuration object are *automatically* factory-defaulted;
        user can perform a full factory default by passing a new device configuration object.
        This line should do a full factory reset */
        m_arm.getConfigurator().apply(new TalonFXConfiguration());

        // m_arm.setControl(NeutralModeValue);
        
        /* Gets the initialization of the device in the subsystem from an optional 
        'initialization method' farther down in the class.
        For example: 
        initializeArmTalonFX(m_arm.getConfigurator()); 
        */

        // var armConfigs = new TalonFXConfiguration();
        /* shortcut assigning a variable for to .getConfigurator() */ 
        // var armConfigurator = m_arm.getConfigurator();

        /* Gains or configuration of arm motor for config slot 0*/ 
        var armGains0 = new Slot0Configs();
        armGains0.GravityType = GravityTypeValue.Arm_Cosine; /* .Elevator_Static | .Arm_Cosine*/
        armGains0.kP =   1.00;   /* Proportional Gain */
        armGains0.kI =   0.00;   /* Integral Gain */
        armGains0.kD =   0.00;   /* Derivative Gain */
        armGains0.kV =   0.00;   /* Velocity Feed Forward Gain */
        armGains0.kS =   0.00;   /* Static Feed Forward Gain // Approximately 0.25V to get the mechanism moving */
        armGains0.kA =   0.00;   /* Acceleration Feedforward */
        armGains0.kG =   0.00;   /* Gravity Feedfoward */

        /* Gains or configuration of arm motor for config slot 1*/ 
        var armGains1 = new Slot1Configs();
        armGains1.GravityType = GravityTypeValue.Arm_Cosine; /* .Elevator_Static | .Arm_Cosine*/
        armGains1.kP =   1.00;   /* Proportional Gain */
        armGains1.kI =   0.00;   /* Integral Gain */
        armGains1.kD =   0.00;   /* Derivative Gain */
        armGains1.kV =   0.00;   /* Velocity Feed Forward Gain */
        armGains1.kS =   0.00;   /* Static Feed Forward Gain // Approximately 0.25V to get the mechanism moving */
        armGains1.kA =   0.00;   /* Acceleration Feedforward */
        armGains1.kG =   0.00;   /* Gravity Feedfoward */
        
        // set Motion Magic settings
        var armMotionMagic0 = new MotionMagicConfigs();
        
        armMotionMagic0.MotionMagicCruiseVelocity = ARM_MAX_VEL/5; // 80 rps cruise velocity // FIMXE changed for safety testing
        armMotionMagic0.MotionMagicAcceleration = ARM_MAX_ACCEL/5; // 160 rps/s acceleration (0.5 seconds) // FIMXE changed for safety testing
  
        armMotionMagic0.MotionMagicJerk = ARM_JERK; // 1600 rps/s^2 jerk (0.1 seconds)

        var armMotionMagic1 = new MotionMagicConfigs();
        armMotionMagic1.MotionMagicCruiseVelocity = ARM_MAX_VEL/2; // 80 rps cruise velocity
        armMotionMagic1.MotionMagicAcceleration = ARM_MAX_ACCEL/2; // 160 rps/s acceleration (0.5 seconds)
        armMotionMagic1.MotionMagicJerk = 0; // 1600 rps/s^2 jerk (0.1 seconds)

        var armSoftLimit0 = new SoftwareLimitSwitchConfigs();
        armSoftLimit0.ForwardSoftLimitEnable = true;
        armSoftLimit0.ForwardSoftLimitThreshold = ARM_FWD_LIMIT; 
        armSoftLimit0.ReverseSoftLimitEnable = true;
        armSoftLimit0.ReverseSoftLimitThreshold = ARM_REV_LIMIT;
        
        var armCurrent0 = new CurrentLimitsConfigs();
        armCurrent0.StatorCurrentLimitEnable = true;
        armCurrent0.StatorCurrentLimit = ARM_STATOR_LIMIT;
        armCurrent0.SupplyCurrentLimitEnable = true;
        armCurrent0.SupplyCurrentLimit = ARM_SUPPLY_LIMIT; 

        /* Applies the configuration shortcut-variable with the apply*/
        // armConfigurator.apply(armConfigs);

        /* Long form (better for my learning): Applies gains with an optional 50 ms timeout (I think) */ 
        m_arm.getConfigurator().apply(armGains0, 0.050);
        m_arm.getConfigurator().apply(armMotionMagic0, 0.050);
        m_arm.getConfigurator().apply(armSoftLimit0, 0.050);
        m_arm.getConfigurator().apply(armCurrent0,0.050);

        /* Send info about the arm to the Smart Dashboard 
         * Defaults to percent output
        */
        // SmartDashboard.putData("Arm Output", m_arm);
        
    } /* End of the class-method */

    
    public StatusSignal<Double> getArmPos() {

        /* Reusing from drivetrain subsystem */
        return m_arm.getPosition();
        
    }

    
    /* An example of a motor intilization method */
    
    /* 
    private void initializeArmTalonFX(TalonFXConfigurator cfg) {

        var toApply = new TalonFXConfiguration();

        /* Configure current limits */
    // }

    /* This calls a 'long form' of MotionMagicVoltage */
    public void setArmMotionMagicVoltage (double armPose) {

        m_arm.setControl(
            new MotionMagicVoltage(
                armPose, 
                false, 
                armPose, 
                ARM_ID, 
                false, 
                false, 
                false));

    }

    public void setArmPose (double armPose) {

        /* Documentation */             //m_arm.Slot = 0;
        /* Documentation version is Iterative */
        //  m_arm.setControl(m_arm.withPosition(200));
        /* Command Based implementation */ 
        m_arm.setControl(
            /* 'long form' of MotionMagicVoltage motor travels at full speed & coast like a velocity output
             * 'short form' of MotionMagicVoltage motor sets to position
             */
            new  MotionMagicVoltage(armPose));

            // SmartDashboard.putNumber(m_arm.getPosition().getValue());
            
            // SmartDashboard.putNumber("Arm Position", m_arm.getPosition().getValue());
            // SmartDashboard.putNumber("Arm Stator", m_arm.getStatorCurrent().getValue());
            showArmTelemetry();
            
    }

    public void setArmToStart() {
        m_arm.setControl(new MotionMagicVoltage(0));
    }

    public void setArmToScorePose1() {
        m_arm.setControl(new MotionMagicVoltage(10));
    }

    public void setArmToScorePose2() {
        m_arm.setControl(new MotionMagicVoltage(20));
    }

    public void setArmToScorePose3() {
        m_arm.setControl(new MotionMagicVoltage(30));
    }

    public void setArmToScorePose4() {
        m_arm.setControl(new MotionMagicVoltage(40));
    }

    public void stopRotation() {
        m_arm.setControl(new DutyCycleOut(0));
    }

    /* Currently only being called in subsystem-command; 
    inspite of my efforts it only appears once it's triggered at least once */
    public void showArmTelemetry() {
        SmartDashboard.putNumber("Arm Position", m_arm.getPosition().getValue());
        SmartDashboard.putNumber("Arm Stator", m_arm.getStatorCurrent().getValue());
        SmartDashboard.putNumber("Arm Supply", m_arm.getSupplyCurrent().getValue());
        SmartDashboard.putNumber("Arm Voltage", m_arm.getMotorVoltage().getValue());
    }

    
} // end of ArmSubsystem method

/***************************
 * NOTES
***************************/
/* 
    TalonFXConfiguration configuration = new TalonFXConfiguration();
    configuration.motionCruiseVelocity = FAST_MOTION_CONSTRAINTS.maxVelocity / SENSOR_VELOCITY_COEFFICIENT;
    configuration.motionAcceleration = FAST_MOTION_CONSTRAINTS.maxAcceleration / SENSOR_VELOCITY_COEFFICIENT;
    configuration.slot0.kP = 0.25;
    configuration.slot0.kI = 0.0;
    configuration.slot0.kD = 0.0;
    configuration.primaryPID.selectedFeedbackSensor = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
    configuration.voltageCompSaturation = 12.0;
    */
/* https://github.com/FRCTeam2910/2023CompetitionRobot-Public/blob/main/src/main/java/org/frcteam2910/c2023/subsystems/arm/ArmIOFalcon500.java */
       