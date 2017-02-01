package fr.woorib.random.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by baudoin on 01/02/2017.
 * Interface for generators
 */
public interface Generator<T> extends Supplier<T> {
    Map<Generator, Thread> runningThreads = new HashMap<>();
    /**
     * Genrate a random object of type T
     * @return an object of type T
     */
    T get();

    /**
     * sets the generator to only return objects considered to be less than the before parameter
     * @param before
     * @return a newly configured Generator
     */
    default Generator<T> lessThan(T before) { return this; }

    /**
     * sets the generator to only return objects considered to be more than the before parameter
     * @param after
     * @return a newly configured Generator
     */
    default Generator<T> moreThan(T after) { return this; }

    /**
     * Start a thread generating T objects at a random interval under maxInterval.
     * The generated objects are passed on to the consumer.
     * @param consumer
     * @param maxInterval
     */
    default void startGenerating(Consumer<T> consumer, int maxInterval) {
        if (runningThreads.containsKey(this)) {
            throw new RuntimeException("Generation already underway");
        }
        Thread thread = new Thread(
                () -> {
                    while (true) {
                        consumer.accept(get());
                        try {
                            Thread.sleep(new Random().nextInt(maxInterval));
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                });
        thread.start();
        runningThreads.put(this, thread);
    }

    /**
     * Stops the generation of T objects by this generator.
     */
    default void stopGenerating() {
        Thread thread = runningThreads.get(this);
        if (thread != null) {
            thread.interrupt();
            runningThreads.remove(this);
        }
    }

    /**
     * Get a stream of amount objects of class T
     * @param amount
     * @return
     */
    default Stream<T> get(int amount) {
        return Stream.generate(this).limit(amount);
    }
}
