package block_1.task_2;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HashtagsTest {

    @Test
    @Order(1)
    void topFiveHashtags() {
        List<String> testList = getText();

        Map<String, Integer> expected = Map.of(
                "#Lorem", 6,
                "#ipsum",4,
                "#dolor", 5,
                "#consectetur", 3,
                "#amet",2);
        Map<String, Integer> actual = Hashtags.getTopFiveHashtags(testList);

        assertEquals(expected,actual);
    }

    @Test
    @Order(2)
    void inputIsEmpty() {
        Map<String, Integer> expected = Map.of();
        Map<String, Integer> actual = Hashtags.getTopFiveHashtags(new LinkedList<>());

        assertEquals(expected,actual);
    }

    @Test
    @Order(3)
    void inputIsNull() {
        Map<String, Integer> expected = Map.of();
        Map<String, Integer> actual = Hashtags.getTopFiveHashtags(null);

        assertEquals(expected,actual);
    }

    private List<String> getText(){
        return Arrays.asList(
            "#Lorem#ipsum #dolor sit #amet, #amet #consectetur#dolor #adipisicing elit. Reprehenderit quisquam facilis ea id repellendus #harum quo #maxime nesciunt hic commodi.",
            "#Lorem #ipsum #dolor sit #amet, #consectetur adipisicing elit. Voluptate et quam, doloribus impedit a vel excepturi consequuntur ducimus temporibus alias amet architecto officia cum? Repellat iusto esse autem temporibus tenetur incidunt culpa, eaque exercitationem iure, odio vero vitae. Impedit dolores excepturi deserunt quos laudantium distinctio iusto expedita non quasi #doloribus.",
            "#Lorem#ipsum #dolor sit, amet #consectetur#dolor adipisicing elit. Laudantium, alias.",
            "#Lorem ipsum #dolor sit amet, consectetur adipisicing elit. #Quibusdam iusto ad quis error totam aliquam omnis quam. Odit dolorum #dolor totam, fugit nam laudantium, veniam perferendis consequatur dolore velit fugiat laboriosam provident praesentium, commodi deserunt sed harum? Expedita sunt debitis excepturi.",
            "#Lorem#dolor#ipsum",
            "#Lorem ",
            "#",
            "",
            null
        );
    }
}