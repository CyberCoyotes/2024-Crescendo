package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.NoteSensorSubsystem;

/**
 * Run Once or Continuously
 */
public class SetIntake2 extends Command {
    private IntakeSubsystem intake;
    private NoteSensorSubsystem notesensor;
    private double power;

    public SetIntake2(IntakeSubsystem intake, NoteSensorSubsystem notesensor, double power) {
        this.intake = intake;
        this.notesensor = notesensor;
        this.power = power;
        addRequirements(intake, notesensor);
    }

    @Override
    public void initialize() {
        intake.Run(this.power);
    }
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {
      intake.Run(0);
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