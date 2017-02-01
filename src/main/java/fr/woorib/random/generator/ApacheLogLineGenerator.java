package fr.woorib.random.generator;

import fr.woorib.bean.ApacheLogLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 * Generates Log lines in the format of the common access logs from apache
 */
public class ApacheLogLineGenerator implements Generator {

    private ObjectGenerator<ApacheLogLine> generator;
    private String[] users = new String[]{"bob", "anne", "delta"};
    private String[] requests = new String[]{"GET /fav.ico", "GET /index.html", "GET /pages/gallery.php"};

    public ApacheLogLineGenerator() {
        Supplier statusCodesGenerator;
        try {
            File dictionaryFile = new File(getClass().getClassLoader().getResource("./http_status_codes").getFile());
            List<Integer> statusCodeList = getListOfStatusCodes(dictionaryFile);
            statusCodesGenerator = new DictionaryGenerator(statusCodeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            statusCodesGenerator = () -> new Random().nextInt(550);
        }

        ZonedDateTime today = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime tomorrow = today.plusDays(1);
        generator = new ObjectGenerator(ApacheLogLine.class)
                .with(ZonedDateTime.class, new ZonedDateTimeGenerator().moreThan(today).lessThan(tomorrow))
                .with("user", new DictionaryGenerator(Arrays.asList(users)))
                .with(String.class, new DictionaryGenerator(Arrays.asList(requests)))
                .with("statusCode", statusCodesGenerator)
                .with("dataSize", () -> new Random().nextInt(1_000_000));
    }

    private List<Integer> getListOfStatusCodes(File dictionaryFile) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        Scanner in = new Scanner(dictionaryFile);
        while (in.hasNextLine()) {
            result.add(Integer.parseInt(in.next()));
        }
        return result;
    }

    @Override
    public ApacheLogLine get() {
        return generator.get();
    }

}
