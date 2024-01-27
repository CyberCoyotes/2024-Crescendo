package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.NoteSensorSubsystem;

/* A simple but often used command to get a loaded (true) or nonloaded (false) status of the time of flight sensor pointed at the 'note' */
public class GetNoteStatus extends Command {
    /*  */

        private final NoteSensorSubsystem m_noteSensorSub;

        public GetNoteStatus(NoteSensorSubsystem noteSensorSub) {
            this.m_noteSensorSub = noteSensorSub;
            addRequirements(noteSensorSub);
        }

        @Override
        public void initialize() {
            // Code to run when the command is first initialized
        }

        @Override
        public void execute() {
            // Code to run repeatedly while the command is scheduled to run
            m_noteSensorSub.isNoteLoaded();
        }

        @Override
        public void end(boolean interrupted) {
            // Code to run when the command ends or is interrupted
        }

        @Override
        public boolean isFinished() {
            // Return true when the command should end
            return false;
        }

    } // end of class GetNoteStatus
