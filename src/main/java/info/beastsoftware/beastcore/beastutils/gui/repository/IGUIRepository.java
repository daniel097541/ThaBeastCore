package info.beastsoftware.beastcore.beastutils.gui.repository;

import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;

import java.util.List;

public interface IGUIRepository {


    List<IPage> getPages();

    IPage getPage(int pageIndex);


}
