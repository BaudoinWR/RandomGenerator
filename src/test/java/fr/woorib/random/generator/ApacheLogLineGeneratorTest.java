package fr.woorib.random.generator;

import org.junit.jupiter.api.Test;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ApacheLogLineGeneratorTest {
    private ApacheLogLineGenerator generator = new ApacheLogLineGenerator(false);

    @Test
    public void testGenerator() {
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
    }

    @Test
    public void testStreamGenerator() throws InterruptedException {
        generator = new ApacheLogLineGenerator(true);
        Thread thread = generator.startGenerating(System.out::println, 2000);
        Thread.sleep(120_000);
        thread.interrupt();
    }
}
