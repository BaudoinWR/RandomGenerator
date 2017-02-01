package fr.woorib.random.generator;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by baudoin on 01/02/2017.
 */
public class IpGeneratorTest {
    IpGenerator generator = new IpGenerator();
    InetAddress subnet = InetAddress.getByAddress(new byte[] {112,25,0,0});

    public IpGeneratorTest() throws UnknownHostException {
    }

    @Test
    public void testIpGenerated() throws UnknownHostException {
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
        System.out.println(generator.get());
    }

    @Test
    public void testSubnetIpGenerated() {
        System.out.println(generator.inSubnet(subnet).get());
        System.out.println(generator.inSubnet(subnet).get());
        System.out.println(generator.inSubnet(subnet).get());
        System.out.println(generator.inSubnet(subnet).get());
        System.out.println(generator.inSubnet(subnet).get());
    }
}
