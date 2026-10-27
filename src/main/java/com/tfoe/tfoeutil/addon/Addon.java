
package com.tfoe.tfoeutil.addon.Addon;

import com.tfoe.tfoeutil.addon.modules.ModuleManager;
import net.anubis.client.addon.AnubisAddon;
import net.anubis.client.module.ModuleCategory;

public final class Addon extends AnubisAddon {

    public static ModuleCategory CATEGORY;

    @Override
    public void onRegisterCategories() {
        CATEGORY = ModuleCategory.register(
            "TFOEUTIL",
            "TFOE's Utilitys"
        );
    }

    @Override
    public void onInitialize() {
        ModuleManager.registerAll();
    }

    @Override
    public String getPackage() {
        return "com.tfoe.tfoeutil";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo(
            "tfoethetfoe",
            "tfoe-utilitys-anubis"
        );
    }
}
