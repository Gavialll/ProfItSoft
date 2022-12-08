package task_1.helper.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.junit.platform.commons.function.Try;
import task_1.helper.Path;
import task_1.helper.Timer;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FileService {
    private ExecutorService executor;

    public List<File> createFiles(int numberOfFiles, int rangeOfViolations, Path path) throws InterruptedException {
        List<Callable<File>> tasks = createTasks(numberOfFiles, rangeOfViolations, path.getPath());

        return executor.invokeAll(tasks)
                .stream()
                .map(s -> Try.call(s::get))
                .flatMap(e -> e.toOptional().stream())
                .collect(Collectors.toList());
    }

    public void clearDirectory(Path path){
        Arrays.stream(Objects.requireNonNull(new File(path.getPath()).listFiles())).forEach(File::delete);
    }

    private static List<Callable<File>> createTasks(int numberOfFiles, int rangeOfViolations, String directoryPath){
        List<Callable<File>> callables = new LinkedList<>();

        for(int i = 0; i < numberOfFiles; i++) {
            int finalI = i;
            callables.add(() -> {
                var timer = Timer.start();

                var file = new File(directoryPath + "/pdr_" + finalI + ".json");

                var violations = Violation.create(rangeOfViolations);

                new ObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .writeValue(file, violations);

                System.out.printf("Thread: %d\nTime: %s\nViolation size: %d\n\n",
                        finalI,
                        timer.stop(),
                        violations.size());
                return file;
            });
        }
        return callables;
    }
}
