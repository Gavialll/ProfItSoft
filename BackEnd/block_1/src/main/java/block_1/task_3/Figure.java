package block_1.task_3;

import java.util.LinkedList;
import java.util.List;

/** 3. Реалізувати метод, який сортує геометричні 3d фігури за обсягом.
    Метод приймає параметром колекцію довільних геометричних фігур (куб, кулю, циліндр).
    Написати unit-тести для методу. */
public abstract class Figure implements Comparable<Figure> {
    public abstract double getVolume();

    /**
     * @Result:  Sorting figure by volume.
     */
    public static List<Figure> sortByVolume(List<Figure> figureList){
        if(figureList == null || figureList.isEmpty())
            return new LinkedList<>();

        figureList.sort(Figure::compareTo);
        return figureList;
    }

    /**
     * @Result: Throw exception if number less than zero.
     */
    protected void isLessZero(String propertyName, double number){
        if(number < 0) throw new IllegalArgumentException(propertyName + " can not be less than zero");
    }

    @Override
    public int compareTo(Figure o) {
        return Double.compare(this.getVolume(), o.getVolume());
    }
}
