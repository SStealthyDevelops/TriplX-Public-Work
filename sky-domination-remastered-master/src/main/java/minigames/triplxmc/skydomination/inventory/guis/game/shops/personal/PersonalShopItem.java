package minigames.triplxmc.skydomination.inventory.guis.game.shops.personal;

import lombok.Getter;
import minigames.triplxmc.skydomination.inventory.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import triplx.core.api.chat.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static minigames.triplxmc.skydomination.inventory.guis.game.shops.personal.PersonalShopItem.ItemCategory.*;

@Getter
public enum PersonalShopItem {

    // NULL ITEM
    NULLABLE(UTILITY, new Item(Material.CAKE, Color.cc("&cNULL OBJECT")).addLore("").addLore("&cIf you see this, there").addLore("&cwas an internal error."), 0, 0),

    // SWORDS
    DIAMOND_SWORD(COMBAT, new Item(Material.DIAMOND_SWORD, Color.cc("&fDiamond Sword")), 1, 950),
    IRON_SWORD(COMBAT, new Item(Material.IRON_SWORD, Color.cc("&fIron Sword")), 2, 550),
    STONE_SWORD(COMBAT, new Item(Material.STONE_SWORD, Color.cc("&fStone Sword")), 3, 150),


    // ARMOR
    CHAINMAIL_ARMOR(ARMOR, new Item(Material.CHAINMAIL_CHESTPLATE, Color.cc("&fChainmail Armor")), 4, 175),
    IRON_ARMOR(ARMOR, new Item(Material.IRON_CHESTPLATE, Color.cc("&fIron Armor")), 5, 250),
    DIAMOND_ARMOR(ARMOR, new Item(Material.DIAMOND_CHESTPLATE, Color.cc("&fDiamond Armor")), 6, 500),

    //RANGED COMBAT
    BASE_BOW(RANGED_COMBAT, new Item(Material.BOW, Color.cc("&fBow")), 7, 95),
    STRONG_BOW(RANGED_COMBAT, new Item(Material.BOW, Color.cc("&fPower Bow")).addEnchantment(Enchantment.ARROW_DAMAGE, 1), 8, 150),
    ARROWS(RANGED_COMBAT, new Item(Material.ARROW, Color.cc("&fArrows")), 9, 45),

    // UTILITY
    ENDERPEARL(UTILITY, new Item(Material.ENDER_PEARL ,Color.cc("&dEnderpearl")), 10, 750),
    GOLD_APPLE(UTILITY, new Item(Material.GOLDEN_APPLE, Color.cc("&6Golden Apple")), 11, 450),
                // potions
    SPEED_POTION(UTILITY, new Item.Potion(new PotionEffect(PotionEffectType.SPEED, 25, 1, false, true)), 12, 350),
    INVISIBILITY_POTION(UTILITY, new Item.Potion(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 1, false, false)), 13, 350),

    // utilities
    FIREBALL(UTILITY, new Item(Material.FIREBALL, "&6Fireball"), 14, 200),
    TNT(UTILITY, new Item(Material.TNT, "&cTNT"), 15, 250),

    // tools
    IRON_PICKAXE(UTILITY, new Item(Material.IRON_PICKAXE, "&7Iron Pickaxe"), 16, 250),
    IRON_AXE(UTILITY, new Item(Material.IRON_AXE, "&7Iron Axe"), 17, 250),

    // ABILITIES
    ULTIMATE_ACCELERANT(HERO_ABILITIES, new Item(Material.NETHER_STAR, "&bUltimate Accelerant")
            .addLore("").addLore("&aAdd 15% to your").addLore("&aultimate ability progression."), 18, 350),

    ABILITY_STIM(HERO_ABILITIES, new Item(Material.EMERALD, "&aTactical Stim")
            .addLore("").addLore("&aGet your tactical back 12%").addLore("&afaster for one life."), 19, 750),

    // BLOCKS

    // wool, endstone, wood

    WOOL(BLOCKS, new Item(Material.WOOL, "&fWool", 16), 20, 25),
    ENDSTONE(BLOCKS, new Item(Material.ENDER_STONE, "&aEndstone", 16), 21, 55),
    WOOD(BLOCKS, new Item(Material.WOOD, "&eWood", 16), 22, 40)



    ;


    private final Item icon;
    private final int id;
    private final ItemCategory category;
    private final int price;

    public final Map<ItemCategory, List<PersonalShopItem>> itemTypes = new HashMap<>();

    PersonalShopItem(ItemCategory category, Item icon, int id, int price) {
        this.icon = icon;
        this.id = id;
        this.category = category;
        itemTypes.computeIfAbsent(category, k -> new ArrayList<>());
        itemTypes.get(category).add(this);
        this.price = price;
    }

    enum ItemCategory {
        ARMOR("Armor", new Item(Material.DIAMOND_CHESTPLATE, Color.cc("&7&lArmor"))),
        COMBAT("Combat", new Item(Material.DIAMOND_SWORD, Color.cc("&7&lCombat"))),
        RANGED_COMBAT("Ranged Combat", new Item(Material.BOW, Color.cc("&7&lRanged Combat"))),
        UTILITY("Utility", new Item(Material.LAVA_BUCKET, Color.cc("&7&lUtility"))),
        HERO_ABILITIES("Hero Abilities", new Item(Material.NETHER_STAR, Color.cc("&7&lHero Abilities"))),
        BLOCKS("Blocks", new Item(Material.WOOL, 14, Color.cc("&7&lBlocks")));

        String name;
        Item icon;
        ItemCategory(String name, Item icon) {
            this.name = name;
            this.icon = icon;
        }
    }

    public static PersonalShopItem getItem(int id) {
        for (PersonalShopItem item : values()) {
            if (item.getId() == id) return item;
        }
        Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not find item " + id));
        return NULLABLE;
    }

}
