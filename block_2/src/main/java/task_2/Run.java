package task_2;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import task_1.Action;

import static task_1.Action.VALUE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>2.  У папці є перелік текстових файлів, кожен із яких є "зліпок" БД порушень правил дорожнього руху
 * протягом певного року.
 * Кожен файл містить список json (або xml - на вибір) об'єктів - порушень приблизно такого виду:
 * <pre>
 * {
 * "date_time: "2020-05-05 15:39:03", // час порушеня
 * "first_name": "Ivan",
 * "last_name": "Ivanov"
 * "type": "SPEEDING", // тип порушення
 * "fine_amount": 340.00 // сума штрафу
 * }</pre>
 * <p>Прочитати дані з усіх файлів, розрахувати та вивести статистику порушень у файл.
 * В вихідному файлі повинні міститися загальні суми штрафів за кожним типом порушень за всі роки,
 * відсортовані за сумою (спочатку за найбільшою сумою штрафу).
 * Якщо вхідний файл був json, то вихідний повиннен бути xml. Якщо вхідний xml, то вихідний - json.
 * Щоб ви мали досвід роботи з обома форматами.
 * <p>* Опціонально (на макс. бал): зробити так, щоб вхідні файли не завантажувалися цілком в пам'ять.
 */
public class Run {

    public static void main(String[] args) throws IOException {

        File[] fileList = new File("block_2/src/main/resources/task_2").listFiles();

        List<Violation> violations = countViolationByType(fileList).entrySet().stream().map(Violation::newInstance).sorted().collect(Collectors.toList());

        File outputFileXML = new File("block_2/src/main/resources/task_2/result/ViolationTotal.xml");
        File outputFileJSON = new File("block_2/src/main/resources/task_2/result/ViolationTotal.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
                    .writeValue(outputFileJSON, violations);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT)
                 .writeValue(outputFileXML, violations);
    }


    /**
     * <p> Read list of "JSON" files and count violation by type.
     * <p>
     *
     * @param files List of file for reading
     * @return Map&#60String, Double&#62, key = type of violation, value = sum violations
     * @throws FileNotFoundException
     */
    public static Map<String, Double> countViolationByType(File[] files) throws FileNotFoundException {
        Map<String, Double> map = new HashMap<>();

        for(File file : files) {
            if(file.isFile()) {
                try(Scanner scanner = new Scanner(file)) {
                    scanner.useDelimiter("\\}\\s*\\,*");
                    while(scanner.hasNext()) {
                        String json = scanner.next();
                        if(json.contains("{")) {
                            String key = getField(json, "type", VALUE);
                            String value = getField(json, "fine_amount", VALUE);

                            if(map.containsKey(key)) {
                                double oldValue = map.get(key);
                                double newValue = Double.parseDouble(value) + oldValue;
                                map.replace(key, newValue);
                            } else {
                                map.put(key, Double.parseDouble(value));
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * <p>Method take one attribute from "JSON"
     *
     * <p>Method can return part of field or all field,
     * You can choose action for choose part of field.
     * <pre>
     *  {@link task_1.Action}.ALL_ROW return String = "<i>name : "value"</i>
     *  {@link task_1.Action}.KEY     return String = "<i>name</i>"
     *  {@link task_1.Action}.VALUE   return String = "<i>value</i>"
     * </pre>
     *
     * @param json      One JSON object
     * @param fieldName Attribute name
     * @param action    Enum for choose action
     * @return String
     */
    public static String getField(String json, String fieldName, Action action) {
        Pattern pattern = Pattern.compile("\\s*\\\"(" + fieldName + ")\\\"\\s*:\\s*\\\"*([А-я|A-z|іІїЇ.,|0-9]*)\\\"*");

        Matcher matcher = pattern.matcher(json);
        if(matcher.find()) {
            return matcher.group(action.getValue()).trim().replaceAll("\"", "");
        }
        throw new IllegalArgumentException("Field with name = \"" + fieldName + "\" not found");
    }
}
