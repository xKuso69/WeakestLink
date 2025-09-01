package com.example.examplemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        // Run after any LivingEntity dies
        ServerLivingEntityEvents.AFTER_DEATH.register((LivingEntity entity, DamageSource source) -> {
            // Only care if it's a player
            if (!(entity instanceof ServerPlayerEntity victim)) return;

            MinecraftServer server = victim.getServer();
            if (server == null) return;

            // Broadcast blame message
            String msg = "Weakest Link! Blame " + victim.getName().getString() + "!";
            server.getPlayerManager().broadcast(Text.literal(msg), false);

            // Kill all other players
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) {
                if (p == victim) continue;
                p.kill();
            }
        });
    }
}
