package liltojustice.tamcobblemonbridge.client

import com.google.gson.JsonObject
import liltojustice.trueadaptivemusic.client.trigger.event.MusicEvent

class OnPokeBattleVictoryEvent: MusicEvent() {
    companion object: MusicEventCompanion<OnPokeBattleVictoryEvent> {
        override fun fromJson(json: JsonObject): MusicEvent {
            return OnPokeBattleVictoryEvent()
        }

        override fun getTypeName(): String {
            return "on_poke_battle_victory"
        }
    }
}