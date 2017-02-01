package fr.woorib.random.generator;

import org.junit.jupiter.api.Test;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ApacheLogLineGeneratorTest {
    private ApacheLogLineGenerator generator = new ApacheLogLineGenerator();

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
}
