package liltojustice.tamcobblemonbridge.client

import liltojustice.trueadaptivemusic.client.TAMClient
import net.fabricmc.api.ClientModInitializer

class TamCobblemonBridgeClient : ClientModInitializer {
    override fun onInitializeClient() {
        TAMClient.registerEvent("on_poke_battle_victory", OnPokeBattleVictoryEvent::class)
        TAMClient.registerPredicate("poke_battle", PokeBattlePredicate::class)
    }
}
