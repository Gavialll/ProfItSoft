package block_1.task_3.figures;

import block_1.task_3.Figure;

import java.util.Objects;

public class Sphere extends Figure {
    private double radius;
    private double diameter;

    @Override
    public double getVolume() {
        double width;
        if(diameter != 0 && radius == 0)
            width = diameter / 2;
        else
            width = radius;

        double widthInCube = width * width * width;

        return (4 * Math.PI * widthInCube) / 3;
    }

    public Sphere setRadius(double radius) {
        isLessZero("Radius", radius);
        this.radius = radius;
        return this;
    }

    public Sphere setDiameter(double diameter) {
        isLessZero("Diameter", diameter);
        this.diameter = diameter;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && Double.compare(sphere.diameter, diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, diameter);
    }
}
