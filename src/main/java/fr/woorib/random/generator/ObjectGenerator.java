package fr.woorib.random.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ObjectGenerator<T> implements Generator {

    private final Class<T> classz;
    private Map<String, Supplier> generators = new HashMap();

    Logger logger = LoggerFactory.getLogger(ObjectGenerator.class);
    GeneratorProvider provider = new GeneratorProvider();

    public ObjectGenerator(Class classz) {
        this.classz = classz;
    }

    @Override
    public T get() {
        T t = null;
        try {
            t = classz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Field[] fields = classz.getDeclaredFields();
        for (Field field : fields) {
            Supplier generator = generators.get(field.getName());
            if (generator == null) {
                generator = provider.get(field.getType());
            }
            if (generator != null) {
                try {
                    String setter = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    classz.getMethod(setter, field.getType()).invoke(t, generator.get());
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    logger.warn("No setter for field {} of class {}", field.getName(), classz);
                }
            }
        }
        return t;
    }

    /**
     * Specifies generic generator to use for all fields of class type
     * @param type
     * @param generator
     * @return
     */
    public ObjectGenerator<T> with(Class type, Supplier generator) {
        provider = provider.with(type, generator);
        return this;
    }

    /**
     * Specifies specific generator to use for field
     * @param fieldName
     * @param generator
     * @return
     */
    public ObjectGenerator<T> with(String fieldName, Supplier generator) {
        generators.put(fieldName, generator);
        return this;
    }
}
