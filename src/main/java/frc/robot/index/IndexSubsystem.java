package frc.robot.index;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Constants;
import frc.robot.util.NoteSensor;

/*For indexing cargo. That is, taking cargo from a stored position and proceeding it to the shooter.*/
public class IndexSubsystem extends SubsystemBase {
    private TalonSRX motor;
    public NoteSensor NoteSensor;

    public IndexSubsystem() {
        this.motor = new TalonSRX(Constants.INDEX_ID);
        NoteSensor = new NoteSensor();
        motor.set(ControlMode.PercentOutput, 0);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    public void runIndexing() {
        motor.set(ControlMode.PercentOutput, IndexConstants.INDEX_POWER);
    }

    public boolean hasCargo() {
        return NoteSensor.isNoteLoaded();
    }

    public void setIndexPower(double percent) {

        motor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIndexing() {
        motor.set(ControlMode.PercentOutput, 0);
    }

}