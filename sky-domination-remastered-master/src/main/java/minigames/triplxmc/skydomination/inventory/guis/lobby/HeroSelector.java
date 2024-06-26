package minigames.triplxmc.skydomination.inventory.guis.lobby;

import lombok.Getter;
import minigames.triplxmc.skydomination.hero.Hero;
import minigames.triplxmc.skydomination.hero.heros.offense.OverkillHero;
import minigames.triplxmc.skydomination.inventory.GUIUtils;
import minigames.triplxmc.skydomination.inventory.Menu;
import minigames.triplxmc.skydomination.nms.SkullCreator;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import minigames.triplxmc.skydomination.utils.Base64Strings;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import triplx.core.api.chat.Color;

public class HeroSelector implements Menu {

    @Getter
    private final Inventory inventory;

    public HeroSelector(Player player) {
        inventory = Bukkit.createInventory(this, 54, Color.cc("&d&lHero Selection"));

        ItemStack overkill = SkullCreator.itemFromBase64(Base64Strings.overkillHead);
        ItemMeta overkillMeta = overkill.getItemMeta();
        overkillMeta.setDisplayName(Color.cc("&d&lOverkill"));
        overkill.setItemMeta(overkillMeta);

        inventory.setItem(GUIUtils.getSlot(2, 2), overkill);

        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, final int slot, ClickType type) {
        Profile profile = ProfileManager.getInstance().getProfile(player);
        Hero hero;
        if (slot == GUIUtils.getSlot(2, 2)) {
            hero = new OverkillHero(profile);
        } else return false;

        player.closeInventory();
        profile.setHero(hero);
        profile.sendMessage(Color.cc("&aSet your hero to " + hero.getName() + "&a!"));
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.3f, 1.0f);
        return false;
    }

    @Override
    public void onOpen(Player player) {

    }

    @Override
    public void onClose(Player player) {

    }
}
