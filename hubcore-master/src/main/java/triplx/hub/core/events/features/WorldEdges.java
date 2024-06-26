package triplx.hub.core.events.features;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.hub.core.data.mongodb.collections.hub.HubSettings;
import triplx.hub.core.hub.HubManager;

public class WorldEdges implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (RankingManager.getRank(player).hasPermission(Rank.BUILDER)) return;

        Integer voidLimit;

        String world = e.getTo().getWorld().getName();
        String hubType = HubManager.getInstance().getHub(world).getType();

        if (hubType == null) return;

        voidLimit = HubSettings.settings(hubType).getVoidLimit();

        final Location to = e.getTo();

        if (to.getY() <= voidLimit) {
            double x, y, z, yaw, pitch;
            x = HubSettings.settings(hubType).getSpawnX();
            y = HubSettings.settings(hubType).getSpawnY();
            z = HubSettings.settings(hubType).getSpawnZ();
            yaw = HubSettings.settings(hubType).getYaw();
            pitch = HubSettings.settings(hubType).getPitch();
            player.teleport(new Location(player.getWorld(), x, y, z,(float) yaw, (float) pitch));
            return;
        }




        outsideWorld(e);
    }


    private void outsideWorld(PlayerMoveEvent e) {
        final Location to = e.getTo();


        String world = e.getTo().getWorld().getName();
        String hubType = HubManager.getInstance().getHub(world).getType();

        if (hubType == null) return;


        String[] xLimits,zLimits;
        xLimits = HubSettings.settings(hubType).getXLimits().split(" ");
        zLimits = HubSettings.settings(hubType).getZLimits().split(" ");

        int x1, x2, z1, z2;

        x1 = Integer.parseInt(xLimits[0]);
        x2 = Integer.parseInt(xLimits[1]);
        z1 = Integer.parseInt(zLimits[0]);
        z2 = Integer.parseInt(zLimits[1]);

//        System.out.println(x1 + " " + x2 + " " + z1 + " " + z2);

        if (to.getX() <= x1 || to.getX() >= x2 || to.getZ() <= z1 || to.getZ() >= z2) {
            e.setTo(e.getFrom());
            message(e.getPlayer());
        }

    }

    private void message(Player p) {
        p.sendMessage(Color.cc("&cYou are not permitted beyond this point. "));
    }

}
