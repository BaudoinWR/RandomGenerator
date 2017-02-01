package fr.woorib.random.generator;

import java.time.ZonedDateTime;

/**
 * Created by baudoin on 01/02/2017.
 * Interface for generators
 */
public interface Generator<T> {
    /**
     * Genrate a random object of type T
     * @return an object of type T
     */
    T generate();

    /**
     * sets the generator to only return objects considered to be less than the before parameter
     * @param before
     * @return a newly configured Generator
     */
    Generator<T> lessThan(T before);

    /**
     * sets the generator to only return objects considered to be more than the before parameter
     * @param after
     * @return a newly configured Generator
     */
    Generator<T> moreThan(T after);
}
