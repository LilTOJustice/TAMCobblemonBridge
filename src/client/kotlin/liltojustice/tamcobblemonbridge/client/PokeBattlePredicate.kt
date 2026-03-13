package liltojustice.tamcobblemonbridge.client

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.client.CobblemonClient
import liltojustice.trueadaptivemusic.client.trigger.predicate.MusicPredicate
import java.io.BufferedReader
import java.io.InputStreamReader

class PokeBattlePredicate(private val battleType: BattleType): MusicPredicate() {
    override fun test(): Boolean {
        return CobblemonClient.battle?.let {
            val enemySide = it.side2

            when (battleType) {
                BattleType.Any -> true
                BattleType.Wild -> enemySide.actors.all { actor -> actor.type == ActorType.WILD }
                BattleType.Trainer -> enemySide.actors.all { actor -> actor.type == ActorType.NPC }
                BattleType.Legendary -> enemySide.actors.any { actor ->
                    actor.activePokemon.any { pokemon ->
                        legendaries.contains(pokemon.battlePokemon?.displayName?.string) } }
            }
        } ?: false
    }

    companion object {
        var legendaries = BufferedReader(
            InputStreamReader(
                this::class.java.getClassLoader()
                    .getResourceAsStream("assets/legendaries")!!, "UTF-8")
        ).use { reader -> reader.readLines().toSet() }
    }
}