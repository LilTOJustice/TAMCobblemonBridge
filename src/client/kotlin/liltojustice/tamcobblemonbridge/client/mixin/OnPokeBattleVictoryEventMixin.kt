package liltojustice.tamcobblemonbridge.client.mixin

import com.cobblemon.mod.common.client.gui.battle.widgets.BattleMessagePane
import com.cobblemon.mod.common.util.battleLang
import liltojustice.tamcobblemonbridge.client.OnPokeBattleVictoryEvent
import liltojustice.tamcobblemonbridge.client.getInternalString
import liltojustice.trueadaptivemusic.client.TAMClient
import liltojustice.trueadaptivemusic.client.trigger.event.MusicEvent
import net.minecraft.client.MinecraftClient
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(BattleMessagePane::class)
class OnPokeBattleVictoryEventMixin {
    @Inject(
        method = ["addEntry(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane\$BattleMessageLine;)I"],
        at = [At("HEAD")])
    fun addEntry(entry: BattleMessagePane.BattleMessageLine, ci: CallbackInfoReturnable<Int>) {
        val userWonString =  battleLang("win", MinecraftClient.getInstance().player?.displayName ?: return)
            .string

        if (entry.line.getInternalString() == userWonString) {
            MusicEvent.invokeMusicEvent(TAMClient.eventRegistry[OnPokeBattleVictoryEvent::class])
        }
    }
}
