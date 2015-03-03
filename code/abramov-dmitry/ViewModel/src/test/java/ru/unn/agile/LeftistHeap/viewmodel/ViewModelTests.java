package ru.unn.agile.LeftistHeap.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.LeftistHeap.Model.LeftistHeap;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new LoggerMock());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertNotNull(viewModel.heapProperty());
        assertEquals("", viewModel.keyProperty().get());
        assertEquals("", viewModel.valueProperty().get());
        assertEquals("", viewModel.newKeyProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals("", viewModel.statusProperty().get());
    }

    @Test
    public void canAddElement() {
        viewModel.keyProperty().set("1");
        viewModel.add();

        assertEquals("New element added to heap 'heap 1'", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canValidateBeforeAddElement() {
        viewModel.add();

        assertEquals(Status.BAD_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canExtractByKey() {
        viewModel.keyProperty().set("1");
        viewModel.valueProperty().set("value");
        viewModel.add();

        viewModel.extract();

        assertEquals("Key: 1, Value: value", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canValidateBeforeExtractByKey() {
        viewModel.keyProperty().set("1");
        viewModel.valueProperty().set("value");
        viewModel.add();
        viewModel.keyProperty().set("abra-kadabra");

        viewModel.extract();

        assertEquals(
                "Any element was not deleted",
                viewModel.resultProperty().get());
        assertEquals(Status.BAD_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canCorrectExtractByNotContainedKey() {
        viewModel.keyProperty().set("3");
        viewModel.valueProperty().set("value");
        viewModel.add();
        viewModel.keyProperty().set("7");

        viewModel.extract();

        assertEquals(
                "Heap 'heap 1' not contain elements with key '7'",
                viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canGetMinimum() {
        viewModel.keyProperty().set("2");
        viewModel.valueProperty().set("big value");
        viewModel.add();

        viewModel.extractMinimum();

        assertEquals("Key: 2, Value: big value", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canGetMinimumFromEmptyHeap() {
        viewModel.extractMinimum();

        assertEquals("Heap 'heap 1' is empty", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canMerge() {
        viewModel.merge();

        assertEquals("Merged heap 'heap 2' with heap 'heap 1'", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canDecreaseKey() {
        viewModel.keyProperty().set("5");
        viewModel.valueProperty().set("value");
        viewModel.add();

        viewModel.newKeyProperty().set("2");

        viewModel.decreaseKey();

        assertEquals("Key decreased", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotDecreaseNotExistKey() {
        viewModel.keyProperty().set("3");
        viewModel.valueProperty().set("value");
        viewModel.add();

        viewModel.keyProperty().set("4");
        viewModel.newKeyProperty().set("2");

        viewModel.decreaseKey();

        assertEquals("Key '4' not found in heap 'heap 1'", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotDecreaseToBiggerKey() {
        viewModel.keyProperty().set("2");
        viewModel.valueProperty().set("value");
        viewModel.add();

        viewModel.newKeyProperty().set("6");

        viewModel.decreaseKey();

        assertEquals("New key must be less than current", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canValidateNewKeyInputBeforeDecrease() {
        viewModel.keyProperty().set("1");
        viewModel.newKeyProperty().set("words");

        viewModel.decreaseKey();

        assertEquals("Any key was not decreased", viewModel.resultProperty().get());
        assertEquals(Status.BAD_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canValidateKeyInputBeforeDecrease() {
        viewModel.keyProperty().set("this key");
        viewModel.newKeyProperty().set("4");

        viewModel.decreaseKey();

        assertEquals("Any key was not decreased", viewModel.resultProperty().get());
        assertEquals(Status.BAD_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canChangeSelectedHeap() {
        viewModel.heapProperty().set(new LeftistHeap<String>("new heap"));
        viewModel.keyProperty().set("5");
        viewModel.valueProperty().set("value");
        viewModel.add();

        assertEquals("New element added to heap 'new heap'", viewModel.resultProperty().get());
        assertEquals(Status.OK.toString(), viewModel.statusProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyAfterStart() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logChangingKeyInput() {
        viewModel.keyProperty().set("key");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Updated input: key - key, value - , newKey - "));
    }

    @Test
    public void logChangingNewKeyInput() {
        viewModel.newKeyProperty().set("new key");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Updated input: key - , value - , newKey - new key"));
    }

    @Test
    public void logChangingValueInput() {
        viewModel.valueProperty().set("value");

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Updated input: key - , value - value, newKey - "));
    }

    @Test
    public void logChangingHeap() {
        viewModel.onHeapChanged(
                new LeftistHeap<String>("old heap"),
                new LeftistHeap<String>("new heap"));

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Heap was changed to 'new heap'"));
    }

    @Test
    public void logAddElementToHeap() {
        viewModel.keyProperty().set("1");
        viewModel.valueProperty().set("first");
        viewModel.add();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Added element --1, first-- to 'heap 1'"));
    }

    @Test
    public void logTryAddBadFormattedElementToHeap() {
        viewModel.keyProperty().set("bad formatted");
        viewModel.valueProperty().set("first");
        viewModel.add();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Element --bad formatted, first-- was not added to 'heap 1'. "
                        + "It have bad format"));
    }

    @Test
    public void logExtractingElementFromHeap() {
        viewModel.keyProperty().set("1");
        viewModel.valueProperty().set("first");
        viewModel.add();

        viewModel.extract();

        assertEquals(2, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(1).matches(
                ".*Element --1, first-- was extracted from 'heap 1'"));
    }

    @Test
    public void logTryExtractNotExistElement() {
        viewModel.keyProperty().set("1");
        viewModel.extract();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Try extracting not exist element with key '1' from 'heap 1'"));
    }

    @Test
    public void logTryExtractElementByBadFormattedKey() {
        viewModel.keyProperty().set("very bad");
        viewModel.extract();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Try extracting by bad formatted key 'very bad' from 'heap 1'"));
    }

    @Test
    public void logHeapMerge() {
        viewModel.merge();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Merged heap 'heap 2' with heap 'heap 1'"));
    }

    @Test
    public void logExtractMinElement() {
        viewModel.keyProperty().set("2");
        viewModel.valueProperty().set("element");
        viewModel.add();

        viewModel.extractMinimum();

        assertEquals(2, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(1).matches(
                ".*Extracted minimum element --2, element-- from 'heap 1'"));
    }

    @Test
    public void logTryExtractMinFromEmptyHeap() {
        viewModel.keyProperty().set("2");
        viewModel.extractMinimum();

        assertEquals(1, viewModel.getLog().size());
        assertTrue(viewModel.getLog().get(0).matches(
                ".*Try extracted minimum element from empty heap 'heap 1'"));
    }
}
