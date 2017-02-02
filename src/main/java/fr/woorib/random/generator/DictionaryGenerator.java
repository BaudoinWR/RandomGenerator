package fr.woorib.random.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by baudoin on 01/02/2017.
 * Generation of String based on a dictionary.
 * The generator chooses a random line in the dictionary file and returns it.
 */
public class DictionaryGenerator<T extends Comparable> implements Generator<T> {

    private List<T> dictionary = new ArrayList();
    Random random = new Random();

    public DictionaryGenerator(File dictionaryFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(dictionaryFile)){
            while (in.hasNextLine()) {
                this.dictionary.add((T) in.nextLine());
            }
        }
    }

    public DictionaryGenerator(List<T> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public T get() {
        if (dictionary.size() == 0) {
            return null;
        }
        int i = random.nextInt(dictionary.size());
        return dictionary.get(i);
    }

    @Override
    public Generator<T> lessThan(T before) {
        List<T> filtered = this.dictionary.stream()
                .filter(s ->  s.compareTo(before) < 0)
                .collect(Collectors.toList());

        return new DictionaryGenerator<>(filtered);
    }

    @Override
    public Generator<T> moreThan(T after) {
        List<T> filtered = this.dictionary.stream()
                .filter(s ->  s.compareTo(after) > 0)
                .collect(Collectors.toList());

        return new DictionaryGenerator<>(filtered);
    }
}
