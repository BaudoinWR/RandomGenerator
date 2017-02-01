package fr.woorib.random.generator;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 */
public class GeneratorProvider {
    private Map<Class, Supplier> generators = new HashMap();
    Random random = new Random();
    public GeneratorProvider() {
        generators.put(String.class, new DictionaryGenerator(Collections.emptyList()));
        generators.put(ZonedDateTime.class, new ZonedDateTimeGenerator());
        generators.put(InetAddress.class, new IpGenerator());
        generators.put(Integer.class, random::nextInt);
        generators.put(Double.class, random::nextDouble);
        generators.put(Long.class, random::nextLong);
        generators.put(Boolean.class, random::nextBoolean);
        generators.put(Float.class, random::nextFloat);
    }

    public Supplier get(Class<?> type) {
        return generators.get(type);
    }

    public GeneratorProvider with(Class type, Supplier generator) {
        generators.put(type, generator);
        return this;
    }
}
