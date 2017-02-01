package fr.woorib.random.generator;

import fr.woorib.bean.ApacheLogLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ApacheLogLineGeneratorTest {
    private ApacheLogLineGenerator generator = new ApacheLogLineGenerator(false);

    @Test
    public void testGenerator() {
        System.out.println(generator.get());
    }

    @Test
    public void testStreamGenerator() throws InterruptedException {
        generator = new ApacheLogLineGenerator(true);
        generator.startGenerating(System.out::println, 2000);
        Thread.sleep(1_000);
        generator.stopGenerating();
    }

    @Test
    public void testStreamGeneratorAlreadyRunning() {
        generator.startGenerating(System.err::println, 500);
        Assertions.assertThrows(RuntimeException.class,() -> generator.startGenerating(System.out::println, 5000));
    }

    @Test
    public void testGenerate100LogMessages() {
        List<ApacheLogLine> list = (List<ApacheLogLine>) generator.get(100).collect(Collectors.toList());
        System.out.println(list.get(0));
        Assertions.assertEquals(100, list.size());
    }
}
