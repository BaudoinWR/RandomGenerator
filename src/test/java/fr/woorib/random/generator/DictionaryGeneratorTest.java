package fr.woorib.random.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by baudoin on 01/02/2017.
 */
public class DictionaryGeneratorTest {

    // Copyright for file: https://github.com/dominictarr/random-name
    DictionaryGenerator nameGenerator = new DictionaryGenerator(new File(getClass().getClassLoader().getResource("./first-names.txt").getFile()));

    public DictionaryGeneratorTest() throws FileNotFoundException {
    }

    @Test
    public void testGenerator() {
        Assertions.assertNotEquals(nameGenerator.generate(), "");
    }

    @Test
    public void testGeneratorAfter() {
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);
        Assertions.assertTrue(nameGenerator.moreThan("Margaretha").generate().compareTo("Margaretha") > 0);

        Assertions.assertEquals("", nameGenerator.moreThan("z").generate());

    }

    @Test
    public void testGeneratorBefore() {
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);
        Assertions.assertTrue(nameGenerator.lessThan("Margaretha").generate().compareTo("Margaretha") < 0);

        Assertions.assertEquals("", nameGenerator.lessThan("A").generate());

    }
}
