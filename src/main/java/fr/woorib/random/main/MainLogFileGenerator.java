package fr.woorib.random.main;

import fr.woorib.bean.ApacheLogLine;
import fr.woorib.random.generator.ApacheLogLineGenerator;
import fr.woorib.random.generator.DictionaryGenerator;
import fr.woorib.random.generator.ZonedDateTimeGenerator;
import org.apache.commons.cli.*;

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
    private static Boolean generateLive = false;
    private static String fileName;
    private static String dateGeneration;

    private static ApacheLogLineGenerator apacheLogLineGenerator;
    public static void main(String[] args) throws IOException {
        parseCommandLineArguments(args);
        String[] split = dateGeneration.split("-");

        try (FileWriter writer = new FileWriter(fileName, true)) {
            apacheLogLineGenerator = new ApacheLogLineGenerator()
                    .withTimestampGenerator(generateLive ? ZonedDateTime::now :
                            new ZonedDateTimeGenerator().onDay(
                                    Integer.parseInt(split[0]),
                                    Integer.parseInt(split[1]),
                                    Integer.parseInt(split[2])))
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
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Date format is yyyy-mm-dd - "+dateGeneration);
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

    private static void parseCommandLineArguments(String[] args) {
        Options options = new Options();
        Option live = new Option("l", "live", false, "Is generation live?");
        live.setRequired(false);
        options.addOption(live);

        Option logFile = new Option("f", "logfile", true, "Log file output location");
        logFile.setRequired(true);
        options.addOption(logFile);


        Option date = new Option("d", "date", true, "Date for generation (if generation is not live) - format yyyy-mm-dd");
        date.setRequired(false);
        options.addOption(date);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("random-generator.jar -f filename [options]", options);

            System.exit(1);
            return;
        }

        generateLive = cmd.hasOption("live");
        fileName = cmd.getOptionValue("logfile");
        dateGeneration  = cmd.getOptionValue("date", "2017-01-01");
    }
}
