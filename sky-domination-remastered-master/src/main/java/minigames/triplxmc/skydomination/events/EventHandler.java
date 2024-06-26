package minigames.triplxmc.skydomination.events;

import minigames.triplxmc.skydomination.Core;
import org.bukkit.plugin.PluginManager;

public class EventHandler {

    public EventHandler(Core core) {
        PluginManager pm = core.getServer().getPluginManager();
        pm.registerEvents(new MenuEvents(), core);
        pm.registerEvents(new PlayerJoinQuit(), core);
        pm.registerEvents(new HandSlotChange(), core);
        pm.registerEvents(new PlayerInteract(), core);
        pm.registerEvents(new PlayerDamage(), core);
        pm.registerEvents(new PlayerDeath(), core);
        pm.registerEvents(new FoodLevelChange(), core);
        pm.registerEvents(new PlayerMove(), core);
        pm.registerEvents(new NPCClick(), core);
        pm.registerEvents(new ItemDrop(), core);
        pm.registerEvents(new BlockBreak(), core);
        pm.registerEvents(new PlayerChat(), core);
    }

}
