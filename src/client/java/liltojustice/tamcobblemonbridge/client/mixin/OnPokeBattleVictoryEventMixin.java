package liltojustice.tamcobblemonbridge.client.mixin;

import com.cobblemon.mod.common.client.gui.battle.widgets.BattleMessagePane;
import liltojustice.tamcobblemonbridge.client.javasucks.MixinExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BattleMessagePane.class, remap = false)
abstract class OnPokeBattleVictoryEventMixin extends AlwaysSelectedEntryListWidget<BattleMessagePane.BattleMessageLine> {
    public OnPokeBattleVictoryEventMixin(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
    }

    @Inject(
        method = "addEntry(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$BattleMessageLine;)I",
        at = @At("HEAD"))
    private void addEntry(BattleMessagePane.BattleMessageLine entry, CallbackInfoReturnable<Integer> cir) {
        MixinExtensions.INSTANCE.addEntry(entry);
    }
}
