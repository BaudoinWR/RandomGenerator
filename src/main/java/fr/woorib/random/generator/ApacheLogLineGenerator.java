package fr.woorib.random.generator;

import fr.woorib.bean.ApacheLogLine;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 * Generates Log lines in the format of the common access logs from apache
 */
public class ApacheLogLineGenerator implements Generator<ApacheLogLine> {
    private Random random = new Random();
    private ObjectGenerator<ApacheLogLine> generator;

    /**
     * Default Generator<br/>
     * Generates the following values by default:
     * <ul>
     *     <li>Timestamp of current Time</li>
     *     <li>User in the form user{i} with i between 0 and 4</li>
     *     <li>Request GET /request/page/{i} with i between 0 and 9</li>
     *     <li>StatusCode between 0 and 549</li>
     *     <li>DataSize between 0 and 999,999</li>
     * </ul>
     */
    public ApacheLogLineGenerator() {
        generator = new ObjectGenerator<ApacheLogLine>(ApacheLogLine.class);
        withTimestampGenerator(ZonedDateTime::now);
        withUserGenerator(() -> "user" + random.nextInt(5));
        withRequestGenerator(() -> "GET /request/page/" + random.nextInt(10));
        withStatusCodeGenerator(() -> random.nextInt(550));
        withDataSizeGenerator(() -> random.nextInt(1_000_000));
    }


    @Override
    public ApacheLogLine get() {
        return generator.get();
    }

    /**
     * Specifies how to generate users
     * @param generator
     * @return configured generator
     */
    public ApacheLogLineGenerator withUserGenerator(Supplier<String> generator) {
        this.generator.with("user", generator);
        return this;
    }

    /**
     * Specifies how to generate users
     * @param generator
     * @return configured generator
     */
    public ApacheLogLineGenerator withTimestampGenerator(Supplier<ZonedDateTime> generator) {
        this.generator.with(ZonedDateTime.class, generator);
        return this;
    }

    /**
     * Specifies how to generate requests entries
     * @param generator
     * @return configured generator
     */
    public ApacheLogLineGenerator withRequestGenerator(Supplier<String> generator) {
        this.generator.with("request", generator);
        return this;
    }

    /**
     * Specifies how to generate statusCodes
     * @param generator
     * @return
     */
    public ApacheLogLineGenerator withStatusCodeGenerator(Supplier<Integer> generator) {
        this.generator.with("statusCode", generator);
        return this;
    }

    /**
     * Specifies how to generate dataSize
     * @param generator
     * @return
     */
    public ApacheLogLineGenerator withDataSizeGenerator(Supplier<Integer> generator) {
        this.generator.with("dataSize", generator);
        return this;
    }

    /**
     * Specifies how to generate userAgent
     * @param generator
     * @return
     */
    public ApacheLogLineGenerator withUserAgentGenerator(DictionaryGenerator<String> generator) {
        this.generator.with("userAgent", generator);
        return this;
    }

    /**
     * Specifies how to generate referer
     * @param generator
     * @return
     */
    public ApacheLogLineGenerator withRefererGenerator(DictionaryGenerator<String> generator) {
        this.generator.with("referer", generator);
        return this;
    }
}
