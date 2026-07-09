package liltojustice.tamcobblemonbridge.client.javasucks

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.gui.battle.widgets.BattleMessagePane.BattleMessageLine
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.mod.common.util.lang
import liltojustice.tamcobblemonbridge.client.BattleType
import liltojustice.tamcobblemonbridge.client.Constants
import liltojustice.tamcobblemonbridge.client.OnPokeBattleVictoryEvent
import liltojustice.tamcobblemonbridge.client.getInternalString
import liltojustice.trueadaptivemusicapi.TAMAPI
import net.minecraft.client.MinecraftClient

object MixinExtensions {
    fun addEntry(entry: BattleMessageLine) {
        val player = MinecraftClient.getInstance().player ?: return
        val userWonString = battleLang("win", player.nameForScoreboard ?: "").string
        val caughtString = lang("capture.succeeded", "").string.dropLast(1)
        val internalString = entry.line.getInternalString()

        if (internalString == userWonString || internalString.startsWith(caughtString)) {
            val enemySide = CobblemonClient.battle?.side2 ?: return

            val battleType = if (enemySide.actors.any { actor ->
                    actor.activePokemon.any { pokemon ->
                        Constants.legendaries.contains(pokemon.battlePokemon?.displayName?.string)
                    }
                }) {
                BattleType.Legendary
            }
            else if (enemySide.actors.all { actor -> actor.type == ActorType.WILD }) {
                BattleType.Wild
            }
            else if (enemySide.actors.all { actor -> actor.type == ActorType.PLAYER }) {
                BattleType.Player
            }
            else if (enemySide.actors.all { actor -> actor.type == ActorType.NPC || actor.type == ActorType.PLAYER }) {
                BattleType.Trainer
            }
            else {
                BattleType.Any
            }

            TAMAPI.invokeEvent(
                OnPokeBattleVictoryEvent, OnPokeBattleVictoryEvent.Input(battleType))
        }
    }
}