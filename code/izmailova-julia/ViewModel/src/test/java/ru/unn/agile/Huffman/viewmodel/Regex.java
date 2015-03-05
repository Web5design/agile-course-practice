package ru.unn.agile.Huffman.viewmodel;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class Regex extends BaseMatcher {
    private final String regex;

    public Regex(final String regex) {
        this.regex = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    public static Matcher<? super String> pattern(final String regex) {
        Regex matcher = new Regex(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
