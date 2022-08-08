package amymialee.wandererscatalogue;

import amymialee.wandererscatalogue.screens.CatalogueScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class WanderersCatalogueClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(WanderersCatalogue.CATALOGUE_SCREEN_HANDLER, CatalogueScreen::new);
    }
}