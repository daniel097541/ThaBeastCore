package info.beastsoftware.beastcore.beastutils.utils;

import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;
import info.beastsoftware.beastcore.beastutils.gui.entity.Page;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {


    public static int calculateNumberOfPages(int pageSize, int offset, int listSize) {
        int finalNumberOfSlots = pageSize - offset;
        int pageNumber = listSize / finalNumberOfSlots;

        if (listSize % finalNumberOfSlots != 0)
            pageNumber++;

        return pageNumber;
    }


    public static List getPageSublist(List original, int startPos, int endPos) {
        List subList;
        subList = original.subList(startPos, endPos);
        return subList;
    }


    public static List<IPage> createPages(List<IButton> buttons, int pageSize, int pageOffset, String pageTitle, IButton nextButton, IButton previousButton, ItemStack spacer) {


        List<IPage> pages = new ArrayList<>();

        int totalSize = buttons.size();
        int pageCount = PaginationUtil.calculateNumberOfPages(pageSize, 9, totalSize);

        int fromIndex = 0;
        int subListSize = pageSize - pageOffset;
        int toIndex = subListSize;


        for (int i = 0; i < pageCount; i++) {


            //check if its the end
            if (fromIndex >= totalSize - 1)
                break;

            //check if its out of bounds
            if (toIndex > totalSize - 1)
                toIndex = totalSize - 1;

            List<IButton> subList = new ArrayList<>(buttons.subList(fromIndex, toIndex));

            //add previous button if not first page
            if (i != 0)
                subList.add(previousButton);

            //add next button if not last page
            if (i != pageCount - 1)
                subList.add(nextButton);

            //generate inventory and add items to it
            String title = pageTitle;
            title = StrUtils.replacePlaceholder(pageTitle, "{page}", (i + 1) + "");
            Inventory pageInv = Bukkit.createInventory(null, pageSize, StrUtils.translate(title));


            IInventoryUtil.addButtonsToInventory(subList, pageInv);

            //fill with spacers
            if (spacer != null)
                IInventoryUtil.fillEmptySlots(pageInv, spacer);


            //add page to pages list
            pages.add(new Page(i, pageInv, subList));

            fromIndex += (subListSize);
            toIndex += (subListSize);
        }

        return pages;
    }

}
