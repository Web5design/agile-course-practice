package ru.unn.agile.Fraction.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FractionTest {

    @Test
    public void canCreateFraction() {
        Fraction fraction = new Fraction(1, 2);
        assertNotNull(fraction);
    }

    @Test
    public void compareEqualFractions() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 2);
        assertEquals(fraction1, fraction2);
    }

    @Test
    public void compareNonEqualFractions() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 1);
        assertNotEquals(fraction1, fraction2);
    }
}
