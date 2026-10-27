package com.tfoe.tfoeutil.modulemanager;

import com.tfoe.tfoeutil.Addib;
import net.anubis.client.module.ClientModule;

public final class ModuleManager extends ClientModule {
    public CreateModules() {
        super("EasyTP", "Adds a keybind to /RTP or /HOME", Addon.CATEGORY);
    }
}
