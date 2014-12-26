package ru.unn.agile.ComplexProcent.Infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpMatcher extends BaseMatcher {
    private final String regularExp;

    public RegularExpMatcher(final String regex) {
        this.regularExp = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regularExp);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regularExp);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegularExpMatcher matcher = new RegularExpMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
