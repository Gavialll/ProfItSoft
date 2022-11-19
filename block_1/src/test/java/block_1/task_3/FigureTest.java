package block_1.task_3;

import block_1.task_3.figures.Cube;
import block_1.task_3.figures.Cylinder;
import block_1.task_3.figures.Sphere;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FigureTest {

    @Test
    @Order(1)
    void sortByVolumeTrue() {
        List<Figure> test = getNotSortFigures();

        List<Figure> expected = getSortFigures();
        List<Figure> actual = Figure.sortByVolume(test);

        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void inputIsNull() {
        List<Figure> test = null;

        List<Figure> expected = new LinkedList<>();
        List<Figure> actual = Figure.sortByVolume(test);

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void inputIsEmpty() {
        List<Figure> test = new LinkedList<>();

        List<Figure> expected = new LinkedList<>();
        List<Figure> actual = Figure.sortByVolume(test);

        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    void throwIllegalException() {
        // I'm not sure about this test. I think it is big and unclear but I think it is needed.
        assertThrows(IllegalArgumentException.class,()->
                new Sphere().setRadius(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Sphere().setRadius(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Cube().setLength(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Cube().setHeight(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Cube().setWidth(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Cylinder().setRadius(-1));
        assertThrows(IllegalArgumentException.class,()->
                new Cylinder().setHeight(-1));
    }

    private List<Figure> getNotSortFigures(){
        return Arrays.asList(
                new Cube()
                        .setWidth(2)
                        .setHeight(2)
                        .setLength(2),
                new Cube()
                        .setWidth(21)
                        .setHeight(22)
                        .setLength(24),
                new Cylinder()
                        .setHeight(24)
                        .setDiameter(42)
                        .setRadius(21),
                new Sphere()
                        .setRadius(5),
                new Cylinder()
                        .setHeight(5)
                        .setDiameter(43)
                        .setRadius(3),
                new Sphere()
                        .setRadius(50)
                );
    }
    private List<Figure> getSortFigures(){
        return Arrays.asList(
                new Cube()
                        .setWidth(2)
                        .setHeight(2)
                        .setLength(2),
                new Cylinder()
                        .setHeight(5)
                        .setDiameter(43)
                        .setRadius(3),
                new Sphere()
                        .setRadius(5),
                new Cylinder()
                        .setHeight(24)
                        .setDiameter(42)
                        .setRadius(21),
                new Cube()
                        .setWidth(21)
                        .setHeight(22)
                        .setLength(24),
                new Sphere()
                        .setRadius(50)
        );
    }
}