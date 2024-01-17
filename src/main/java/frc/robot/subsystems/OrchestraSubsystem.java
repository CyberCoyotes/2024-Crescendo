package frc.robot.subsystems;

import java.util.ArrayList;
import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.controls.MusicTone;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OrchestraSubsystem extends SubsystemBase{

ArrayList<TalonFX> instruments;
Orchestra m_orchestra;

public OrchestraSubsystem(TalonFX[] motors) {

m_orchestra = new Orchestra();
//add instruments
    for (int i = 0; i < motors.length; i++ ) {
     m_orchestra.addInstrument(motors[i], i);
     motors[i].setControl(new MusicTone(i));
    }}

// Attempt to load the chrp

public void SetTune(Song song)
{
    
    m_orchestra.loadMusic(song.name()+ ".chrp");
}
public void Play()
{
    m_orchestra.play();
}
public void Shud()
{
    m_orchestra.stop();
}

public enum Song{
ICE_CREAM,
E1M1,
ONE_ONE_FIVE,
ULTRA_INSTINCT,
SANS
}
}