package fr.woorib.random.generator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by baudoin on 01/02/2017.
 */
public class IpGenerator implements Generator<InetAddress> {
    Random random = new Random();
    Supplier<Byte> getFirst = () -> (byte) random.nextInt(255);
    Supplier<Byte> getSecond = () -> (byte) random.nextInt(255);
    Supplier<Byte> getThird = () -> (byte) random.nextInt(255);
    Supplier<Byte> getFourth = () -> (byte) random.nextInt(255);

    @Override
    public InetAddress get() {
        try {
            InetAddress byAddress = InetAddress.getByAddress(
                    new byte[]{getFirst.get(),
                            getSecond.get(),
                            getThird.get(),
                            getFourth.get()});
            return byAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Generator<InetAddress> inSubnet(InetAddress subnet) {
        byte[] address = subnet.getAddress();
        if (address[0] != 0) {
            getFirst = () -> address[0];
        }
        if (address[1] != 0) {
            getSecond = () -> address[1];
        }
        if (address[2] != 0) {
            getThird = () -> address[2];
        }
        if (address[3] != 0) {
            getFourth = () -> address[3];
        }
        return this;
    }

    @Override
    public Generator<InetAddress> lessThan(InetAddress before) {
        return null;
    }

    @Override
    public Generator<InetAddress> moreThan(InetAddress after) {
        return null;
    }
}
