package info.beastsoftware.beastcore.beastutils.gui.repository;

import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;

import java.util.List;

public class GUIRepo implements IGUIRepository {

    private List<IPage> pages;


    public GUIRepo(List<IPage> pages) {
        this.pages = pages;
    }


    @Override
    public List<IPage> getPages() {
        return pages;
    }

    @Override
    public IPage getPage(int pageIndex) {
        return pages.get(pageIndex);
    }


}
