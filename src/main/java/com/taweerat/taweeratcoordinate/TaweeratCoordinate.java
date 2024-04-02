package com.taweerat.taweeratcoordinate;

import org.bukkit.plugin.java.JavaPlugin;

public final class TaweeratCoordinate extends JavaPlugin {

    private static TaweeratCoordinate instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static TaweeratCoordinate getInstance() {
        return instance;
    }
}
