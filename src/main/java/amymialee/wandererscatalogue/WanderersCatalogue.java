package amymialee.wandererscatalogue;

import amymialee.wandererscatalogue.screens.CatalogueScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class WanderersCatalogue implements ModInitializer {
    public static final String MOD_ID = "wandererscatalogue";
    private static List<TradeOffer> availableOffers;

    public static final ScreenHandlerType<CatalogueScreenHandler> CATALOGUE_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "catalogue", new ScreenHandlerType<>(CatalogueScreenHandler::new));
    public static final Item WANDERERS_CATALOGUE = Registry.register(Registry.ITEM, id("catalogue"), new CatalogueItem(new FabricItemSettings().rarity(Rarity.RARE).group(ItemGroup.TOOLS)));

    @Override
    public void onInitialize() {}

    public static List<TradeOffer> getAvailableOffers(Entity entity) {
        if (availableOffers != null) {
            return availableOffers;
        }
        fillRecipes(entity);
        return availableOffers;
    }

    protected static void fillRecipes(Entity entity) {
        ArrayList<TradeOffer> offers = new ArrayList<>();
        Random random = Random.create();
        TradeOffers.Factory[] factory = TradeOffers.WANDERING_TRADER_TRADES.get(1);
        if (factory != null) {
            for (TradeOffers.Factory fact : factory) {
                TradeOffer tradeOffer = fact.create(entity, random);
                if (tradeOffer != null) {
                    offers.add(tradeOffer);
                }
            }
        }
        TradeOffers.Factory[] factory2 = TradeOffers.WANDERING_TRADER_TRADES.get(2);
        if (factory2 != null) {
            for (TradeOffers.Factory fact : factory2) {
                TradeOffer tradeOffer = fact.create(entity, random);
                if (tradeOffer != null) {
                    offers.add(tradeOffer);
                }
            }
        }
        availableOffers = offers;
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}