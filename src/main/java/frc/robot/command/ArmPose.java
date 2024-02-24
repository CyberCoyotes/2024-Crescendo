package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

// Previously was Command
public class ArmPose extends Command {

    private static final double ARM_POSE_ONE = 20;
    
    private ArmSubsystem m_armSub;
    // private final StatusSignal<Double> m_armGetter; // get arm 
    // private final double m_armPose; /* arm encoder position */
    private double armPose;
    private boolean isFinished = false;

    /*private double lowerArmSetpoint = 0.0;
  private double upperArmSetpoint = 0.0;
 */

    // Same name as class-command
    public ArmPose(ArmSubsystem armSub, double armPose) {
        // Using this approach (https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/CommanddDrive/src/main/java/frc/robot/commands/DriveStraightCommand.java)
        // The "m_" is for member variable; second ArmPose is for the method "intro"
        m_armSub = armSub;

        // m_armPose = armPose;
        this.armPose = armPose;
        /* From another example project
        this.lowerArmSetpoint = setpoint.lowerArmSetpoint;
        this.upperArmSetpoint = setpoint.upperArmSetpoint;
        */
        addRequirements(armSub);
    }

    @Override
    public void initialize() {
        isFinished = false;

    }

    @Override
    public void execute() {

        /* If subsystem has a set value, this value has no impact. One should NOT set the value in the subsystem generally
        It would seem that the value should be set in the Command, not Subsystem */

        /* The method is created in the subsystem, called here as 'm_armSub' */
        // m_armSub.setArmMotionMagic(ARM_POSE_ONE);

        /* Experimental */ // m_armSub.setArmDutyCycleOut(Constants.armPower);

    }

    @Override
    public void end(boolean interrupted) {
        /* 
        Test: See if arm stops when reaches point without this method.
        Reasoning: I speculate it is inteferring with Auton 
        Conclusion: After reaching its encoder position the motor appears to stop. 
        I think it would still work at higher speeds but further testing needed.
        Conditionals could be used to change the true/false status
        */

        /* Sets the motor output to zero /* Previous method */
        // m_armSub.stopRotation(); 
        isFinished = true;

    }

    @Override
    public boolean isFinished() {
        // Implement the condition for command completion
        return isFinished;
    }

}

/* NOTE
 * https://github.com/MAikenMagic1102/2023SheriffPhoenix6/blob/main/src/main/java/frc/robot/commands/ArmToSetpoint.java
 * 
 * Uses an If statement to check that position is reached.
 * 
 *  if(isFirstRun){
      m_Arm.setUpperArmSetPoint(Constants.Arm.RETRACT);
      //System.out.println("Upper Arm is set to : " + Constants.Arm.RETRACT);
      isFirstRun = false;
      //System.out.println("IsFirstRun: " + isFirstRun);
    }


    if(Math.abs(Constants.Arm.RETRACT - m_Arm.getUpperArmPosition()) < 0.2){
      m_Arm.setLowerArmSetPoint(lowerArmSetpoint);
      //System.out.println("Lower Arm is set to : " + lowerArmSetpoint);
    }

    if(Math.abs(lowerArmSetpoint - m_Arm.getLowerArmPosition()) < 0.3){
      m_Arm.setUpperArmSetPoint(upperArmSetpoint);
      //System.out.println("Upper Arm is set to : " + upperArmSetpoint);
    }


    if((Math.abs(upperArmSetpoint - m_Arm.getUpperArmPosition()) < 0.3) && (Math.abs(lowerArmSetpoint - m_Arm.getLowerArmPosition()) < 0.3)){
        //System.out.println("Complete Exiting Command");
        isFinished = true;
      }  
 */