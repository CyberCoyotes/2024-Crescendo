# Subsystem or not?
In the FRC (FIRST Robotics Competition) Java command-based framework, Limelight is typically not considered a subsystem on its own. Instead, Limelight is usually treated as a sensor or a component within a subsystem.

In the context of the command-based framework, subsystems represent physical components or systems of the robot, such as drivetrain, elevator, arm, etc. These subsystems are responsible for managing the hardware and controlling the behavior of those components.

Limelight, on the other hand, is a vision processing system used for targeting or other vision-related tasks. It typically interfaces with a subsystem, such as the drivetrain or shooter subsystem, to provide information for targeting or aiming. In this case, you might create a Limelight wrapper class within the subsystem that interacts with the Limelight hardware and processes its data, but the Limelight itself is not a standalone subsystem.

So, in summary, while Limelight is a crucial component of many FRC robots, it's usually integrated into existing subsystems rather than being considered a subsystem itself in the command-based framework.