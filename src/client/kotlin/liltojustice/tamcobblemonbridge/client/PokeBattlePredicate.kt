package liltojustice.tamcobblemonbridge.client

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.client.CobblemonClient
import com.google.gson.JsonObject
import liltojustice.trueadaptivemusic.client.trigger.predicate.MusicPredicate
import net.minecraft.client.MinecraftClient
import net.minecraft.util.JsonHelper
import java.io.BufferedReader
import java.io.InputStreamReader

class PokeBattlePredicate(private val battleType: BattleType): MusicPredicate() {
    override fun test(client: MinecraftClient): Boolean {
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

    override fun toJson(): JsonObject {
        val result = super.toJson()
        result.addProperty("battleType", battleType.toString())

        return result
    }

    companion object: MusicPredicateCompanion<PokeBattlePredicate> {
        var legendaries = BufferedReader(
            InputStreamReader(
                this::class.java.getClassLoader()
                    .getResourceAsStream("assets/legendaries")!!, "UTF-8")
        ).use { reader -> reader.readLines().toSet() }

        override fun fromJson(json: JsonObject): MusicPredicate {
            return PokeBattlePredicate(
                BattleType.valueOf(JsonHelper.getString(json, "battleType")))
        }
    }
}