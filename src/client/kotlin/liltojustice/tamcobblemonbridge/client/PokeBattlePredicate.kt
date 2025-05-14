package liltojustice.tamcobblemonbridge.client

import com.cobblemon.mod.common.client.CobblemonClient
import com.google.gson.JsonObject
import liltojustice.trueadaptivemusic.client.trigger.predicate.MusicPredicate
import net.minecraft.client.MinecraftClient

class PokeBattlePredicate: MusicPredicate() {
    override fun test(client: MinecraftClient): Boolean {
        return CobblemonClient.battle != null
    }

    companion object: MusicPredicateCompanion<PokeBattlePredicate> {
        override fun getTypeName(): String {
            return "poke_battle"
        }

        override fun fromJson(json: JsonObject): MusicPredicate {
            return PokeBattlePredicate()
        }
    }
}