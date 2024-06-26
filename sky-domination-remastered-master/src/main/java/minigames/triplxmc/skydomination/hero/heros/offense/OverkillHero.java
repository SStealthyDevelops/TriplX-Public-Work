package minigames.triplxmc.skydomination.hero.heros.offense;

import lombok.Getter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.game.GameState;
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

@Getter
public class OverkillHero extends Hero {

    private final Profile profile;

    private final static ItemStack helmet = SkullCreator.itemFromBase64(Base64Strings.overkillHead);
    private final static ItemMeta meta = helmet.getItemMeta();

    static {
        meta.setDisplayName(Color.cc("&c&lOverkill"));
        helmet.setItemMeta(meta);
    }

    public OverkillHero(Profile profile) {

        super("overkill","&c&lOverkill", "&bPhasewalk", "&5Frenzy", 10, 25,
                new Item(Material.SUGAR, Color.cc("&bPhasewalk")).addLore("").addLore("&7Become invincible for").addLore("&7a short period of time"),
                new Item(Material.DIAMOND_SWORD, "&5Frenzy").addLore("").addLore("&7Become an unstoppable ").addLore("&7killing machine."),
                helmet,
                0,
                profile
        );




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
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 180, 0, true, false);
        profile.getPlayer().addPotionEffect(speed);

        PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, 180, 0, true, false);
        profile.getPlayer().addPotionEffect(haste);

        PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 180, 0, true, false);
        profile.getPlayer().addPotionEffect(strength);

        if (profile.getGame().getState() == GameState.ACTIVE)
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> profile.getPlayer().getInventory().setItem(8, getUltimateItem().getStack()), 180L);

    }

    @Override
    public void kill(Profile target) {
        if (isUltimateActive()) {
            PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, true, false);
            getProfile().getPlayer().addPotionEffect(strength);

            ItemStack ultimate = getUltimateItem().getStack();
            ItemMeta meta = ultimate.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ultimate.setItemMeta(meta);

            getProfile().getPlayer().getInventory().setItem(8, ultimate);
        }
    }

    /*
    Ulimate: Frenzy
    Get boosted speed, haste mining, get Strength I for 25 seconds, and get Strength II after every kill for 3 seconds
    Add red particle trail/aura to player
     */

    /*
    Tactical: Wraith phase
    Become invincible, get speed 1, particle trail
     */


}
