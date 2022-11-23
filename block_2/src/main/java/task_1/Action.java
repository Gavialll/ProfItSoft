package task_1;

public enum Action {
    KEY(1), VALUE(2), ALL_ROW(0) ;

    private int value;

    Action(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
