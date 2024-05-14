package frc.robot.xperimental;

import edu.wpi.first.math.interpolation.Interpolatable;

import java.io.Serializable;

public class InterpolatingDouble2910 implements Interpolatable<InterpolatingDouble2910>, InverseInterpolatable2910<InterpolatingDouble2910>, Comparable<InterpolatingDouble2910>, Serializable {
    private static final long serialVersionUID = -4236134181182630613L;

    public Double value;

    public InterpolatingDouble2910(Double value) {
        this.value = value;
    }

    @Override
    public int compareTo(InterpolatingDouble2910 other) {
        return Double.compare(value, other.value);
    }

    @Override
    public InterpolatingDouble2910 interpolate(InterpolatingDouble2910 other, double t) {
        double delta = other.value - value;
        return new InterpolatingDouble2910(value + delta * t);
    }

    @Override
    public double inverseInterpolate(InterpolatingDouble2910 upper, InterpolatingDouble2910 query) {
        double delta = upper.value - value;
        double deltaQuery = query.value - value;

        return deltaQuery / delta;
    }
}
