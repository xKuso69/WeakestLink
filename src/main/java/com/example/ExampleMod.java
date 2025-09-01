package com.example.examplemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.PlayerDeathCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // When any player dies
        PlayerDeathCallback.EVENT.register((player, source) -> {
            MinecraftServer server = player.getServer();
            if (server == null) return;

            String msg = "Weakest Link! Blame " + player.getName().getString() + "!";
            server.getPlayerManager().broadcast(Text.literal(msg), false);

            // Kill all other players
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
                if (p == player) continue;
                p.kill();
            }
        });
    }
}
