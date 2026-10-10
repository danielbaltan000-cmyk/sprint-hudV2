package com.WrDawn.sprinthud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

public class SprintHudClient implements ClientModInitializer {
    // Text and look. Change these to taste.
    private static final String TEXT = "[sprinting toggled]";
    private static final float SCALE = 1.0f;   // 1.0 = vanilla size
    private static final int X = 6;
    private static final int Y = 6;
    private static final int COLOR = 0xFFFFFFFF; // ARGB, white
    private static final int BG_COLOR = 0x66808080; // ARGB, gray at ~40% opacity
    private static final int PAD = 3;

    @Override
    public void onInitializeClient() {
        SprintHudConfig.load();
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.fromNamespaceAndPath("sprinthud", "label"),
                (graphics, tickCounter) -> render(graphics)
        );
    }

    private static void render(GuiGraphics graphics) {
        SprintHudConfig cfg = SprintHudConfig.INSTANCE;
        if (!cfg.enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        if (!cfg.alwaysVisible) {
            boolean walking = mc.options.keyUp.isDown()
                    || mc.options.keyDown.isDown()
                    || mc.options.keyLeft.isDown()
                    || mc.options.keyRight.isDown();
            if (!walking) return;
        }

        graphics.pose().pushMatrix();
        graphics.pose().scale(SCALE, SCALE);
        int tx = (int) (X / SCALE);
        int ty = (int) (Y / SCALE);
        int textWidth = mc.font.width(TEXT);
        graphics.fill(tx - PAD, ty - PAD, tx + textWidth + PAD, ty + 9 + PAD, BG_COLOR);
        graphics.drawString(mc.font, TEXT, tx, ty, COLOR, true);
        graphics.pose().popMatrix();
    }
}
