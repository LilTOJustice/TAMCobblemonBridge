package liltojustice.tamcobblemonbridge.client

import liltojustice.trueadaptivemusic.client.trigger.event.MusicEvent

class OnPokeBattleVictoryEvent(private val battleType: BattleType): MusicEvent() {
    override fun validate(vararg eventArgs: Any?): Boolean {
        val battleType = eventArgs[0] as? BattleType ?: return false

        return this.battleType == BattleType.Any || this.battleType == battleType
    }
}