package com.tfoe.tfoeutil.addon;

import net.anubis.client.addon.AnubisAddon;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.ModuleRegistry;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;

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
        Reflections reflections =
            new Reflections("com.tfoe.tfoeutil.modulemanager");

        for (Class<? extends ClientModule> clazz :
                reflections.getSubTypesOf(ClientModule.class)) {

            if (Modifier.isAbstract(clazz.getModifiers())
                    || clazz.isInterface()) {
                continue;
            }

            try {
                ClientModule module =
                    clazz.getDeclaredConstructor().newInstance();

                ModuleRegistry.add(module);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(
                    "Could not load module: " + clazz.getName(), e
                );
            }
        }
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
