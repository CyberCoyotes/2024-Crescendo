package frc.robot.util;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

/* Verbose? Probably. Effective? I hope so */
public class FlywheelConfigs3 {

    public static void applyFlywheelConfigs(TalonFX primaryMotor, TalonFX secondaryMotor) {

        TalonFXConfiguration flywheelConfigs = new TalonFXConfiguration();

        // primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
        // secondaryMotor.getConfigurator().apply(new TalonFXConfiguration());

        Slot0Configs slot0 = flywheelConfigs.Slot0;
        slot0.kP = 0.10;
        slot0.kI = 0.00;
        slot0.kS = 0.00;
        slot0.kV = 0.005;

        VoltageConfigs volt = flywheelConfigs.Voltage;
        volt.PeakForwardVoltage = 12;
        volt.PeakReverseVoltage = -12;
                
        CurrentLimitsConfigs current = flywheelConfigs.CurrentLimits;
        current.StatorCurrentLimit = 60;
        current.StatorCurrentLimitEnable = true;

        /* TODO Test neutral mode with the class being called in Configs file */
        /*
        var flywhevelMotorOutput = new MotorOutputConfigs();
        flywheelMotorOutput
                .withNeutralMode(NeutralModeValue.Coast);
         */

        primaryMotor.setInverted(true);
        secondaryMotor.setInverted(true); 

        /* Apply Configs */
        /* TODO Test this approach and see if it also works
         
        primaryMotor.getConfigurator().apply(slot0, 0.050);
        primaryMotor.getConfigurator().apply(volt, 0.050);
        primaryMotor.getConfigurator().apply(current, 0.050);
    
        secondaryMotor.getConfigurator().apply(slot0, 0.050);
        secondaryMotor.getConfigurator().apply(volt, 0.050);
        secondaryMotor.getConfigurator().apply(current, 0.050);
        */

    } // end of constructor

} // end of class