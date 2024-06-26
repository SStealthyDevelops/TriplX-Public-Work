package minigames.triplxmc.skydomination.hero.heros.tactical;

import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.hero.Hero;
import minigames.triplxmc.skydomination.inventory.Item;
import minigames.triplxmc.skydomination.nms.SkullCreator;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.utils.Base64Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import triplx.core.api.chat.Color;

public class VectorHero extends Hero {

    private final Profile profile;


    private final static ItemStack helmet = SkullCreator.itemFromBase64(Base64Strings.vectorHead);
    private final static ItemMeta meta = helmet.getItemMeta();

    static {
        meta.setDisplayName(Color.cc("&6&lVector"));
        helmet.setItemMeta(meta);
    }

    public VectorHero(Profile profile) {
        //tactical: 2 second speed boost
        // ultiamte: EITHER frog abilities (jump & speed) or a short distance TP (like Valorant://OMEN tactical)
        super("vector", "&6&lVector", "&bKinetic Discharge", "&cULTIMATE NAME", 30, 120,
                new Item(Material.SUGAR, "&bKinetic Discharge"),
                new Item(Material.BOOK, "&cULTIMATE NAME"),
                helmet, 0, profile);

        this.profile = profile;
    }

    @Override
    public void useUltimate(Profile profile) {
        super.useUltimate(profile);
    }

    @Override
    public void tactical() {

    }

    @Override
    public void ultimate() {

        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> profile.getPlayer().getInventory().setItem(8, getUltimateItem().getStack()), 180L);
    }

    @Override
    public void kill(Profile target) {

    }


}
