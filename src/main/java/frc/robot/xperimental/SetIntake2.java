package frc.robot.xperimental;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.util.NoteSensor;

/**
 * Run Once or Continuously
 */
public class SetIntake2 extends Command {
    private IntakeSubsystem intake;
    private NoteSensor notesensor;
    private double power;

    public SetIntake2(IntakeSubsystem intake, NoteSensor notesensor, double power) {
        this.intake = intake;
        this.notesensor = notesensor;
        this.power = power;
        addRequirements(intake, notesensor);
    }

    @Override
    public void initialize() {
        intake.powerIntake(this.power);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      intake.stopIntake();
    }
  
    @Override
    public boolean isFinished() {
      if (notesensor.isNoteLoaded()) {
        return true;
      } else {
        return false;
      }
    }

} // end of class SetIntake2