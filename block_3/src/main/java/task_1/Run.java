package task_1;

import static task_1.helper.Path.RESULT_PATH;
import static task_1.helper.Path.DIRECTORY_PATH;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import task_1.helper.database.FileService;
import task_1.helper.Timer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 1. Модифікувати завдання з попереднього блоку (у папці є перелік текстових файлів,
 * кожен із яких є "зліпок" БД порушень правил дорожнього руху протягом певного року...) таким чином,
 * щоб різні файли з папки завантажувалися асинхронно за допомогою пулу потоків,
 * але загальна статистика однаково формувалася.
 * Використовувати CompletableFuture і ExecutorService.
 * Порівняти швидкодію програми, коли не використовується параллелизація,
 * коли використовується 2 потоки, 4 і 8.
 * Файлів в папці повинно бути 10+, їх розмір повинен бути достатнім, щоб заміри були цікавими.
 * Результати порівняння прикласти коментарем до викононаго завдання.
 */
public class Run {
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        FileService fileService = new FileService(executorService);

        //Delete old files for create new files
        fileService.clearDirectory(DIRECTORY_PATH);

        //Create new files with violations
        fileService.createFiles(50, 1_000_000, DIRECTORY_PATH);

        Timer timer = Timer.start();

        File[] files = new File(DIRECTORY_PATH.getPath()).listFiles();

        var futures = createTasks(files, executorService);

        var listViolations = CompletableFuture
                .allOf(futures)
                .thenApply(v -> Arrays.stream(futures)
                            .map(CompletableFuture::join)
                            .flatMap(List::stream)
                            .collect(Collectors.toList()))
                .thenApply(Run::margeViolations)
                .thenApply(s -> s.stream()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList()))
                .join();

        new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .writeValue(new File(RESULT_PATH.getPath()), listViolations);

        executorService.shutdown();

        System.out.println(timer.stop());
    }

    @SuppressWarnings("All")
    private static CompletableFuture<List<ViolationTotal>>[] createTasks(File[] files, ExecutorService executorService){
        return Arrays.stream(files)
                .map(file -> CompletableFuture.supplyAsync(() -> countViolationByType(file), executorService))
                .toArray(CompletableFuture[]::new);
    }

    private static List<ViolationTotal> margeViolations(List<ViolationTotal> violationTotals){
        List<String> types = violationTotals
                .stream()
                .distinct()
                .map(ViolationTotal::getType)
                .collect(Collectors.toList());

        List<ViolationTotal> allStatistics = new ArrayList<>();

        for(String type : types) {
            BigDecimal total = violationTotals.stream()
                    .map(v -> (v.getType().equals(type)) ? v.getTotal() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            allStatistics.add(new ViolationTotal().setType(type).setTotal(total));
        }
        return allStatistics;
    }

    private static List<ViolationTotal> countViolationByType(File file) {
        List<ViolationTotal> violationTotals = new ArrayList<>();
        if(file.isFile() && file.getName().endsWith(".json")) {
            JsonFactory jFactory = new JsonFactory();
            try(JsonParser jParser = jFactory.createParser(file)) {
                while(jParser.nextToken() != JsonToken.END_ARRAY) {
                    ViolationTotal violationTotal = new ViolationTotal();
                    while(jParser.nextToken() != JsonToken.END_OBJECT) {
                        String fieldName = jParser.getCurrentName();
                        if("fineAmount".equals(fieldName)) {
                            jParser.nextToken();
                            violationTotal.setTotal(jParser.getDecimalValue());
                        }
                        if("type".equals(fieldName)) {
                            jParser.nextToken();
                            violationTotal.setType(jParser.getText());
                        }
                    }
                    ViolationTotal oldViolationTotal = violationTotals
                            .stream().filter(v -> v.getType().equals(violationTotal.getType()))
                            .findFirst()
                            .orElse(null);

                    if(oldViolationTotal == null) {
                        violationTotals.add(violationTotal);
                    } else {
                        BigDecimal newTotal = oldViolationTotal.getTotal().add(violationTotal.getTotal());
                        oldViolationTotal.setTotal(newTotal);
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return violationTotals;
    }
}
