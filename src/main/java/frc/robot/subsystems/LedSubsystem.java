package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;

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

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LedSubsystem extends SubsystemBase {
   private final CANdle m_candle = new CANdle(Constants.CANIDs.CANDLE_ID);

   /* Update once the LEDs are installed if using Animations */
   private final int LedCount = 69;
   public enum AnimationTypes {
      ColorFlowRed,
      ColorFlowRedReverse,
      ColorFlowBlue,
      // Fire, 
      // Larson, 
      // Rainbow,
      // RgbFade,
      // SingleFade,
      // Strobe,
      // Twinkle,
      // TwinkleOff,
      AnimationsOff
  }

   /* Constructor */
   private AnimationTypes m_currentAnimation;
   public Animation m_toAnimate = null;

   public LedSubsystem() {
      // this.joystick = joy;
      changeAnimation(AnimationTypes.AnimationsOff);
      CANdleConfiguration configAll = new CANdleConfiguration();
      configAll.statusLedOffWhenActive = true;
      configAll.disableWhenLOS = false;
      configAll.stripType = LEDStripType.GRB;
      configAll.brightnessScalar = 1.0; // 0 to 1 Scale. Previously tested 0.1
      configAll.vBatOutputMode = VBatOutputMode.Modulated;
      m_candle.configAllSettings(configAll, 100);
   }

   public double getVbat() {
      return m_candle.getBusVoltage();
   }

   public double get5V() {
      return m_candle.get5VRailVoltage();
   }

   public void configBrightness(double percent, LedSubsystem ledSubsystem) {
      m_candle.configBrightnessScalar(percent, 0);
   }

   public void configLos(boolean disableWhenLos) {
      m_candle.configLOSBehavior(disableWhenLos, 0);
   }

   public void configLedType(LEDStripType type) {
      m_candle.configLEDType(type, 0);
   }

   public void configStatusLedBehavior(boolean offWhenActive) {
      m_candle.configStatusLedState(offWhenActive, 0);
   }

   public void ColorOrange() {
      m_candle.setLEDs(255, 60, 0);
   }

   public void ColorRed() {
      m_candle.setLEDs(255, 0, 0);
   }

   public void ColorGreen() {
      AnimeOFFMaybe();
      m_candle.setLEDs(0, 255, 0);
   }

   public void ColorBlue() {
      AnimeOFFMaybe();
      m_candle.setLEDs(0, 0, 255);
   }

   public void ColorYellow() {
      m_candle.setLEDs(255, 255, 0);
   }
  
   public void ColorFlowRed() {
      m_candle.animate(m_toAnimate);
      changeAnimation(AnimationTypes.ColorFlowRed);
   }
    public void ColorFlowRedReverse() {
      m_candle.animate(m_toAnimate);
      changeAnimation(AnimationTypes.ColorFlowRedReverse);
   }
    public void ColorFlowBlue() {
      m_candle.animate(m_toAnimate);
      changeAnimation(AnimationTypes.ColorFlowBlue);
   }
   /*******************************
    * Color | RGB Values
    * ----------------------------
    * Red | (255, 0, 0)
    * Green | (0, 255, 0)
    * Blue | (0, 0, 255)
    * Orange | (255, 165, 0)
    * Dark Orange | (255, 60,0)
    * Yellow | (255, 255,0)
    * 
    ********************************/
    public void AnimeOFFMaybe() {
      changeAnimation(AnimationTypes.AnimationsOff);
  }
    public void changeAnimation(AnimationTypes toChange) {
      m_currentAnimation = toChange;
      
      switch(toChange)
      {
     case ColorFlowRed:
       m_toAnimate = new ColorFlowAnimation(255, 0, 0, 0, 0.7, LedCount, Direction.Forward);
       break;
     case ColorFlowRedReverse:
       m_toAnimate = new ColorFlowAnimation(255, 0, 0, 0, 0.7, LedCount, Direction.Backward);
       break; 
     case ColorFlowBlue:
       m_toAnimate = new ColorFlowAnimation(0, 0, 255,0 , 0.7, LedCount, Direction.Forward);
       break;
     case AnimationsOff:
         m_toAnimate = null;
    break;
   }

   System.out.println("Changed to " + m_currentAnimation.toString());
   }
} // end of class LedSubsystem