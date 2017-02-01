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
 * The generator choses a random line in the dictionary file and returns it.
 */
public class DictionaryGenerator implements Generator<Comparable> {

    private List<Comparable> dictionary = new ArrayList();
    Random random = new Random();

    public DictionaryGenerator(File dictionaryFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(dictionaryFile)){
            while (in.hasNextLine()) {
                this.dictionary.add(in.nextLine());
            }
        }
    }

    public DictionaryGenerator(List<Comparable> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Comparable generate() {
        if (dictionary.size() == 0) {
            return "";
        }
        int i = random.nextInt(dictionary.size() - 1);
        return dictionary.get(i);
    }

    @Override
    public Generator<Comparable> lessThan(Comparable before) {
        List<Comparable> filtered = this.dictionary.stream()
                .filter(s ->  s.compareTo(before) < 0)
                .collect(Collectors.toList());

        return new DictionaryGenerator(filtered);
    }

    @Override
    public Generator<Comparable> moreThan(Comparable after) {
        List<Comparable> filtered = this.dictionary.stream()
                .filter(s ->  s.compareTo(after) > 0)
                .collect(Collectors.toList());

        return new DictionaryGenerator(filtered);
    }
}
