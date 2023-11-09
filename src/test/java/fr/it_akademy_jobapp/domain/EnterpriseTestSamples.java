package fr.it_akademy_jobapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EnterpriseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Enterprise getEnterpriseSample1() {
        return new Enterprise().id(1L).name2("name21");
    }

    public static Enterprise getEnterpriseSample2() {
        return new Enterprise().id(2L).name2("name22");
    }

    public static Enterprise getEnterpriseRandomSampleGenerator() {
        return new Enterprise().id(longCount.incrementAndGet()).name2(UUID.randomUUID().toString());
    }
}
