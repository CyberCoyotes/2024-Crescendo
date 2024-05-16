/* Using ChatGPT
In an FRC command based Java framework, write a class that use Cross the Roads Electronics (CTRE) candle and addressable LED strips to do the following:
* Animation mode that turns on one section of LEDS at 100% power with color red,
       * The section precedeing will be at 50% power with color red, 
       * and the section before that will be at 25% power with color red.
       * The animation will then move to the next section and repeat the process until it reaches the end of the LED strip which `LEDCount`
       * The process will then reverse and repeat until it reaches the start of the LED strip.
       * The entire process will repeat indefinitely.

*/


package frc.robot.xperimental;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;

@SuppressWarnings("unused")

public class LEDCyclon {
    
    // Here's a Java class that implements the described functionality using the WPILib library for FRC robotics and assuming you have set up the CTRE components appropriately in your robot code:
    
        private AddressableLED ledStrip;
        private int LEDCount;
        private Color[] colors;
        private int currentLEDIndex;
        private boolean forwardDirection;
        
        public LEDCyclon(AddressableLED ledStrip, int LEDCount) {
            this.ledStrip = ledStrip;
            this.LEDCount = LEDCount;
            this.colors = new Color[LEDCount];
            this.currentLEDIndex = 0;
            this.forwardDirection = true;
            
            // Initialize LED strip colors to black (off)
            for (int i = 0; i < LEDCount; i++) {
                colors[i] = Color.kBlack;
            }
            
            // Set the length of the LED strip
            ledStrip.setLength(LEDCount);
            
            // Start the LED strip output
            ledStrip.start();
        }
        
        public void runAnimation() {
            while (!DriverStation.isDisabled()) {
                // Set current LED color and brightness
                setColorAndBrightness();
                
                // Update LED strip
                // ledStrip.setData(colors);
                
                // Delay between LED updates
                Timer.delay(0.05); // Adjust this delay as needed
            }
        }
        
        private void setColorAndBrightness() {
            // Determine colors and brightness for current LED section
            Color baseColor = Color.kRed;
            int baseBrightness = 100;
            int brightnessStep = 50;
            int numSections = 3; // Number of LED sections with different brightness
            
            // Reset all LED colors to black (off)
            for (int i = 0; i < LEDCount; i++) {
                colors[i] = Color.kBlack;
            }
            
            // Set colors and brightness for current LED section
            for (int i = 0; i < numSections; i++) {
                int index = (currentLEDIndex + i) % LEDCount;
                colors[index] = baseColor;
                setBrightness(index, baseBrightness - (i * brightnessStep));
            }
            
            // Move to next LED section
            if (forwardDirection) {
                currentLEDIndex++;
                if (currentLEDIndex >= LEDCount) {
                    currentLEDIndex = LEDCount - 1;
                    forwardDirection = false;
                }
            } else {
                currentLEDIndex--;
                if (currentLEDIndex < 0) {
                    currentLEDIndex = 0;
                    forwardDirection = true;
                }
            }
        }
        
        private void setBrightness(int index, int brightness) {
            // Clamp brightness value between 0 and 100
            brightness = Math.max(0, Math.min(100, brightness));
            
            // Convert brightness percentage to duty cycle (0-255)
            int dutyCycle = (int) (brightness * 2.55);
            
            // Set LED brightness
            // ledStrip.setLEDOutput(index, new Color(0, 0, 0), dutyCycle);
        }
    }

    
    // Make sure to instantiate this class with the appropriate `AddressableLED` object and the number of LEDs in your strip. Also, ensure that you call the `runAnimation()` method in your robot code to start the LED animation loop.
