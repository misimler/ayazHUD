package com.ayaz.ayazmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class AyazModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(
            Identifier.of("ayazhud", "fps_totem_hud"),
            (graphics, tickCounter) -> {
                MinecraftClient client = MinecraftClient.getInstance();

                if (client.player == null || client.options.hudHidden) {
                    return;
                }

                int fps = client.getCurrentFps();
                int totems = 0;

                for (var stack : client.player.getInventory().getMainStacks()) {
                    if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                        totems += stack.getCount();
                    }
                }

                if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                    totems += client.player.getOffHandStack().getCount();
                }

                String fpsText = "FPS: " + fps;
                String totemText = totems + " TOT";

                int width = client.getWindow().getScaledWidth();
                int x = width - 8 - Math.max(
                    client.textRenderer.getWidth(fpsText),
                    client.textRenderer.getWidth(totemText)
                );

                graphics.drawText(
                    client.textRenderer, fpsText, x, 8, 0xDFFFFFFF, true
                );

                graphics.drawText(
                    client.textRenderer, totemText, x, 20, 0xDFFFFFFF, true
                );
            }
        );
    }
}
