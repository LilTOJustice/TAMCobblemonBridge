package liltojustice.tamcobblemonbridge.client

import liltojustice.trueadaptivemusicapi.TAMAPI
import net.fabricmc.api.ClientModInitializer

class TamCobblemonBridgeClient : ClientModInitializer {
    override fun onInitializeClient() {
        TAMAPI.registerEventType(OnPokeBattleVictoryEvent)
        TAMAPI.registerPredicateType(PokeBattlePredicate)
    }
}
