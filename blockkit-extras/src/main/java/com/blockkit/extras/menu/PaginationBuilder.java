package com.blockkit.extras.menu;

import com.blockkit.BlockKit;
import com.blockkit.api.menu.MenuBuilder;
import com.blockkit.core.text.builder.GradientBuilder;
import com.blockkit.core.text.util.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder voor paginated menus met "Vorige" en "Volgende" knoppen.
 */
public class PaginationBuilder {

    private final List<ItemStack> items;
    private final String title;
    private final int rows;

    private ItemStack filler =
        BlockKit.itemBuilder(Material.GRAY_STAINED_GLASS_PANE)
               .name(" ")
               .build();
    private ItemStack prevButton =
        BlockKit.itemBuilder(Material.ARROW)
               .name(ColorUtils.color(
                       GradientBuilder.of(BlockKit.getChatConfig().getPrimaryHex(), BlockKit.getChatConfig().getSecondaryHex())
                               .apply("Vorige")
               ))
               .build();
    private ItemStack nextButton =
        BlockKit.itemBuilder(Material.ARROW)
                .name(ColorUtils.color(
                        GradientBuilder.of(BlockKit.getChatConfig().getPrimaryHex(), BlockKit.getChatConfig().getSecondaryHex())
                                .apply("Volgende")
                ))
               .build();

    // standaard: links-onder en rechts-onder
    private int prevRow, prevCol, nextRow, nextCol;

    private PaginationBuilder(List<ItemStack> items, String title, int rows) {
        this.items = items;
        this.title = title;
        this.rows  = rows;
        this.prevRow = rows - 1;
        this.prevCol = 0;
        this.nextRow = rows - 1;
        this.nextCol = 8;
    }

    /** Start de builder. */
    public static PaginationBuilder of(List<ItemStack> items, String title, int rows) {
        return new PaginationBuilder(items, title, rows);
    }

    /** Achtergrond‐vuller instellen. */
    public PaginationBuilder filler(ItemStack filler) {
        this.filler = filler;
        return this;
    }

    /** Item voor de “Vorige” knop. */
    public PaginationBuilder prevButton(ItemStack button) {
        this.prevButton = button;
        return this;
    }

    /** Item voor de “Volgende” knop. */
    public PaginationBuilder nextButton(ItemStack button) {
        this.nextButton = button;
        return this;
    }

    /** Positie van “Vorige” knop (row, col). */
    public PaginationBuilder prevPosition(int row, int col) {
        this.prevRow = row;
        this.prevCol = col;
        return this;
    }

    /** Positie van “Volgende” knop (row, col). */
    public PaginationBuilder nextPosition(int row, int col) {
        this.nextRow = row;
        this.nextCol = col;
        return this;
    }

    /**
     * Bouw de pagination en return het Pagination‐object.
     */
    public Pagination build() {
        Pagination pagination = new Pagination();
        int itemsPerPage   = (rows - 1) * 9; // bovenste rijen voor items
        int totalItems     = items.size();
        int pageCount      = (totalItems + itemsPerPage - 1) / itemsPerPage;

        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            // items voor deze pagina
            int start = pageIndex * itemsPerPage;
            int end   = Math.min(start + itemsPerPage, totalItems);
            List<ItemStack> pageItems = items.subList(start, end);

            // menu‐builder met paginatitel
            String pageTitle = String.format("%s [%d/%d]",
                title, pageIndex + 1, pageCount);
            MenuBuilder mb = BlockKit.menuBuilder(pageTitle, rows);
            mb.background(filler);

            // plaats items
            for (int i = 0; i < pageItems.size(); i++) {
                int row = i / 9;
                int col = i % 9;
                mb.item(row, col, pageItems.get(i));
            }

            // voeg navigatie‐knoppen toe als er meer dan 1 pagina is
            if (pageCount > 1) {
                final int idx = pageIndex; // voor lambda
                if (pageIndex > 0) {
                    mb.item(prevRow, prevCol, prevButton)
                      .onClick(prevRow, prevCol, e ->
                          pagination.openPage((Player) e.getWhoClicked(), idx - 1)
                      );
                }
                if (pageIndex < pageCount - 1) {
                    mb.item(nextRow, nextCol, nextButton)
                      .onClick(nextRow, nextCol, e ->
                          pagination.openPage((Player) e.getWhoClicked(), idx + 1)
                      );
                }
            }

            Inventory inv = mb.build();
            pagination.pages.add(inv);
        }

        return pagination;
    }

    /**
     * Resultaat van de builder: open pagina’s voor spelers.
     */
    public static class Pagination {
        private final List<Inventory> pages = new ArrayList<>();

        /** Open de eerste pagina. */
        public void open(Player player) {
            openPage(player, 0);
        }

        /** Open specifieke pagina (0-based). */
        public void openPage(Player player, int pageIndex) {
            if (pageIndex < 0 || pageIndex >= pages.size()) return;
            player.openInventory(pages.get(pageIndex));
        }

        /** Aantal pagina’s. */
        public int getPageCount() {
            return pages.size();
        }
    }
}
