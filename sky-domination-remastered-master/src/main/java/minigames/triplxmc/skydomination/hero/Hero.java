package minigames.triplxmc.skydomination.hero;

import lombok.Getter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.game.GameState;
import minigames.triplxmc.skydomination.hero.heros.offense.OverkillHero;
import minigames.triplxmc.skydomination.inventory.Item;
import minigames.triplxmc.skydomination.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Random;

@Getter
public class Hero {


    private final String key;

    private final int tacticalCooldown;
    private final int ultimateCooldown;

    private final int ultimateUseTime = 0;
    private final int tacticalUseTime = 0;

    private boolean tacticalAvailable;
    private boolean ultimateAvailable;

    private long lastTactical;
    private long lastUltimate;

    private final String name;
    private final String tacticalName;
    private final String ultimateName;

    private boolean tacticalActive;
    private boolean ultimateActive;

    private final Item tacticalItem;
    private final Item ultimateItem;

    private final ItemStack helmet;
    private final int armorColor;

    private final Profile profile;

    public Hero(String key, String name, String tacticalName, String ultimateName, int tacticalCooldown, int ultimateCooldown, Item tacticalItem, Item ultimateItem, ItemStack helmet, int armorColor, Profile profile) {
        this.profile = profile;
        this.key = key;
        this.tacticalCooldown = tacticalCooldown;
        this.ultimateCooldown = ultimateCooldown;

        this.name = name;

        this.tacticalName = tacticalName;
        this.ultimateName = ultimateName;

        this.ultimateItem = ultimateItem;
        this.tacticalItem = tacticalItem;

        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> { // ultimate re-earned
            ItemStack ultimate = getUltimateItem().getStack();
            ItemMeta meta = ultimate.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ultimate.setItemMeta(meta);
            profile.getPlayer().getInventory().setItem(8, ultimate);

            this.ultimateAvailable = true;
            this.ultimateActive = false;
        }, ultimateCooldown * 20); // from seconds to ticks

        this.ultimateAvailable = false;
        this.tacticalActive = true;

        this.armorColor = armorColor;
        this.helmet = helmet;
    }

    public boolean useTactical(final Profile profile) {
        if (tacticalAvailable && profile.getGame().getState() == GameState.ACTIVE) {
            lastTactical = System.currentTimeMillis();
            tacticalAvailable = false;
            profile.getPlayer().getInventory().setItem(7, getTacticalItem().getStack());


            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {

                ItemStack tactical = getTacticalItem().getStack();
                ItemMeta meta = tactical.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                tactical.setItemMeta(meta);

                tacticalAvailable = true;
                tacticalActive = false;
                tactical();
            }, tacticalCooldown * 20);
            return true;
        } else return false;
    }

    public void useUltimate(final Profile profile) {
        if (ultimateAvailable && profile.getGame().getState() == GameState.ACTIVE) {
            this.lastUltimate = System.currentTimeMillis();
            this.ultimateAvailable = false;
            this.ultimateActive = true;
            this.ultimate();
            profile.getPlayer().getInventory().setItem(8, getUltimateItem().getStack());
            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> { // ultimate re-earned
                ItemStack ultimate = getUltimateItem().getStack();
                ItemMeta meta = ultimate.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                ultimate.setItemMeta(meta);

                profile.getPlayer().getInventory().setItem(8, ultimate);

                this.ultimateAvailable = true;
                this.ultimateActive = false;
            }, ultimateCooldown * 20); // from seconds to ticks
        }
    }

    public void ultimate() {
    }

    public void tactical() {
    }

    @SuppressWarnings("unused")
    public void kill(Profile target) {

    }

    public void initializeInventory(Profile profile) {
        profile.getPlayer().getInventory().clear();
        profile.getPlayer().getInventory().setArmorContents(null);
        profile.getPlayer().getInventory().setHelmet(helmet);

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
        chestMeta.setColor(Color.fromRGB(armorColor));
        chestMeta.setDisplayName(triplx.core.api.chat.Color.cc(this.getName() + " torso"));
        chestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        chestMeta.spigot().setUnbreakable(true);
        chest.setItemMeta(chestMeta);

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta legsMeta = (LeatherArmorMeta) chest.getItemMeta();
        legsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        legsMeta.spigot().setUnbreakable(true);
        legsMeta.setColor(Color.fromRGB(armorColor));
        legsMeta.setDisplayName(triplx.core.api.chat.Color.cc(this.getName() + " pants"));
        legs.setItemMeta(legsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) chest.getItemMeta();
        bootsMeta.spigot().setUnbreakable(true);
        bootsMeta.setColor(Color.fromRGB(armorColor));
        bootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bootsMeta.setDisplayName(triplx.core.api.chat.Color.cc(this.getName() + " boots"));
        boots.setItemMeta(bootsMeta);




        profile.getPlayer().getInventory().setChestplate(chest);
        profile.getPlayer().getInventory().setBoots(boots);
        profile.getPlayer().getInventory().setLeggings(legs);

        profile.getPlayer().getInventory().setItem(7, getTacticalItem().getStack());
        profile.getPlayer().getInventory().setItem(8, getUltimateItem().getStack());
    }

    public static Hero randomHero(Profile p) {
        Hero[] heros = new Hero[]{new OverkillHero(p)};
        Random random = new Random();
        //noinspection all
        int i = random.nextInt(heros.length);
        return heros[i];
    }


}
