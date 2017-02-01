package fr.woorib.random.generator;

import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 * Interface for generators
 */
public interface Generator<T> extends Supplier<T> {
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
}
