package task_1.helper.database;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;

@Getter
@Setter
class Violation {
    private String firstName;
    private String lastName;
    private String type;
    private Double fineAmount;
    private final String dateTime;

    public Violation(String firstName, String lastName, String type, Double fineAmount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.fineAmount = fineAmount;
        this.dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static List<Violation> create(int violatoinsRange) {
        var violations = new LinkedList<Violation>();
        var firstName = Arrays.asList("Ivan", "Taras", "Andrii", "Igor", "Semen");
        var lastName = Arrays.asList("Mon", "Tue", "Wen", "Sun", "Sat");
        var typeMap = Map.of("PARKING", 450.0,
                "SPEEDING", 550.0,
                "ALCOHOL", 10000.0,
                "RED_LIGHT", 500.0,
                "NO_LIGHT", 340.0);

        int random = new Random().nextInt(violatoinsRange);
        int randomFirstName = new Random().nextInt(firstName.size() - 1);
        int randomLastName = new Random().nextInt(lastName.size() - 1);
        int randomType = new Random().nextInt(typeMap.size() - 1);

        for(int i = 0; i < random; i++) {
            String type = new ArrayList<>(typeMap.keySet()).get(randomType);
            Double fineAmount = typeMap.get(type);

            Violation violation = new Violation(
                    firstName.get(randomFirstName),
                    lastName.get(randomLastName),
                    type,
                    fineAmount
            );

            violations.add(violation);

            randomFirstName = new Random().nextInt(firstName.size() - 1);
            randomLastName = new Random().nextInt(lastName.size() - 1);
            randomType = new Random().nextInt(typeMap.size() - 1);
        }
        return violations;
    }
}

