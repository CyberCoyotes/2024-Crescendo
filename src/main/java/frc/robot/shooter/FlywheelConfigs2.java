package frc.robot.shooter;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

public class FlywheelConfigs2 {

    public static void applyFlywheelConfigs(TalonFX primaryMotor, TalonFX secondaryMotor) {

        TalonFXConfiguration flywheelConfigs = new TalonFXConfiguration();

        Slot0Configs slot0 = flywheelConfigs.Slot0;
        slot0.kP = 0.10; // <- Tune this if needed
        slot0.kI = 0.00;
        slot0.kS = 0.00;
        slot0.kV = 0.005; // <- Tune this if needed

        VoltageConfigs volt = flywheelConfigs.Voltage;
        volt.PeakForwardVoltage = 12;
        volt.PeakReverseVoltage = -12;
                
        CurrentLimitsConfigs current = flywheelConfigs.CurrentLimits;
        current.StatorCurrentLimit = 60;
        current.StatorCurrentLimitEnable = true;

        primaryMotor.setInverted(true);
        secondaryMotor.setInverted(true); 

        /* Apply Configs - This approach seems to work the same as other Config approach */
        /* Commenting out because we are using FlywheelConfigs

        primaryMotor.getConfigurator().apply(slot0, 0.050);
        primaryMotor.getConfigurator().apply(volt, 0.050);
        primaryMotor.getConfigurator().apply(current, 0.050);
    
        secondaryMotor.getConfigurator().apply(slot0, 0.050);
        secondaryMotor.getConfigurator().apply(volt, 0.050);
        secondaryMotor.getConfigurator().apply(current, 0.050);
         */

    } // end of constructor

} // end of class