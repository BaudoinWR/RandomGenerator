package fr.woorib.random.main;

import fr.woorib.bean.ApacheLogLine;
import fr.woorib.random.generator.ApacheLogLineGenerator;
import fr.woorib.random.generator.DictionaryGenerator;
import fr.woorib.random.generator.ZonedDateTimeGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Created by baudoin on 02/02/2017.
 */
public class MainLogFileGenerator {
    private static final Boolean generateLive = true;

    private static ApacheLogLineGenerator apacheLogLineGenerator;
    public static void main(String[] args) throws IOException {
        try (FileWriter writer = new FileWriter("D:\\x159139\\access.log", true)) {
            apacheLogLineGenerator = new ApacheLogLineGenerator()
                    .withTimestampGenerator(generateLive ? ZonedDateTime::now : new ZonedDateTimeGenerator().onDay(2017, 1, 5))
                    .withStatusCodeGenerator(new DictionaryGenerator<Integer>(Arrays.asList(200, 200, 200, 200, 404, 500)))
                    .withUserAgentGenerator(new DictionaryGenerator<String>(getFile("./user_agents")))
                    .withRefererGenerator(new DictionaryGenerator<String>(getFile("./referer")));

            if (generateLive) {
                apacheLogLineGenerator.startGenerating(outputToLogfile(writer),2000);
                while (true) {
                    try {
                        writer.flush();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } else {
                apacheLogLineGenerator.get(5000)
                        .sorted(Comparator.comparing(ApacheLogLine::getTime))
                        .forEach(outputToLogfile(writer));
            }
        }
    }

    private static Consumer<ApacheLogLine> outputToLogfile(FileWriter writer) {
        return line -> {
            try {
                System.out.println("generated : " + line.toString());
                writer.write(line.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static File getFile(String name) {
        return new File(ClassLoader.getSystemClassLoader().getResource(name).getFile());
    }
}
