package liltojustice.tamcobblemonbridge.client

import com.google.gson.JsonObject
import liltojustice.trueadaptivemusic.client.trigger.event.MusicEvent
import net.minecraft.util.JsonHelper

class OnPokeBattleVictoryEvent(private val battleType: BattleType): MusicEvent() {
    override fun validate(vararg eventArgs: Any?): Boolean {
        val battleType = eventArgs[0] as? BattleType ?: return false

        return this.battleType == BattleType.Any || this.battleType == battleType
    }

    override fun toJson(): JsonObject {
        val result = super.toJson()
        result.addProperty("battleType", battleType.toString())

        return result
    }

    companion object: MusicEventCompanion<OnPokeBattleVictoryEvent> {
        override fun fromJson(json: JsonObject): MusicEvent {
            return OnPokeBattleVictoryEvent(
                BattleType.valueOf(JsonHelper.getString(json, "battleType")))
        }
    }
}