package sk.m3ii0.amazingtitles.code.notifications;

import org.bukkit.entity.Player;
import sk.m3ii0.amazingtitles.code.AmazingTitles;

import java.util.*;

public class DynamicBar {

    /*
     *
     * Values
     *
     * */

    private static final Map<UUID, DynamicBar> bars = new HashMap<>();

    /*
     *
     * Builders
     *
     * */

    public static DynamicBar getBar(Player player) {
        if (bars.containsKey(player.getUniqueId())) return bars.get(player.getUniqueId());
        DynamicBar bar = new DynamicBar(player);
        bars.put(player.getUniqueId(), bar);
        return bar;
    }

    public static void removeBar(Player player) {
        bars.remove(player.getUniqueId());
    }

    public static Map<UUID, DynamicBar> getBars() {
        return bars;
    }

    /*
     *
     * Class - Values
     *
     * */

    private final Player player;
    private final LinkedHashMap<String, BarNotification> notifications = new LinkedHashMap<>();
    private final HashMap<String, BarNotification> overrides = new HashMap<>();

    /*
     *
     * Constructor
     *
     * */

    private DynamicBar(Player player) {
        this.player = player;
    }

    /*
     *
     * Methods
     *
     * */

    public Map<String, BarNotification> getNotifications() {
        return notifications;
    }

    public Map<String, BarNotification> getOverrides() {
        return overrides;
    }

    public void removeNotificationsInstantly() {
        notifications.clear();
        overrides.clear();
    }

    public void notification(String id, BarNotification notification) {
        if (notifications.containsKey(id)) {
            overrides.put(id, notification);
            return;
        }
        notifications.put(id, notification);
    }

    public void removeInstantly(String notificationId) {
        notifications.remove(notificationId);
        overrides.remove(notificationId);
    }

    public void update(long time) {
        if (notifications.isEmpty()) return;
        StringBuilder text = new StringBuilder();
        Set<String> toRemove = new HashSet<>();
        int counter = 0;
        for (Map.Entry<String, BarNotification> entry : notifications.entrySet()) {
            BarNotification notification = entry.getValue();
            if (overrides.containsKey(entry.getKey())) {
                BarNotification newOne = overrides.remove(entry.getKey());
                if (entry.getValue().isOverridable()) {
                    if (newOne != null) {
                        entry.getValue().updateBy(newOne);
                    }
                }
            }
            if (counter < notifications.size()-1) {
                notification.setHide(true);
                int secondCounter = 0;
                for (BarNotification overriding : notifications.values()) {
                    if (secondCounter == counter+1) {
                        if (overriding.isEnding()) {
                            notification.setHide(false);
                        }
                    }
                    ++secondCounter;
                }
            } else notification.setHide(false);
            if (notification.isValid(time)) {
                text.append(notification.getCurrentFrame(time)).append(" ");
            } else {
                toRemove.add(entry.getKey());
            }
            ++counter;
        }
        for (String var : toRemove) {
            notifications.remove(var);
        }
        String finalText = "";
        if (text.length() > 1) {
            finalText = text.substring(0, text.length() - 1);
        }
        Object packet = AmazingTitles.getProvider().createActionbarPacket(finalText);
        AmazingTitles.getProvider().sendActionbar(player, packet);
    }

    public void unregister() {
        bars.remove(player.getUniqueId());
    }

}
