package ru.unn.agile.CalculateVolume.viewmodel;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel myView;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.myView = viewModel;
    }

    @Before
    public void setUp() {
        if (myView == null) {
            myView = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        myView = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", myView.stringFirstParamProperty().get());
        assertEquals("", myView.stringSecondParamProperty().get());
        assertEquals("", myView.stringThirdParamProperty().get());
        assertEquals("Cone", myView.figureProperty().get());
        assertEquals("", myView.calculateResultProperty().get());
        assertEquals(CalculateStatus.WAITING.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldFirstParam() {
        myView.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.WAITING.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldSecondParam() {
        myView.stringFirstParamProperty().set("1");
        assertEquals(CalculateStatus.WAITING.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldThirdParam() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenTwoParam();
        assertEquals(CalculateStatus.WAITING.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsCone() {
        assertEquals(true, myView.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsCylinder() {
        myView.figureProperty().set("Cylinder");
        assertEquals(true, myView.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsEllipsoid() {
        myView.figureProperty().set("Ellipsoid");
        assertEquals(false, myView.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsParallelepiped() {
        myView.figureProperty().set("Parallelepiped");
        assertEquals(false, myView.thirdParamDisabledProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureCone() {
        assertEquals("Radius", myView.labelFirstParamNameProperty().get());
        assertEquals("Height", myView.labelSecondParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureCylinder() {
        myView.figureProperty().set("Cylinder");
        assertEquals("Radius", myView.labelFirstParamNameProperty().get());
        assertEquals("Height", myView.labelSecondParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureEllipsoid() {
        myView.figureProperty().set("Ellipsoid");
        assertEquals("Radius A", myView.labelFirstParamNameProperty().get());
        assertEquals("Radius B", myView.labelSecondParamNameProperty().get());
        assertEquals("Radius C", myView.labelThirdParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureParallelepiped() {
        myView.figureProperty().set("Parallelepiped");
        assertEquals("Length", myView.labelFirstParamNameProperty().get());
        assertEquals("Width", myView.labelSecondParamNameProperty().get());
        assertEquals("Height", myView.labelThirdParamNameProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureCone() {
        setInputDataWhenTwoParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureCylinder() {
        myView.figureProperty().set("Cylinder");
        setInputDataWhenTwoParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureEllipsoid() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenThreeParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureParallelepiped() {
        myView.figureProperty().set("Parallelepiped");
        setInputDataWhenThreeParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenFirstParamIsWrong() {
        myView.stringFirstParamProperty().set("trash");
        myView.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenSecondParamIsWrong() {
        myView.stringFirstParamProperty().set("1");
        myView.stringSecondParamProperty().set("trash");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenThirdParamIsWrong() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenTwoParam();
        myView.stringThirdParamProperty().set("trash");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenInputCorrectWithTwoParam() {
        setInputDataWhenTwoParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());

    }

    @Test
    public void statusIsReadyWhenInputCorrectWithThreeParam() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenThreeParam();
        assertEquals(CalculateStatus.READY.toString(), myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenCalculateWithTwoParam() {
        setInputDataWhenTwoParam();
        myView.calculate();
        assertEquals(CalculateStatus.SUCCESS.toString(),
                myView.calculateStatusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenCalculateWithThreeParam() {
        setInputDataWhenThreeParam();
        myView.calculate();
        assertEquals(CalculateStatus.SUCCESS.toString(),
                myView.calculateStatusProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionIfLoggerNull() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyInTheStart() {
        List<String> log = myView.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsTrueMessageAfterCalculation() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenThreeParam();
        myView.calculate();
        String message = myView.getLog().get(0);
        assertTrue(message.matches(".*" + LoggingInfo.PRESSED_CALCULATE + ".*"));
    }

    @Test
    public void logContainsTwoInputParametersAfterCalculation() {
        setInputDataWhenTwoParam();
        myView.calculate();
        String message = myView.getLog().get(0);
        assertTrue(message.matches(".*" + myView.stringFirstParamProperty().get()
                + ".*" + myView.stringSecondParamProperty().get() + ".*"));
    }

    @Test
    public void logContainsThreeInputParametersAfterCalculation() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenThreeParam();
        myView.calculate();
        String parameters = myView.getLog().get(0);
        assertTrue(parameters.matches(".*" + myView.stringFirstParamProperty().get()
                + ".*" + myView.stringSecondParamProperty().get()
                + ".*" + myView.stringThirdParamProperty().get() + ".*"));
    }

    @Test
    public void logContainsCorrectArgumentsWhenFigureCone() {
        setInputDataWhenTwoParam();
        myView.calculate();
        String message = myView.getLog().get(0);
        assertTrue(message.matches(".*Arguments"
                + ".*" + myView.labelFirstParamNameProperty().get()
                + ".*" + myView.labelSecondParamNameProperty().get() + ".*"));
    }

    @Test
    public void logContainsCorrectArgumentsWhenFigureCylinder() {
        myView.figureProperty().set("Cylinder");
        setInputDataWhenTwoParam();
        myView.calculate();
        String messages = myView.getLog().get(0);
        assertTrue(messages.matches(".*Arguments"
                + ".*" + myView.labelFirstParamNameProperty().get()
                + ".*" + myView.labelSecondParamNameProperty().get() + ".*"));
    }

    @Test
    public void logContainsCorrectArgumentsWhenFigureEllipsoid() {
        myView.figureProperty().set("Ellipsoid");
        setInputDataWhenThreeParam();
        myView.calculate();
        String args = myView.getLog().get(0);
        assertTrue(args.matches(".*Arguments"
                + ".*" + myView.labelFirstParamNameProperty().get()
                + ".*" + myView.labelSecondParamNameProperty().get()
                + ".*" + myView.labelThirdParamNameProperty().get() + ".*"));
    }

    @Test
    public void logContainsCorrectArgumentsWhenFigureParallelepiped() {
        myView.figureProperty().set("Parallelepiped");
        setInputDataWhenThreeParam();
        myView.calculate();
        String arguments = myView.getLog().get(0);
        assertTrue(arguments.matches(".*Arguments"
                + ".*" + myView.labelFirstParamNameProperty().get()
                + ".*" + myView.labelSecondParamNameProperty().get()
                + ".*" + myView.labelThirdParamNameProperty().get() + ".*"));
    }

    @Test
    public void figureTypeIsDisplayedInTheLog() {
        setInputDataWhenTwoParam();
        myView.calculate();
        String message = myView.getLog().get(0);
        assertTrue(message.matches(".*Cone.*"));
    }

    @Test
    public void canDisplayFewLogMessages() {
        setInputDataWhenTwoParam();
        myView.calculate();
        myView.calculate();
        myView.calculate();
        assertEquals(3, myView.getLog().size());
    }

    @Test
    public void canDisplayOperationChangeInLog() {
        setInputDataWhenTwoParam();
        myView.onOperationChanged("Cone", "Cylinder");
        String message = myView.getLog().get(0);
        assertTrue(message.matches(".*" + LoggingInfo.CHANGED_OPERATION + "Cylinder.*"));
    }

    @Test
    public void calculateIsNotCalledWhenButtonIsDisabled() {
        myView.calculate();
        assertTrue(myView.getLog().isEmpty());
    }

    private void setInputDataWhenThreeParam() {
        myView.stringFirstParamProperty().set("1");
        myView.stringSecondParamProperty().set("1");
        myView.stringThirdParamProperty().set("1");
    }

    private void setInputDataWhenTwoParam() {
        myView.stringFirstParamProperty().set("1");
        myView.stringSecondParamProperty().set("1");
    }
}
