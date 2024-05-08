package frc.robot.experimental;

import java.util.Map;
import java.util.TreeMap;

/*  */
public class ShooterSubsystem2 {
    // TreeMap to store known arm poses and corresponding ty values
    private TreeMap<Double, Double> knownPoses = new TreeMap<>();

    // Method to set arm pose and corresponding ty value
    public void setArmPose(double armPose, double ty) {
        knownPoses.put(armPose, ty);
    }

    // Method to get interpolated ty value for a given arm pose
    public double getInterpolatedTy(double armPose) {
        // Get the two arm poses closest to the given arm pose
        Double lower = knownPoses.floorKey(armPose);
        Double higher = knownPoses.ceilingKey(armPose);

        if (lower == null && higher == null) {
            throw new IllegalArgumentException("No known poses to interpolate from");
        }

        if (lower == null) {
            return knownPoses.get(higher);
        }

        if (higher == null) {
            return knownPoses.get(lower);
        }

        // If the given arm pose is exactly known, return the corresponding ty value
        if (lower.equals(higher)) {
            return knownPoses.get(lower);
        }

        // Perform linear interpolation
        double tyLower = knownPoses.get(lower);
        double tyHigher = knownPoses.get(higher);
        return tyLower + (armPose - lower) * (tyHigher - tyLower) / (higher - lower);
    }
} // end of class ShooterSubsystem2

/* In this code, `ShooterSubsystem` maintains a `TreeMap` of known arm poses and corresponding `ty` values.
 The `setArmPose()` method is used to set a known arm pose and its corresponding `ty` value. 
 The `getInterpolatedTy()` method is used to get an interpolated `ty` value for a given arm pose. 
 It finds the two known arm poses closest to the given arm pose and performs linear interpolation to calculate the `ty` value. 
 If the given arm pose is exactly known, it returns the corresponding `ty` value. 
 If there are no known arm poses to interpolate from, it throws an exception.
 */