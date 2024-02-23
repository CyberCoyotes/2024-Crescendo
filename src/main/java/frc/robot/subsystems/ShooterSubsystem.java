package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    public static final int RIGHT_FLYWHEEL_ID = 24; // Initialize the shooterMotorID variable

    private final TalonFX m_shooter;

    final VelocityVoltage m_shooterVelocity = new VelocityVoltage(0); // Initializes to position 0

    public ShooterSubsystem() {
        m_shooter = new TalonFX(RIGHT_FLYWHEEL_ID);

    m_shooter.getConfigurator().apply(new TalonFXConfiguration());

      
    TalonFXConfiguration shooterConfigs0 = new TalonFXConfiguration();

    /* Voltage-based velocity requires a feed forward to account for the back-emf of the motor */
    shooterConfigs0.Slot0.kP = 0.11; // An error of 1 rotation per second results in 2V output
    shooterConfigs0.Slot0.kI = 0.5; // An error of 1 rotation per second increases output by 0.5V every second
    shooterConfigs0.Slot0.kD = 0.0001; // A change of 1 rotation per second squared results in 0.01 volts output
    shooterConfigs0.Slot0.kV = 0.12; 
    // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / Rotation per second
    
    // Peak output of 8 volts
    shooterConfigs0.Voltage.PeakForwardVoltage = 8;
    shooterConfigs0.Voltage.PeakReverseVoltage = -8; 

    var shooterCurrent0 = new CurrentLimitsConfigs();
    shooterCurrent0.StatorCurrentLimitEnable = true;
    shooterCurrent0.StatorCurrentLimit = 60;

    m_shooter.getConfigurator().apply(shooterConfigs0, 0.050);
    m_shooter.getConfigurator().apply(shooterCurrent0, 0.050);

 }

   /* This calles a 'long form' of  */
    public void setShooter (double wheelVelocity) {

        m_shooter.setControl(new VelocityVoltage(wheelVelocity));

    }

}
