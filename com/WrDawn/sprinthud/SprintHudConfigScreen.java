package com.WrDawn.sprinthud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SprintHudConfigScreen extends Screen {
    private final Screen parent;

    public SprintHudConfigScreen(Screen parent) {
        super(Component.literal("Sprint HUD Options"));
        this.parent = parent;
    }

    private static Component enabledLabel() {
        return Component.literal("Enabled: " + (SprintHudConfig.INSTANCE.enabled ? "ON" : "OFF"));
    }

    private static Component alwaysLabel() {
        return Component.literal("Always visible: " + (SprintHudConfig.INSTANCE.alwaysVisible ? "ON" : "OFF"));
    }

    @Override
    protected void init() {
        int w = 200;
        int x = this.width / 2 - w / 2;
        int y = this.height / 2 - 40;

        this.addRenderableWidget(Button.builder(enabledLabel(), b -> {
            SprintHudConfig.INSTANCE.enabled = !SprintHudConfig.INSTANCE.enabled;
            b.setMessage(enabledLabel());
            SprintHudConfig.save();
        }).bounds(x, y, w, 20).build());

        this.addRenderableWidget(Button.builder(alwaysLabel(), b -> {
            SprintHudConfig.INSTANCE.alwaysVisible = !SprintHudConfig.INSTANCE.alwaysVisible;
            b.setMessage(alwaysLabel());
            SprintHudConfig.save();
        }).bounds(x, y + 28, w, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"), b -> this.onClose())
                .bounds(x, y + 70, w, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
        graphics.drawCenteredString(this.font,
                Component.literal("Always visible OFF = only shows while moving"),
                this.width / 2, this.height / 2 + 60, 0xFFAAAAAA);
    }

    @Override
    public void onClose() {
        SprintHudConfig.save();
        if (this.minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
