package task_1.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Path {
    DIRECTORY_PATH("block_3/src/main/resources/task_1/MookDataBase"),
    RESULT_PATH("block_3/src/main/resources/task_1/ViolationTotal.json");

    private String path;
}
