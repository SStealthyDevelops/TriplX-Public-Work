package minigames.triplxmc.skydomination.npc;

import lombok.Getter;
import minigames.triplxmc.skydomination.Core;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import triplx.core.api.chat.Color;

public class NPCWrapper {

    @Getter
    private final static NPCWrapper instance = new NPCWrapper();

    private final FileConfiguration config = Core.getInstance().getConfig();

    public void spawnPersonalShop(Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Color.cc(config.getString("personal-shop-name")));
        npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, "SStealthy");
        npc.spawn(location);
    }

    public void spawnTeamShop(Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, Color.cc(config.getString("team-shop-name")));
        npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, "SStealthy");
        npc.spawn(location);
    }

    

}
