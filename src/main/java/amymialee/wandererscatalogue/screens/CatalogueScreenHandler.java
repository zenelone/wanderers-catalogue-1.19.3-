package amymialee.wandererscatalogue.screens;

import amymialee.wandererscatalogue.WanderersCatalogue;
import amymialee.wandererscatalogue.util.PlayerOrderWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;

import java.util.List;

public class CatalogueScreenHandler extends ScreenHandler {
    private final Property selectedRecipe;

    public CatalogueScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(WanderersCatalogue.CATALOGUE_SCREEN_HANDLER, syncId);
        this.selectedRecipe = Property.create();
        if (playerInventory.player instanceof PlayerOrderWrapper wrapper) {
            this.selectedRecipe.set(wrapper.getPlayerOrder());
        }
        for (int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperty(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            if (player instanceof PlayerOrderWrapper wrapper) {
                wrapper.setPlayerOrder(id);
            }
        }
        return true;
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < getAvailableOfferCount();
    }

    public ScreenHandlerType<?> getType() {
        return WanderersCatalogue.CATALOGUE_SCREEN_HANDLER;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    public int getAvailableOfferCount() {
        return WanderersCatalogue.availableOffers.size();
    }

    public List<TradeOffer> getAvailableOffers() {
        return WanderersCatalogue.availableOffers;
    }
}