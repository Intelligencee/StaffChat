package me.intelligence.staffchat;

import me.intelligence.intelapi.IntelPlugin;
import me.intelligence.staffchat.commands.StaffChatCommand;

public class StaffChat extends IntelPlugin {

    private static StaffChat instance;

    public void onEnable() {
        StaffChat.instance = this;
        new StaffChatCommand().register((IntelPlugin)this);
    }

    public void onDisable() {
        super.onDisable();
    }

    public static String format(final int i) {
        return format(i + "");
    }

    public static String format(final String s) {
        String r = "";
        for (int j = s.length() - 1; j >= 0; --j) {
            r = s.charAt(j) + r;
            if ((s.length() - j) % 3 == 0) {
                r = "," + r;
            }
        }
        if (r.startsWith(",")) {
            return r.substring(1);
        }
        return r;
    }

    public static StaffChat getInstance() {
        return StaffChat.instance;
    }
}

