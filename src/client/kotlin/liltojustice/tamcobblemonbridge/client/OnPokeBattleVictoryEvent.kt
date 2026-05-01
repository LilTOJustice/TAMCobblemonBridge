package liltojustice.tamcobblemonbridge.client

import liltojustice.trueadaptivemusicapi.trigger.arguments.TriggerArguments
import liltojustice.trueadaptivemusicapi.trigger.event.input.EventInput
import liltojustice.trueadaptivemusicapi.trigger.event.type.StaticEventType
import kotlin.reflect.typeOf

object OnPokeBattleVictoryEvent
    : StaticEventType<OnPokeBattleVictoryEvent.Arguments, OnPokeBattleVictoryEvent.Input>(
    "on_poke_battle_victory", typeOf<Arguments>()
) {
    data class Arguments(val battleType: BattleType): TriggerArguments()
    data class Input(val battleType: BattleType): EventInput()

    override fun validate(arguments: Arguments, input: Input): Boolean {
        return arguments.battleType == BattleType.Any || arguments.battleType == input.battleType
    }
}