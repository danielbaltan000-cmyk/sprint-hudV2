package com.WrDawn.sprinthud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class SprintHudConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("sprinthud.json");

    /** Master switch. Off = nothing is shown. */
    public boolean enabled = true;
    /** On = label always visible. Off = only while a movement key is held. */
    public boolean alwaysVisible = false;

    public static SprintHudConfig INSTANCE = new SprintHudConfig();

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try (Reader r = Files.newBufferedReader(FILE)) {
            SprintHudConfig loaded = GSON.fromJson(r, SprintHudConfig.class);
            if (loaded != null) INSTANCE = loaded;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (Writer w = Files.newBufferedWriter(FILE)) {
            GSON.toJson(INSTANCE, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
