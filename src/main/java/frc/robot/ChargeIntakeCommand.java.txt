package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class ChargeIntakeCommand extends Command {

    CommandSwerveDrivetrain m_driveTrain;
    IntakeSubsystem intake;
    SwerveRequest newRequest;
    SwerveRequest oldRequest;

    @Override
    public void execute() {

        super.execute();
        m_driveTrain.applyRequest(() -> newRequest);
        intake.Run(Constants.IntakeConstants.INTAKE_POWER);
    }

    public ChargeIntakeCommand(CommandSwerveDrivetrain dt, IntakeSubsystem intake,
            SwerveRequest.FieldCentric oldRequest) {
        this.oldRequest = oldRequest;
        this.m_driveTrain = dt;
        this.intake = intake;
        addRequirements(intake, m_driveTrain);

    }

    @Override
    public void initialize() {
        super.initialize();

        double velo = TunerConstants.kSpeedAt12VoltsMps;
        newRequest = new SwerveRequest.RobotCentric().withVelocityY(-velo);
    }

    public ChargeIntakeCommand() {
        this.addRequirements(m_driveTrain);

    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.applyRequest(() -> oldRequest);
    }
}
