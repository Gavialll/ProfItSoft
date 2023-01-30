package task_2;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

        List<Violation> violations = new LinkedList<>();
        for(File file : fileList) {
            if(file.isFile()) {
                JsonFactory jFactory = new JsonFactory();
                try(JsonParser jParser = jFactory.createParser(file)) {
                    while(jParser.nextToken() != JsonToken.END_ARRAY) {
                        Violation violation = new Violation();
                        while(jParser.nextToken()!= JsonToken.END_OBJECT) {
                            String fieldName = jParser.getCurrentName();
                            if("fine_amount".equals(fieldName)) {
                                jParser.nextToken();
                                violation.setTotal(jParser.getDoubleValue());
                            }
                            if("type".equals(fieldName)) {
                                jParser.nextToken();
                                violation.setType(jParser.getText());
                            }
                        }
                        Violation oldViolation = violations
                                .stream()
                                .filter(v -> v.getType().equals(violation.getType()))
                                .findFirst()
                                .orElse(null);

                        if(oldViolation == null) {
                            violations.add(violation);
                        } else {
                            double newTotal = oldViolation.getTotal() + violation.getTotal();
                            oldViolation.setTotal(newTotal);
                        }
                    }
                }
            }
        }

        File outputFileXML = new File("block_2/src/main/resources/task_2/result/ViolationTotal.xml");
        File outputFileJSON = new File("block_2/src/main/resources/task_2/result/ViolationTotal.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValue(outputFileJSON, violations);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT).writeValue(outputFileXML, violations);
    }
}
