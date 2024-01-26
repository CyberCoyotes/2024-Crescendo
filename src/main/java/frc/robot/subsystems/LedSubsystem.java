package frc.robot.subsystems;

/* Subsystem class for controlling the LEDs on the robot. used to indicate the status of other subsystems.
* It will be use data from the Time of Flight sensor data (NoteSensorSubsystem) for determining the load status/position of a 'note'
* It is also partially cosmetic
*/

/* 
CANdle requires the Phoenix (v5) vendordep
https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2024-latest.json

com.ctre.phoenix.led.CANdle Class Reference 
https://api.ctr-electronics.com/phoenix/release/java/
*/

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.RainbowAnimation;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LedSubsystem extends SubsystemBase{
    private final CANdle m_candle = new CANdle(30, "rio");
    private final int LedCount = 69;
    private XboxController joystick;
        
    /* Constructor */
    //private AnimationTypes m_currentAnimation;
    public LedSubsystem(XboxController joy) {
        this.joystick = joy;
        //changeAnimation(AnimationTypes.SetAll);
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.GRB;
        configAll.brightnessScalar = 0.1;
        configAll.vBatOutputMode = VBatOutputMode.Modulated;
        m_candle.configAllSettings(configAll, 100);

    }
        public double getVbat() { return m_candle.getBusVoltage(); }
        public double get5V() { return m_candle.get5VRailVoltage(); }
        public void configBrightness(double percent, LedSubsystem ledSubsystem) { m_candle.configBrightnessScalar(percent, 0); }
        public void configLos(boolean disableWhenLos) { m_candle.configLOSBehavior(disableWhenLos, 0); }
        public void configLedType(LEDStripType type) { m_candle.configLEDType(type, 0); }
        public void configStatusLedBehavior(boolean offWhenActive) { m_candle.configStatusLedState(offWhenActive, 0); }
    
        public void ColorOrange (){
            m_candle.setLEDs(255,60,0) ;
         }
         public void ColorRed (){
            m_candle.setLEDs(255,0,0) ;
         }  
         public void ColorGreen (){
            m_candle.setLEDs(0,255,0) ;
         }
         public void ColorBlue (){
            m_candle.setLEDs(0,0,255) ;
         }
     
        public void noteLoadedColor (){
            m_candle.setLEDs(0,255,0) ;
         }
         
         /*******************************
         Color       |   RGB Values
         ----------------------------
         Red         |   (255, 0, 0)
         Green       |   (0, 255, 0)
         Blue        |   (0, 0, 255)
         Orange      |   (255, 165, 0)
         Dark Orange |   (255, 60,0) looks more orange to Scoy Senior
     
         ********************************/
    } // end of class LedSubsystem
