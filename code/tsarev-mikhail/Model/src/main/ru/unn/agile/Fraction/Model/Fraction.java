package ru.unn.agile.Fraction.Model;

public class Fraction {
    private final int numerator;
    private final int denominator;

    public Fraction(final int numerator, final int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public boolean equals(final Object object) {
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Fraction other = (Fraction) object;
        return numerator == other.numerator && denominator == other.denominator;
    }

    public int hashCode() {
        return 0;
    }
}
