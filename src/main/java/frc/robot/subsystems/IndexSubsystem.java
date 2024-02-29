package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/*For indexing cargo. That is, taking cargo from a stored position and proceeding it to the shooter.*/
public class IndexSubsystem extends SubsystemBase {
    private TalonSRX motor;
    public NoteSensorSubsystem NoteSensor;

    public IndexSubsystem() {
        this.motor = new TalonSRX(Constants.CANIDs.INDEX_ID);
        NoteSensor = new NoteSensorSubsystem();
        motor.set(ControlMode.PercentOutput, 0);

    }

    double indexPower = 0.5;

    public void RunIndexing() {
        motor.set(ControlMode.PercentOutput, indexPower);
    }

    public boolean HasCargo() {
        return NoteSensor.isNoteLoaded();
    }

    public void SetPower(double input) {

        motor.set(ControlMode.PercentOutput, input);
    }

    public void StopIndexing() {
        motor.set(ControlMode.PercentOutput, 0);
    }

}