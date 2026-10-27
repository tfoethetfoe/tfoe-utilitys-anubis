package com.tfoe.tfoeutil.modulemanager;

import com.tfoe.tfoeutil.addon.Addon;
import java.util.Locale;
import net.anubis.client.chat.ModuleChat;
import net.anubis.client.event.EventPriority;
import net.anubis.client.event.Stage;
import net.anubis.client.event.impl.KeyInputEvent;
import net.anubis.client.event.impl.MouseInputEvent;
import net.anubis.client.event.impl.TickEvent;
import net.anubis.client.input.ModuleKeybinds;
import net.anubis.client.module.ClientModule;
import net.anubis.client.module.ModuleCategory;
import net.anubis.client.module.setting.KeySetting;
import net.anubis.client.module.setting.ModeSetting;
import net.anubis.client.module.setting.NumberSetting;
import net.anubis.client.module.setting.StringSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_3544;
import net.minecraft.class_408;
import net.minecraft.class_634;
import org.apache.commons.lang3.StringUtils;

public final class ModuleManager extends ClientModule {
   public static final int MAX_MACROS = 8;
   private static final String ACTION_SEND = "Send";
   private static final String ACTION_OPEN = "Open in Chat";
   private final ModeSetting slots = this.addSetting("slots", new ModeSetting("Slots", "3", slotChoices()));
   private final ModeSetting pressAction = this.addSetting("on-press", new ModeSetting("On Press", "Send", "Send", "Open in Chat"));
   private final NumberSetting cooldown = this.addSetting("cooldown", new NumberSetting("Cooldown (s)", 1.0F, 1.0F, 10.0F).step(0.5F));
   private final KeySetting[] keys = new KeySetting[8];
   private final StringSetting[] texts = new StringSetting[8];
   private int pendingSlot = -1;
   private long lastSendNanos;
   private boolean sentAny;
    public CreateModules() {
        super("EasyTP", "Adds a keybind to /RTP or /HOME", Addon.CATEGORY);
        this.usesNotifications();
        this.cooldown.visibleWhen(() -> this.pressAction.is("Send"));

      for (int i = 0; i < 8; i++) {
         int slot = i + 1;
         this.keys[i] = this.addSetting("macro-" + slot + "-key", new KeySetting("Macro " + slot + " Key", -1));
         this.texts[i] = this.addSetting("macro-" + slot + "-text", new StringSetting("Macro " + slot + " Text", ""));
         this.texts[i].privateValue();
         this.keys[i].visibleWhen(() -> slot <= this.slotCount());
         this.texts[i].visibleWhen(() -> slot <= this.slotCount());
    }
}
