package block_1.task_1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NumbersTest {

    @Test
    @Order(1)
    void differentNumbers() {
        int[] test = new int[]{4,-2,5,8,5,-6,7,18,-3};

        int[] expected = new int[]{18,8,7,5,5,4};
        int[] actual = Numbers.getPositiveNumbers(test);

        assertArrayEquals(expected, actual);
    }
    @Test
    @Order(2)
    void onlyPositiveNumbers () {
        int[] test = new int[]{4,2,5,8,5,6,7,18,3};

        int[] expected = new int[]{18,8,7,6,5,5,4,3,2};
        int[] actual = Numbers.getPositiveNumbers(test);

        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void onlyNegativeNumbers () {
        int[] test = new int[]{-4,-2,-5,-8,-5,-6,-7,-18,-3};

        int[] expected = new int[0];
        int[] actual = Numbers.getPositiveNumbers(test);

        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void arrayIsEmpty () {
        int[] test = new int[0];

        int[] expected = new int[0];
        int[] actual = Numbers.getPositiveNumbers(test);

        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(5)
    void arrayIsNull () {
        int[] test = null;

        assertThrows(IllegalArgumentException.class, () -> Numbers.getPositiveNumbers(test));
    }
}