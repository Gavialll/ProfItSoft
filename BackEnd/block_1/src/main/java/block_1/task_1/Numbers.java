package block_1.task_1;

import java.util.Arrays;

/**  1. Зробити метод, який приймає на вхід масив цілих чисел,
        і повертає тільки ті з них, які є позитивними
        (>=0), відсортувавши їх за спаданням.
        Зробити unit-тести для цього методу. */
public class Numbers {

    /**
     * @Result: Returns an array of positive numbers and sorts them in reverse order.
     */
    public static int[] getPositiveNumbers(int[] numbers){
        if(numbers == null) throw new IllegalArgumentException("Input array is null");

        numbers = Arrays.stream(numbers).filter(number -> number >= 0).toArray();

        return choseSort(numbers);
    }

    /**
     * @Result: Sorting by choice. Sorts the array in reverse order.
     */
    private static int[] choseSort(int[] array){
        for(int i = array.length-2; i >= 0; i--) {
            if(array[i] < array[i+1]) {
                for(int j = i; j < array.length; j++) {
                    if(j+1 == array.length || array[j] > array[j+1]) break;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
}
