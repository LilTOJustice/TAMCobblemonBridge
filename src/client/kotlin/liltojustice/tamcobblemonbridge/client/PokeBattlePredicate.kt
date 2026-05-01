package liltojustice.tamcobblemonbridge.client

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.client.CobblemonClient
import liltojustice.trueadaptivemusicapi.trigger.arguments.TriggerArguments
import liltojustice.trueadaptivemusicapi.trigger.predicate.type.StaticPredicateType
import kotlin.reflect.typeOf

object PokeBattlePredicate
    : StaticPredicateType<PokeBattlePredicate.Arguments>("poke_battle", typeOf<Arguments>()) {
    data class Arguments(val battleType: BattleType): TriggerArguments()

    override fun test(arguments: Arguments): Boolean {
        return CobblemonClient.battle?.let {
            val enemySide = it.side2

            when (arguments.battleType) {
                BattleType.Any -> true
                BattleType.Wild -> enemySide.actors.all { actor -> actor.type == ActorType.WILD }
                BattleType.Trainer -> enemySide.actors.all { actor -> actor.type == ActorType.NPC }
                BattleType.Legendary -> enemySide.actors.any { actor ->
                    actor.activePokemon.any { pokemon ->
                        Constants.legendaries.contains(pokemon.battlePokemon?.displayName?.string)
                    }
                }
            }
        } ?: false
    }
}