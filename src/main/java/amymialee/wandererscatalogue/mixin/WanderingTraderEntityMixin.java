package amymialee.wandererscatalogue.mixin;

import amymialee.wandererscatalogue.WanderersCatalogue;
import amymialee.wandererscatalogue.util.PlayerOrderWrapper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntityMixin {
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> INTERACTED = DataTracker.registerData(WanderingTraderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected WanderingTraderEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void MerchantEntity$DataTracker(CallbackInfo ci) {
        super.MerchantEntity$DataTracker(ci);
        this.dataTracker.startTracking(INTERACTED, false);
    }

    @Override
    public void setCustomer(PlayerEntity customer, CallbackInfo ci) {
        if (customer instanceof PlayerOrderWrapper wrapper && wrapper.getPlayerOrder() >= 0 && !this.dataTracker.get(INTERACTED)) {
            this.dataTracker.set(INTERACTED, true);
            this.getOffers().add(0, new TradeOffer(WanderersCatalogue.availableOffers.get(wrapper.getPlayerOrder()).toNbt()));
        }
        super.setCustomer(customer, ci);
    }

    @Inject(method = "fillRecipes", at = @At("HEAD"))
    protected void WanderersCatalogue$StockBook(CallbackInfo ci) {
        TradeOfferList tradeOfferList = this.getOffers();
        TradeOffer tradeOffer = new TradeOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(WanderersCatalogue.WANDERERS_CATALOGUE), 1, 6, 1);
        tradeOfferList.add(tradeOffer);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void WanderersCatalogue$InteractedWrite(NbtCompound nbt, CallbackInfo ci) {
        if (this.dataTracker.get(INTERACTED)) {
            nbt.putBoolean("wc:interacted", true);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void WanderersCatalogue$InteractedRead(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(INTERACTED, nbt.getBoolean("wc:interacted"));
    }
}