package ru.unn.agile.Fraction.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class FractionTest {

    @Test
    public void canCreateFraction() {
        Fraction fraction = new Fraction(1, 2);
        assertNotNull(fraction);
    }

    @Test
    public void canCreateOtherFraction() {
        Fraction fraction = new Fraction(4, 6);
        assertNotNull(fraction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotDivideByZero() {
        Fraction fraction = new Fraction(1, 0);
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

    @Test
    public void fractionReducing() {
        Fraction fraction1 = new Fraction(1, 1);
        Fraction fraction2 = new Fraction(3, 3);
        assertEquals(fraction1, fraction2);
    }

    @Test
    public void addFractions() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 3);
        assertEquals(new Fraction(5, 6),
                    fraction1.add(fraction2));
    }

    @Test
    public void addNegativeFraction() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(-1, 3);
        assertEquals(new Fraction(1, 6),
                    fraction1.add(fraction2));
    }

    @Test
    public void addZero() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(0, 1);
        assertEquals(fraction1, fraction1.add(fraction2));
    }

    @Test
    public void subtractFractions() {
        Fraction fraction1 = new Fraction(5, 6);
        Fraction fraction2 = new Fraction(1, 3);
        assertEquals(new Fraction(1, 2),
                fraction1.subtract(fraction2));
    }

    @Test
    public void subtractNegativeFraction() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(-1, 3);
        assertEquals(new Fraction(5, 6),
                fraction1.subtract(fraction2));
    }

    @Test
    public void subtractZero() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(0, 1);
        assertEquals(fraction1, fraction1.subtract(fraction2));
    }

    @Test
    public void fractionGreaterThan() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 3);
        assertTrue(fraction1.greaterThan(fraction2));
    }

    @Test
    public void fractionLessThan() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 3);
        assertTrue(fraction2.lessThan(fraction1));
    }

    @Test
    public void multiplyBy() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(2, 3);
        assertEquals(new Fraction(2, 6), fraction1.multiplyBy(fraction2));
    }

    @Test
    public void divideBy() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(2, 3);
        assertEquals(new Fraction(3, 4), fraction1.divideBy(fraction2));
    }
}
