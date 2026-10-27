
package com.tfoe.tfoeutil.addon.modules;

import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleRegistry;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;
import java.util.function.Supplier;

public final class ModuleManager {

    private ModuleManager() {}

    public record ModuleDefinition(
            String id,
            String name,
            String description,
            List<String> dependencies,
            Supplier<? extends ClientModule> factory
    ) {}

    /*
     * MODULE TABLE
     *
     * id           = unique module ID
     * name         = display name
     * description  = module description
     * dependencies = required Fabric mod IDs
     * factory      = creates the module
     */
    private static final List<ModuleDefinition> MODULES = List.of(
        new ModuleDefinition(
            "easytp",
            "EasyTP",
            "Adds a keybind to /RTP or /HOME",
            List.of(),
            EasyTP::new
        )

        // Add future modules here, separated by commas.
    );

    private static boolean registered;

    public static void registerAll() {
        if (registered) {
            return;
        }

        registered = true;

        for (ModuleDefinition definition : MODULES) {
            boolean missingDependency = definition.dependencies()
                .stream()
                .anyMatch(id ->
                    !FabricLoader.getInstance().isModLoaded(id)
                );

            if (missingDependency) {
                System.out.println(
                    "[TFOEUtil] Skipping " + definition.name()
                    + ": missing required dependency."
                );
                continue;
            }

            try {
                ClientModule module = definition.factory().get();
                ModuleRegistry.add(module);

                System.out.println(
                    "[TFOEUtil] Registered " + definition.name()
                );
            } catch (RuntimeException exception) {
                System.err.println(
                    "[TFOEUtil] Failed to register "
                    + definition.name()
                );
                exception.printStackTrace();
            }
        }
    }

    public static List<ModuleDefinition> getModules() {
        return MODULES;
    }
}
