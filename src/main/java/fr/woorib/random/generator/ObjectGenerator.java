package fr.woorib.random.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ObjectGenerator {

    Logger logger = LoggerFactory.getLogger(ObjectGenerator.class);
    GeneratorProvider provider = new GeneratorProvider();

    public <T> T generate(Class<T> classz) throws IllegalAccessException, InstantiationException {
        T t = classz.newInstance();
        Field[] fields = classz.getDeclaredFields();
        for (Field field : fields) {
            Generator generator = provider.get(field.getType());
            if (generator != null) {
                try {
                    String setter = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                    classz.getMethod(setter, field.getType()).invoke(t, generator.generate());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    logger.warn("No setter for field {} of class {}", field.getName(), classz);
                }
            }
        }
        return t;
    }

    public ObjectGenerator with(Class type, Generator generator) {
        provider = provider.with(type, generator);
        return this;
    }
}
