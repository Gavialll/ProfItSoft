package block_1.task_3.figures;

import block_1.task_3.Figure;

import java.util.Objects;

public class Cube extends Figure {
    private double width;
    private double length;
    private double height;

    @Override
    public double getVolume() {
        return width * length * height;
    }

    public Cube setWidth(int width) {
        isLessZero("Width" , width);
        this.width = width;
        return this;
    }

    public Cube setLength(int length) {
        isLessZero("Length" , length);
        this.length = length;
        return this;
    }

    public Cube setHeight(int height) {
        isLessZero("Height" , height);
        this.height = height;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return Double.compare(cube.width, width) == 0 && Double.compare(cube.length, length) == 0 && Double.compare(cube.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length, height);
    }
}
