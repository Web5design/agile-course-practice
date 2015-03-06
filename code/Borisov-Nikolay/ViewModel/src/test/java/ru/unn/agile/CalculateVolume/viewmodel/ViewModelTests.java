package ru.unn.agile.CalculateVolume.viewmodel;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.stringFirstParamProperty().get());
        assertEquals("", viewModel.stringSecondParamProperty().get());
        assertEquals("", viewModel.stringThirdParamProperty().get());
        assertEquals("Cone", viewModel.figureProperty().get());
        assertEquals("", viewModel.calculateResultProperty().get());
        assertEquals(CalculateStatus.WAITING.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldFirstParam() {
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.WAITING.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldSecondParam() {
        viewModel.stringFirstParamProperty().set("1");
        assertEquals(CalculateStatus.WAITING.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFieldThirdParam() {
        viewModel.figureProperty().set("Ellipsoid");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.WAITING.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
      public void thirdParamIsDisabledWhenFigureIsCone() {
        assertEquals(true, viewModel.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsCylinder() {
        viewModel.figureProperty().set("Cylinder");
        assertEquals(true, viewModel.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsEllipsoid() {
        viewModel.figureProperty().set("Ellipsoid");
        assertEquals(false, viewModel.thirdParamDisabledProperty().get());
    }

    @Test
    public void thirdParamIsDisabledWhenFigureIsParallelepiped() {
        viewModel.figureProperty().set("Parallelepiped");
        assertEquals(false, viewModel.thirdParamDisabledProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureCone() {
        assertEquals("Radius", viewModel.labelFirstParamNameProperty().get());
        assertEquals("Height", viewModel.labelSecondParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureCylinder() {
        viewModel.figureProperty().set("Cylinder");
        assertEquals("Radius", viewModel.labelFirstParamNameProperty().get());
        assertEquals("Height", viewModel.labelSecondParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureEllipsoid() {
        viewModel.figureProperty().set("Ellipsoid");
        assertEquals("Radius A", viewModel.labelFirstParamNameProperty().get());
        assertEquals("Radius B", viewModel.labelSecondParamNameProperty().get());
        assertEquals("Radius C", viewModel.labelThirdParamNameProperty().get());
    }

    @Test
    public void labelsIsCorrectWhenFigureParallelepiped() {
        viewModel.figureProperty().set("Parallelepiped");
        assertEquals("Length", viewModel.labelFirstParamNameProperty().get());
        assertEquals("Width", viewModel.labelSecondParamNameProperty().get());
        assertEquals("Height", viewModel.labelThirdParamNameProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureCone() {
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureCylinder() {
        viewModel.figureProperty().set("Cylinder");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureEllipsoid() {
        viewModel.figureProperty().set("Ellipsoid");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.stringThirdParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFillAndFigureParallelepiped() {
        viewModel.figureProperty().set("Parallelepiped");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.stringThirdParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenFirstParamIsWrong() {
        viewModel.stringFirstParamProperty().set("trash");
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenSecondParamIsWrong() {
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("trash");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenThirdParamIsWrong() {
        viewModel.figureProperty().set("Ellipsoid");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.stringThirdParamProperty().set("trash");
        assertEquals(CalculateStatus.BAD_FORMAT_OF_INPUT.toString(),
                viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsReadyWhenInputCorrectWithTwoParam() {
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());

    }

    @Test
    public void statusIsReadyWhenInputCorrectWithThreeParam() {
        viewModel.figureProperty().set("Ellipsoid");
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.stringThirdParamProperty().set("1");
        assertEquals(CalculateStatus.READY.toString(), viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenCalculateWithTwoParam() {
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.calculate();
        assertEquals(CalculateStatus.SUCCESS.toString(),
                viewModel.calculateStatusProperty().get());
    }

    @Test
    public void statusIsSuccessWhenCalculateWithThreeParam() {
        viewModel.stringFirstParamProperty().set("1");
        viewModel.stringSecondParamProperty().set("1");
        viewModel.stringThirdParamProperty().set("1");
        viewModel.calculate();
        assertEquals(CalculateStatus.SUCCESS.toString(),
                viewModel.calculateStatusProperty().get());
    }
}
