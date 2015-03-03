package ru.unn.agile.calculateSalary.ViewModel;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexMatcher extends BaseMatcher {
    private final String inputRegex;

    public RegexMatcher(final String inputRegex) {
        this.inputRegex = inputRegex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(inputRegex);
    }

    public void describeTo(final Description description) {
        description.appendText("matches inputRegex = ");
        description.appendText(inputRegex);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegexMatcher matcher = new RegexMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> myMatcher = (Matcher<? super String>)   matcher;
        return myMatcher;
    }
}

