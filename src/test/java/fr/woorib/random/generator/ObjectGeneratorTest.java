package fr.woorib.random.generator;

import fr.woorib.bean.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ObjectGeneratorTest {
    ObjectGenerator<User> generator = new ObjectGenerator(User.class);
    private static final ZonedDateTime before = ZonedDateTime.of(1986, 9, 5, 0, 0, 0, 0, ZoneId.systemDefault());
    private static final ZonedDateTime after = ZonedDateTime.of(1980, 6, 2, 0, 0, 0, 0, ZoneId.systemDefault());
    @Test
    public void testGenerateLogLine() throws InstantiationException, IllegalAccessException, FileNotFoundException {
        DictionaryGenerator nameGenerator = new DictionaryGenerator(new File(getClass().getClassLoader().getResource("./first-names.txt").getFile()));
        ZonedDateTimeGenerator timeGenerator = new ZonedDateTimeGenerator().lessThan(before).moreThan(after);
        User generate = generator.with(String.class, nameGenerator::get).with(ZonedDateTime.class, timeGenerator::get).get();
        System.out.println(generate);
    }



}
