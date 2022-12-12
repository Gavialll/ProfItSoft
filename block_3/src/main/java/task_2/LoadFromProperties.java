package task_2;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@ToString
class LoadFromProperties {
    private String stringProperty;
    @Property(name = "numberProperty")
    private int myNumber;
    @Property(format = "dd.MM.yyyy HH:mm:ss")
    private Instant timeProperty;

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }
}

