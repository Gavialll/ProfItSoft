package block_1.task_3.figures;

import block_1.task_3.Figure;

import java.util.Objects;

public class Cylinder extends Figure {
    private double height;
    private double radius;
    private double diameter;

    @Override
    public double getVolume() {
        double width;
        if(diameter != 0 && radius == 0)
            width = diameter;
        else
            width = radius * 2;

        return (Math.PI * width) * height;
    }

    public Cylinder setHeight(double height) {
        isLessZero("Height", height);
        this.height = height;
        return this;
    }

    public Cylinder setRadius(double radius) {
        isLessZero("Radius", radius);
        this.radius = radius;
        return this;
    }

    public Cylinder setDiameter(double diameter) {
        isLessZero("Diameter", diameter);
        this.diameter = diameter;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.height, height) == 0 && Double.compare(cylinder.radius, radius) == 0 && Double.compare(cylinder.diameter, diameter) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, radius, diameter);
    }
}
