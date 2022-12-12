package task_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.text.ParseException;
import java.time.Instant;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CreateFactoryTest {
    private static User expectedUser;
    private static UserAnnotation expectedUserAnnotation;
    private static Path goodProperties;
    private static Path badProperties;
    private static Path badDateProperties;

    @BeforeAll
    static void before() throws ParseException {
        expectedUser = new User("newFromProperties", 10, Instant.parse("2022-11-29T18:30:00Z"));
        expectedUserAnnotation = new UserAnnotation("newFromProperties", 10, Instant.parse("2022-11-29T00:00:00Z"));
        goodProperties = Path.of("src/test/resources/Good.properties");
        badProperties = Path.of("src/test/resources/Bad.properties");
        badDateProperties = Path.of("src/test/resources/BadDate.properties");
    }

    @Test
    void completeUserAnnotationWithAnnotation() {
        UserAnnotation actual = CreateFactory.loadFromProperty(UserAnnotation.class, badProperties);
        assertEquals(expectedUserAnnotation, actual);
    }

    @Test
    void completeUserWithoutAnnotation() {
        User actual = CreateFactory.loadFromProperty(User.class, goodProperties);
        assertEquals(expectedUser, actual);
    }

    @Test
    void throwWhenDateFormatIsBad() {
        String actual = assertThrows(IllegalArgumentException.class,
                () -> CreateFactory.loadFromProperty(User.class, badDateProperties)
        ).getMessage();

        assertEquals("Date is not correct: 29dsa.das11.20fsdad22 18sad:30", actual);
    }

    @Test
    void throwFieldNotFound() {
        Pattern pattern = Pattern.compile("^(Field with name \")[A-z]*(\" in class not found )$");

        String actual = assertThrows(IllegalArgumentException.class,
                () -> CreateFactory.loadFromProperty(User.class, badProperties)
        ).getMessage();

        assertTrue(pattern.matcher(actual).matches());
    }

    @Test
    void throwClassNotHaveEmptyConstructor() {
        class ThrowException{ public ThrowException(String string) {}}

        String actual = assertThrows(IllegalAccessException.class,
                () -> CreateFactory.loadFromProperty(ThrowException.class, goodProperties)
        ).getMessage();
        assertEquals("Class doesn't have empty constructor", actual);
    }

    @Data
    @ToString
    static class User {
        private String name;
        private Integer age;
        private Instant time;

        public User(String name, Integer age, Instant time) {
            this.name = name;
            this.age = age;
            this.time = time;
        }

        public User() {
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @ToString
    static class UserAnnotation {
        @Property(name = "firstName")
        private String name;
        @Property(name = "size")
        private Integer age;
        @Property(name = "timeProperty", format = "dd.MM.yyyy")
        private Instant time;
    }
}