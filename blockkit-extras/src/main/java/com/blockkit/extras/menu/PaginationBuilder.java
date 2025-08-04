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
 * Builder voor paginated menus met “Vorige” en “Volgende” knoppen
 * én klik‐callbacks op individuele items.
 */
public class PaginationBuilder {

    private final List<ItemStack> items;
    private final String title;
    private final int rows;

    private ItemStack filler = BlockKit.itemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                                      .name(" ")
                                      .build();
    private ItemStack prevButton = BlockKit.itemBuilder(Material.ARROW)
                                           .name(ColorUtils.color(
                                               GradientBuilder.of(
                                                   BlockKit.getChatConfig().getPrimaryHex(),
                                                   BlockKit.getChatConfig().getSecondaryHex()
                                               ).apply("Vorige")
                                           )).build();
    private ItemStack nextButton = BlockKit.itemBuilder(Material.ARROW)
                                           .name(ColorUtils.color(
                                               GradientBuilder.of(
                                                   BlockKit.getChatConfig().getPrimaryHex(),
                                                   BlockKit.getChatConfig().getSecondaryHex()
                                               ).apply("Volgende")
                                           )).build();

    private int prevRow, prevCol, nextRow, nextCol;

    // Callback voor item‐clicks
    private TriConsumer<Player, Integer, ItemStack> itemClickHandler;

    private PaginationBuilder(List<ItemStack> items, String title, int rows) {
        this.items = items;
        this.title = title;
        this.rows  = rows;
        this.prevRow = rows - 1;
        this.prevCol = 0;
        this.nextRow = rows - 1;
        this.nextCol = 8;
    }

    public static PaginationBuilder of(List<ItemStack> items, String title, int rows) {
        return new PaginationBuilder(items, title, rows);
    }

    public PaginationBuilder filler(ItemStack filler) {
        this.filler = filler;
        return this;
    }

    public PaginationBuilder prevButton(ItemStack button) {
        this.prevButton = button;
        return this;
    }

    public PaginationBuilder nextButton(ItemStack button) {
        this.nextButton = button;
        return this;
    }

    public PaginationBuilder prevPosition(int row, int col) {
        this.prevRow = row;
        this.prevCol = col;
        return this;
    }

    public PaginationBuilder nextPosition(int row, int col) {
        this.nextRow = row;
        this.nextCol = col;
        return this;
    }

    /**
     * Registreert een callback die wordt aangeroepen als de speler op
     * een item in de paginering klikt.
     *
     * @param handler (speler, absolute slot index, itemstack)
     */
    public PaginationBuilder onClick(TriConsumer<Player, Integer, ItemStack> handler) {
        this.itemClickHandler = handler;
        return this;
    }

    public Pagination build() {
        Pagination pagination = new Pagination();
        int itemsPerPage   = (rows - 1) * 9;
        int totalItems     = items.size();
        int pageCount      = (totalItems + itemsPerPage - 1) / itemsPerPage;

        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            int start = pageIndex * itemsPerPage;
            int end   = Math.min(start + itemsPerPage, totalItems);
            List<ItemStack> pageItems = items.subList(start, end);

            String pageTitle = String.format("%s [%d/%d]",
                title, pageIndex + 1, pageCount);

            MenuBuilder mb = BlockKit.menuBuilder(pageTitle, rows);
            mb.background(filler);

            // Plaats items én registreer click‐callbacks
            for (int i = 0; i < pageItems.size(); i++) {
                int row = i / 9;
                int col = i % 9;
                ItemStack item = pageItems.get(i);

                mb.item(row, col, item);

                if (itemClickHandler != null) {
                    final int absoluteIndex = start + i;
                    mb.onClick(row, col, e -> {
                        Player player = (Player) e.getWhoClicked();
                        itemClickHandler.accept(player, absoluteIndex, item);
                    });
                }
            }

            // Navigatieknoppen
            if (pageCount > 1) {
                final int idx = pageIndex;
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

            pagination.pages.add(mb.build());
        }

        return pagination;
    }

    public static class Pagination {
        private final List<Inventory> pages = new ArrayList<>();

        public void open(Player player) {
            openPage(player, 0);
        }

        public void openPage(Player player, int pageIndex) {
            if (pageIndex < 0 || pageIndex >= pages.size()) return;
            player.openInventory(pages.get(pageIndex));
        }

        public int getPageCount() {
            return pages.size();
        }
    }
}
