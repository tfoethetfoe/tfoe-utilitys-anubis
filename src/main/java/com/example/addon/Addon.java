package com.example.addon;

import com.example.addon.modules.ExampleModule;
import net.anubis.client.addon.AnubisAddon;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.ModuleRegistry;

public final class ExampleAddon extends AnubisAddon {
    // clickgui category for the addon, gets set in onRegisterCategories (that runs before any module is made)
    public static ModuleCategory CATEGORY;

    @Override
    public void onRegisterCategories() {
        CATEGORY = ModuleCategory.register("TFOEUTIL", "TFOE's Utilitys");
    }

    @Override
    public void onInitialize() {
        ModuleRegistry.add(new EasyTP());
    }

    @Override
    public String getPackage() {
        return "com.tfoe.tfoeutil";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("tfoethetfoe", "tfoe-utilitys-anubis");
    }
}
