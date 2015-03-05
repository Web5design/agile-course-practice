package ru.unn.agile.Fraction.Model;

public class Fraction {
    private static final int ONE_HALF_OF_INT_LENGTH = 16;
    private static final int ONE_HALF_OF_INT_MASK = 0xFF;

    private final int numerator;
    private final int denominator;

    public Fraction(final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator can not be zero!");
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
        return numerator << ONE_HALF_OF_INT_LENGTH | (denominator & ONE_HALF_OF_INT_MASK);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public enum Operation {
        ADD("Add") {
            public Fraction apply(final Fraction l, final Fraction r) {
                return l.add(r);
            }
        },
        SUBTRACT("Subtract") {
            public Fraction apply(final Fraction l, final Fraction r) {
                return l.subtract(r);
            }
        },
        MULTIPLY("Mul") {
            public Fraction apply(final Fraction l, final Fraction r) {
                return l.multiplyBy(r);
            }
        },
        DIVIDE("Divide") {
            public Fraction apply(final Fraction l, final Fraction r) {
                return l.divideBy(r);
            }
        };

        private final String name;
        Operation(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public abstract Fraction apply(final Fraction l, final Fraction r);
    }
}
