package fr.woorib.random.generator;

import fr.woorib.bean.ApacheLogLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ApacheLogLineGeneratorTest {
    private ApacheLogLineGenerator generator = new ApacheLogLineGenerator();
    private static final String[] users = new String[]{"bob", "anne", "delta"};
    private static final String[] requests = new String[]{"GET /fav.ico", "GET /index.html", "GET /pages/gallery.php"};

    @BeforeEach
    public void init() {
        Supplier statusCodesGenerator;
        try {
            File dictionaryFile = new File(getClass().getClassLoader().getResource("./http_status_codes").getFile());
            List<Integer> statusCodeList = getListOfStatusCodes(dictionaryFile);
            statusCodesGenerator = new DictionaryGenerator<>(statusCodeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            statusCodesGenerator = () -> new Random().nextInt(550);
        }
        generator.withTimestampGenerator(ZonedDateTime::now)
                .withUserGenerator(new DictionaryGenerator<>(Arrays.asList(users)))
                .withRequestGenerator(new DictionaryGenerator<>(Arrays.asList(requests)))
                .withStatusCodeGenerator(statusCodesGenerator)
                .withDataSizeGenerator(() -> new Random().nextInt(1_000_000));

    }

    private List<Integer> getListOfStatusCodes(File dictionaryFile) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        Scanner in = new Scanner(dictionaryFile);
        while (in.hasNextLine()) {
            result.add(Integer.parseInt(in.next()));
        }
        return result;
    }

    @Test
    public void testGenerator() {
        System.out.println(generator.get());
    }

    @Test
    public void testStreamGenerator() throws InterruptedException {
        generator.startGenerating(System.out::println, 2000);
        Thread.sleep(1_000);
        generator.stopGenerating();
    }

    @Test
    public void testStreamGeneratorAlreadyRunning() {
        ApacheLogLineGenerator apacheLogLineGenerator = new ApacheLogLineGenerator();
        apacheLogLineGenerator.startGenerating(System.err::println, 500);
        Assertions.assertThrows(RuntimeException.class,() -> apacheLogLineGenerator.startGenerating(System.out::println, 5000));
    }

    @Test
    public void testGenerate100LogMessages() {
        List<ApacheLogLine> list = (List<ApacheLogLine>) generator.get(100).collect(Collectors.toList());
        System.out.println(list.get(0));
        Assertions.assertEquals(100, list.size());
    }
}
