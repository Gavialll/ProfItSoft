package task_2;

import java.nio.file.Path;

public class Run {

    /**
     * 2. Розробити класс-утиліту зі статичним методом, який приймає параметр типу Class
     * і шлях до properties-файлу,
     * створює об'єкт цього класу, наповнює його атрибути значеннями з properties-файлу (викоистовуючи сеттери)
     * і повертає цей об'єкт.
     *
     * Приклад сигнатури метода:
     *   public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath)
     *
     * Properties-файл має формат:
     * stringProperty=value1
     * numberProperty=10
     * timeProperty=29.11.2022 18:30
     *
     * Метод має вміти парсити строки, цілі числа (int та Integer) і дати з часом (Instant).
     * Створити аннотацію @Property, за допомогою якої можна було б опціонально перезадати назву
     * property (акщо назва атрибуту класу відрізняється від ключу property в файлі),
     * а для полів типу дата/час задати - очікуваний формат дати.
     * Наприклад, клас, який ми читаємо з файлу вище, міг бі мати такі трибути:
     *
     *   private String stringProperty;
     *
     *   @Property(name="numberProperty")
     *   private int myNumber
     *
     *   @Property(format="dd.MM.yyyy tt:mm")
     *   private Instant timeProperty;
     *
     * Складені ключі (prefix.propertyKey) в цьому завданні можуть використовуватися тільки якщо
     * ми задаємо їх в аннотації @Property(name="prefix.propertyKey").
     * Якщо щось розпарсии/заповнити не вдалося (не підтримується тип, неправильний формат, тощо),
     * метод повинен кидати відповідний Exception.
     * Створити unit-тести для цього метода.
     */
    public static void main(String[] args) {
        Path path = Path.of("block_3/src/main/resources/task_2/fields.properties");

        LoadFromProperties instance = CreateFactory.loadFromProperty(LoadFromProperties.class, path);

        System.out.println(instance);
    }
}
