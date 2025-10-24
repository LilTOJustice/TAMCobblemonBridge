package liltojustice.tamcobblemonbridge.client.mixin;

import com.cobblemon.mod.common.client.gui.battle.widgets.BattleMessagePane;
import liltojustice.tamcobblemonbridge.client.OnPokeBattleVictoryEvent;
import liltojustice.trueadaptivemusic.client.TAMClient;
import liltojustice.trueadaptivemusic.client.trigger.event.MusicEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cobblemon.mod.common.util.LocalizationUtilsKt.battleLang;

@Mixin(value = BattleMessagePane.class, remap = false)
abstract class OnPokeBattleVictoryEventMixin extends AlwaysSelectedEntryListWidget<BattleMessagePane.BattleMessageLine> {
    public OnPokeBattleVictoryEventMixin(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
    }

    @Inject(
        method = "addEntry(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane$BattleMessageLine;)I",
        at = @At("HEAD"))
    private void addEntry(BattleMessagePane.BattleMessageLine entry, CallbackInfoReturnable<Integer> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }

        String userWonString =  battleLang("win", player.getDisplayName()).getString();

        if (getOrderedTextString(entry.getLine()).equals(userWonString)) {
            MusicEvent.Companion.invokeMusicEvent(
                    TAMClient.INSTANCE.getEventRegistry().get(OnPokeBattleVictoryEvent.class));
        }
    }

    @Unique
    private String getOrderedTextString(OrderedText orderedText) {
        StringBuilder result = new StringBuilder();
        orderedText.accept((index, style, codePoint) -> {
            result.append((char) codePoint);
            return true;
        });

        return result.toString();
    }
}
