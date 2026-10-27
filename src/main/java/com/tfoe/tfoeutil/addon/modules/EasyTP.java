
package com.tfoe.tfoeutil.addon.modules;

import com.tfoe.tfoeutil.addon.Addon;
import net.anubis.client.event.Stage;
import net.anubis.client.event.EventPriority;
import net.anubis.client.event.impl.KeyInputEvent;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.setting.KeySetting;
import net.anubis.client.module.setting.NumberSetting;
import net.anubis.client.input.ModuleKeybinds;
import net.minecraft.client.Minecraft;

public final class EasyTP extends ClientModule {

    private final KeySetting homeKey =
            this.addSetting(
                    "home-key",
                    new KeySetting("Home Keybind", -1)
            );

    private final KeySetting rtpKey =
            this.addSetting(
                    "rtp-key",
                    new KeySetting("RTP Keybind", -1)
            );

    private final NumberSetting homeNumber =
            this.addSetting(
                    "home-number",
                    new NumberSetting("Home Number", 1.0F, 1.0F, 10.0F)
                            .step(1.0F)
            );

    public EasyTP() {
        super(
                "easytp",
                "EasyTP",
                "Adds two keybinds for /home and /rtp.",
                Addon.CATEGORY
        );
    }

    @Override
    protected void onEnable() {
        this.on(
                KeyInputEvent.TYPE,
                Stage.PRE,
                EventPriority.HIGH,
                this::onKey
        );
    }

    private void onKey(KeyInputEvent event) {
        if (event.action != 1) {
            return;
        }

        int key = event.key;

        if (key < 0 || !ModuleKeybinds.isBindable(key)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.screen != null || mc.player == null) {
            return;
        }

        if (key == this.homeKey.value()) {
            sendCommand("home " + Math.round(this.homeNumber.value()[0]));
        }

        if (key == this.rtpKey.value()) {
            sendCommand("rtp");
        }
    }

    private void sendCommand(String command) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.getConnection() != null) {
            mc.getConnection().sendCommand(command);
        }
    }
}
