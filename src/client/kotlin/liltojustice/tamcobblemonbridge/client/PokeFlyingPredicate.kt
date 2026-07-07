package liltojustice.tamcobblemonbridge.client

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import liltojustice.trueadaptivemusicapi.trigger.arguments.EmptyTriggerArguments
import liltojustice.trueadaptivemusicapi.trigger.predicate.type.PredicateType
import liltojustice.trueadaptivemusicapi.trigger.state.TriggerState
import net.minecraft.client.MinecraftClient
import kotlin.reflect.typeOf

object PokeFlyingPredicate: PredicateType<EmptyTriggerArguments, PokeFlyingPredicate.State>("poke_flying", typeOf<EmptyTriggerArguments>()) {
    data class State(var noGroundTime: UInt = GRACE_PERIOD_TICKS): TriggerState()

    override fun test(arguments: EmptyTriggerArguments, state: State): Boolean {
        return (MinecraftClient.getInstance().player?.vehicle as? PokemonEntity)
            ?.takeIf { it.canFly() }
            ?.let {
                if (it.isOnGround) {
                    state.noGroundTime = 0U
                    return false
                }

                if (state.noGroundTime == GRACE_PERIOD_TICKS) {
                    return true
                }

                state.noGroundTime++
                return false
            } ?: false
    }

    override fun createState(arguments: EmptyTriggerArguments): State {
        return State()
    }

    private val GRACE_PERIOD_TICKS = 10U
}