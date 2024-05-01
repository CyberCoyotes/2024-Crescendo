/*
 * Super basic class to get data from the limelight
 * It imports necessary classes from the edu.wpi.first.networktables package. NetworkTables is a key-value pair network communication protocol used in FRC.
 * It declares a NetworkTable object table and four NetworkTableEntry objects tx, ty, ta, and tv.
 * In the constructor Vision3(), it initializes the table object to the "limelight" table in the NetworkTable. It also initializes tx, ty, ta, and tv to entries in this table.
 * tx: Horizontal offset from the crosshair to the target (-27 degrees to 27 degrees).
 * ty: Vertical offset from the crosshair to the target (-20.5 degrees to 20.5 degrees).
 * ta: Area that the target occupies in total image percentage (0% to 100%).
 * tv: Whether the Limelight has any valid targets (0 or 1).
 * It provides getter methods getX(), getY(), and getArea() to retrieve the values of tx, ty, and ta respectively. These methods return the double value of the corresponding entry, or 0.0 if the entry does not exist.
 */

package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision3 {

    NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;
    NetworkTableEntry tv; // 

    public Vision3() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
    }

    public double getX() {
        return tx.getDouble(0.0);
    }

    public double getY() {
        return ty.getDouble(0.0);
    }

    public double getArea() {
        return ta.getDouble(0.0);
    }

    public boolean isTargetVisible() {
        return tv.getDouble(0) == 1;
    }
}