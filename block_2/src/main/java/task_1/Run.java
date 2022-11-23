package task_1;


import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

/**
1. Розробити програму, яка на вхід отримує xml-файл з тегами <person>, в яких є атрибути name і surname.
   Програма повинна створювати копію цього файлу, в якій значення атрибута surname об'єднане з name.
   <p>Наприклад name="Тарас" surname="Шевченко" у вхідному файлі повинно бути замінене на name="Тарас Шевченко" (атрибут surname має бути видалений).
   <p>Вхідний файл може бути великий, тому завантажувати його цілком в оперативну пам'ять буде поганою ідеєю.
 <p>* Опціонально (на макс. бал): зробити так, щоб форматування вихідного файла повторювало форматування вхідного файлу (мабуть, xml-парсер в такому разі тут не підійде).

<p>Приклад вхідного файлу:
 <pre>
  &#60persons&#62
  &#60person name="Іван" surname="Котляревський" birthDate="09.09.1769" /&#62
  &#60person surname="Шевченко" name="Тарас" birthDate="09.03.1814" /&#62
  &#60person
          birthData="27.08.1856"
                  name = "Іван"
                  surname = "Франко" /&#62
  &#60person name="Леся"
          surname="Українка"
          birthData="13.02.1871" /&#62
  &#60/persons&#62  </pre>

<p>Приклад вихідного файлу:
 <pre>
  &#60persons>
  &#60person name="Іван Котляревський" birthDate="09.09.1769"  /&#62
  &#60person name="Тарас Шевченко" birthDate="09.03.1814" /&#62
  &#60person
          birthData="27.08.1856"
                  name = "Іван Франко"
                  /&#62
  &#60person name="Леся Українка"

          birthData="13.02.1871" /&#62
  &#60/persons&#62
 </pre> */
public class Run {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        File fromFile = new File("block_2/src/main/resources/task_1/Persons.xml");
        File toFile = new File("block_2/src/main/resources/task_1/PersonsUpdate.xml");

        toFile.delete();

        try(Scanner scanner = new Scanner(fromFile);
            FileWriter fileWriter = new FileWriter(toFile, true)) {

            scanner.useDelimiter("\\/>");

            while(scanner.hasNext()) {
                String xmlObject = scanner.next();
                if(xmlObject.contains("<person")) {
                    String xml = new XML(xmlObject)
                            .joinFields("name", "surname", "name")
                            .getXml();

                    fileWriter.write( xml + "/>");
                    continue;
                }
                fileWriter.write(xmlObject);
            }
        }

        long elapsedTime = System.nanoTime() - startTime;

        System.out.println("Time: " + elapsedTime / 1000000);
    }
}
