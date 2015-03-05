package ru.unn.agile.Huffman.infrastructure;

import ru.unn.agile.Huffman.viewmodel.ViewModelTest;
import ru.unn.agile.Huffman.viewmodel.ViewModel;


public class ViewModelLoggTest extends ViewModelTest {
    @Override
    public void setUp() {
        Logg lg =
            new Logg("./Izmailova-julia-lab3.log");
        super.setViewModel(new ViewModel(lg));
    }
}
