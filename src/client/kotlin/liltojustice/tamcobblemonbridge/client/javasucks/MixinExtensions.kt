package liltojustice.tamcobblemonbridge.client.javasucks

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.gui.battle.widgets.BattleMessagePane.BattleMessageLine
import com.cobblemon.mod.common.util.battleLang
import liltojustice.tamcobblemonbridge.client.BattleType
import liltojustice.tamcobblemonbridge.client.OnPokeBattleVictoryEvent
import liltojustice.tamcobblemonbridge.client.PokeBattlePredicate.Companion.legendaries
import liltojustice.tamcobblemonbridge.client.getInternalString
import liltojustice.trueadaptivemusic.client.TAMClient
import net.minecraft.client.MinecraftClient

object MixinExtensions {
    fun addEntry(entry: BattleMessageLine) {
        val player = MinecraftClient.getInstance().player ?: return
        val userWonString = battleLang("win", player.displayName ?: "").string

        if (entry.line.getInternalString() == userWonString) {
            val enemySide = CobblemonClient.battle?.side2 ?: return

            val battleType = if (enemySide.actors.all { actor -> actor.type == ActorType.WILD }) {
                BattleType.Wild
            }
            else if (enemySide.actors.all { actor -> actor.type == ActorType.NPC }) {
                BattleType.Trainer
            }
            else if (enemySide.actors.any { actor ->
                    actor.activePokemon.any { pokemon ->
                        legendaries.contains(pokemon.battlePokemon?.displayName?.string) } }) {
                BattleType.Legendary
            }
            else {
                BattleType.Any
            }

            TAMClient.invokeMusicEvent(OnPokeBattleVictoryEvent::class, battleType)
        }
    }
}