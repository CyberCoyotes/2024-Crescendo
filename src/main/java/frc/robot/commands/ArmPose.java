package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ArmPose extends Command {

  private ArmSubsystem m_armSub;
  private double armPose;
  private boolean isFinished = false;

  // Same name as class-command
  public ArmPose(ArmSubsystem armSub, double armPose) {
    // Using this approach
    // (https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/CommanddDrive/src/main/java/frc/robot/commands/DriveStraightCommand.java)
    m_armSub = armSub;

    // m_armPose = armPose;
    this.armPose = armPose;

    addRequirements(armSub);
  }

  @Override
  public void initialize() {
    isFinished = false;

  }

  @Override
  public void execute() {

    /*
     * If subsystem has a set value, this value has no impact. One should NOT set
     * the value in the subsystem generally
     * It would seem that the value should be set in the Command, not Subsystem
     */

  }

  @Override
  public void end(boolean interrupted) {
    /*
     * Test: See if arm stops when reaches point without this method.
     * Reasoning: I speculate it is inteferring with Auton
     * Conclusion: After reaching its encoder position the motor appears to stop.
     * I think it would still work at higher speeds but further testing needed.
     * Conditionals could be used to change the true/false status
     */

    /* Sets the motor output to zero /* Previous method */
    isFinished = true;

  }

  @Override
  public boolean isFinished() {
    // Implement the condition for command completion
    return isFinished;
  }

}
