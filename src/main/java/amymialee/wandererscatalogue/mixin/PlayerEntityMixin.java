package amymialee.wandererscatalogue.mixin;

import amymialee.wandererscatalogue.util.PlayerOrderWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerOrderWrapper {
    @Unique
    private int playerOrder = -1;

    @Override
    public int getPlayerOrder() {
        return playerOrder;
    }

    @Override
    public void setPlayerOrder(int orderId) {
        this.playerOrder = orderId;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (getPlayerOrder() > -1) {
            nbt.putInt("wc:order", getPlayerOrder());
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("wc:order")) {
            this.setPlayerOrder(nbt.getInt("wc:order"));
        }
    }
}