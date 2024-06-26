package triplx.hub.core.events;

import org.bukkit.plugin.PluginManager;
import triplx.hub.core.Hubcore;
import triplx.hub.core.events.features.*;

public class EventHandler {

    public EventHandler(PluginManager manager, Hubcore h) {
        manager.registerEvents(new AntiInventory(), h);
        manager.registerEvents(new PlayerJoinQuit(), h);
        manager.registerEvents(new MenuEvents(), h);
        manager.registerEvents(new AntiDamage(), h);
        manager.registerEvents(new AntiHunger(), h);
        manager.registerEvents(new PlayerSneakEvent(), h);
        manager.registerEvents(new DoubleJump(), h);
        manager.registerEvents(new Hotbar(), h);
        manager.registerEvents(new NPCInteract(), h);
        manager.registerEvents(new PlayerFreeze(), h);
        manager.registerEvents(new PlayerChat(), h);
        manager.registerEvents(new WorldEdit(), h);
        manager.registerEvents(new PlayerCommand(), h);
        manager.registerEvents(new WorldEdges(), h);
        manager.registerEvents(new PlayerVisibility(), h);
        manager.registerEvents(new PlayerSpeed(), h);
        manager.registerEvents(new WeatherChange(), h);
    }

}
