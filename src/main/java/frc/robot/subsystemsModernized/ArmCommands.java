package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ArmCommands {
    private final ArmSubsystem armSubsystem;

    public ArmCommands(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    /**
     * Creates a command to move the arm to a specified position
     * @param position Target position in native units
     * @return Command that will move the arm
     */
    public Command setPosition(double position) {
        return armSubsystem.setPositionCommand(position)
            .withName("SetArmPosition")
            .withTimeout(2.0); // Safety timeout
    }

    /**
     * Creates a command for manual control of the arm
     * @param voltage Voltage to apply (-12 to 12)
     * @return Command for manual control
     */
    public Command setVoltage(double voltage) {
        return armSubsystem.setVoltageCommand(voltage)
            .withName("ManualArmControl");
    }

    /**
     * Creates a command to stop the arm
     * @return Command that will stop the arm
     */
    public Command stop() {
        return armSubsystem.stopCommand()
            .withName("StopArm");
    }

    // Commonly used positions as command factories
    public Command setHome() {
        return setPosition(0).withName("ArmHome");
    }

    public Command setLow() {
        return setPosition(10).withName("ArmLow");
    }

    public Command setMid() {
        return setPosition(25.5).withName("ArmMid");
    }

    public Command setHigh() {
        return setPosition(55).withName("ArmHigh");
    }

    public Command setAmp() {
        return setPosition(90).withName("ArmAmp");
    }
}