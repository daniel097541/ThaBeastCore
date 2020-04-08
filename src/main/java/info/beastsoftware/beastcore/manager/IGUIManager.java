package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.gui.IComplexGui;
import info.beastsoftware.beastcore.gui.ISimpleGui;
import info.beastsoftware.beastcore.struct.ComplexGui;
import info.beastsoftware.beastcore.struct.SimpleGui;

public interface IGUIManager {

    void load();

    ISimpleGui getSimpleGUI(SimpleGui guiType);

    IComplexGui getComplexGUI(ComplexGui guiType);

    void reload();

}
