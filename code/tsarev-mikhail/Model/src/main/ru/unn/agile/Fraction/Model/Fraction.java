package ru.unn.agile.Fraction.Model;

public class Fraction {
    private final int numerator;
    private final int denominator;

    public Fraction(final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator can not be zero!");
        }
        if (numerator == 0) {
            this.numerator = 0;
            this.denominator = 1;
        } else {
            int denominatorSignum = (int) Math.signum(denominator);
            this.numerator = numerator / gcd(numerator, denominator) * denominatorSignum;
            this.denominator = denominator / gcd(numerator, denominator) * denominatorSignum;
        }
    }

    private static int gcd(final int a, final int b) {
        int x1 = Math.abs(a);
        int x2 = Math.abs(b);
        if (x1 > x2) {
            return gcd(x1 - x2, x2);
        } else if (x2 > x1) {
            return gcd(x1, x2 - x1);
        } else {
            return x1;
        }
    }

    private static int lcm(final int a, final int b) {
        int x1 = a;
        int x2 = b;
        while (x1 != x2) {
            if (x1 < x2) {
                x1 += a;
            } else {
                x2 += b;
            }
        }
        return x1;
    }

    public Fraction add(final Fraction other) {
        int commonDenominator = lcm(denominator, other.denominator);
        return new Fraction(numerator * (commonDenominator / denominator)
                            + other.numerator * (commonDenominator / other.denominator),
                            commonDenominator);
    }

    public Fraction subtract(final Fraction other) {
        int commonDenominator = lcm(denominator, other.denominator);
        return new Fraction(numerator * (commonDenominator / denominator)
                - other.numerator * (commonDenominator / other.denominator),
                commonDenominator);
    }

    public Fraction multiplyBy(final Fraction other) {
        return new Fraction(numerator * other.numerator, denominator * other.denominator);
    }

    public Fraction divideBy(final Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return new Fraction(numerator * other.denominator, denominator * other.numerator);
    }

    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Fraction other = (Fraction) object;
        return numerator == other.numerator && denominator == other.denominator;
    }

    public boolean greaterThan(final Fraction other) {
        return subtract(other).numerator > 0;
    }

    public boolean lessThan(final Fraction other) {
        return subtract(other).numerator < 0;
    }

    public int hashCode() {
        return 0;
    }
}
