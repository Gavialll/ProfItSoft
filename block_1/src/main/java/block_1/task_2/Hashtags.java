package block_1.task_2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

/** 2. Написати метод, який на вхід приймає список рядків-текстів,
    у яких можуть бути хеш-теги (слова, що починаються на знак "#").
    Як результат, метод повинен повертати top-5 найчастіше згаданих хеш-тегів
    із зазначенням кількості згадки шкірного з них. Декілька однакових хеш-тегів
    в одній строчці повинні вважатися однією згадкою. Написати unit-тести для цього методу. */
public class Hashtags {
    /**
     * @Result: Method return top 5 hashtags or empty "Map<String, Integer>" if hashtags not found.
     */
    public static Map<String, Integer> getTopFiveHashtags(List<String> textList){
        if(Objects.isNull(textList)) return new LinkedHashMap<>();

        Map<String, Integer> hashtagMap = new HashMap<>();

        textList.stream()
                .map(Hashtags::findHashtags)
                .flatMap(Set::stream)
                .forEach(hashtag -> {
                    if(hashtagMap.containsKey(hashtag))
                        hashtagMap.replace(hashtag, hashtagMap.get(hashtag) + 1);
                    else
                        hashtagMap.put(hashtag, 1);
                });

        return hashtagMap.entrySet()
                         .stream()
                         .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                         .limit(5)
                         .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * @Result: Return only unique hashtags.
     */
    private static HashSet<String> findHashtags(String str){
        if(Objects.isNull(str)) return new HashSet<>();

        HashSet<String> hashtags = new HashSet<>();

        Pattern pattern = Pattern.compile("#.+?\\b");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            hashtags.add(matcher.group());
        }
        return hashtags;
    }
}