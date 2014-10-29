package ru.unn.agile.Fraction.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FractionTest {

    @Test
    public void canCreateFraction() {
        Fraction fraction = new Fraction();
        assertNotNull(fraction);
    }
}
