package minigames.triplxmc.skydomination.inventory.guis.game.shops.personal;

import lombok.Getter;
import minigames.triplxmc.skydomination.game.GameManager;
import minigames.triplxmc.skydomination.inventory.GUIUtils;
import minigames.triplxmc.skydomination.inventory.Item;
import minigames.triplxmc.skydomination.inventory.Menu;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import triplx.core.api.chat.Color;

import static minigames.triplxmc.skydomination.inventory.guis.game.shops.personal.PersonalShopItem.*;

public class PersonalShop implements Menu {

    @Getter
    private final Inventory inventory;

    public PersonalShop(final Profile profile) {
        final Player player = profile.getPlayer();
        GameManager.getInstance().getGame(profile.getPlayer());
        inventory = Bukkit.createInventory(this, 54, Color.cc("&aPersonal Shop"));

        // swords.. armor.. utils.. ultimate charge up.. etc

        // favorites, utils, armor, combat.. etc?
//
//        for (Map.Entry<Integer, ShopItem> favorites : profile.getFavorites().entrySet()) {
//            if (favorites.getKey() > 100 || favorites.getKey() < 0) { // in case the slot is needed for other things
//                continue;
//            }
//            inventory.setItem(favorites.getKey(), favorites.getValue().getIcon().getStack());
//        }
        // THIS IS LAST PRIORITY ^^

        Item closeShop = new Item(Material.BARRIER, Color.cc("&cClose Shop"));
        inventory.setItem(53, closeShop.getStack());

        Item armor = PersonalShopItem.ItemCategory.ARMOR.icon;
        inventory.setItem(GUIUtils.getSlot(2, 3), armor.getStack());

        Item combat = PersonalShopItem.ItemCategory.COMBAT.icon;
        inventory.setItem(GUIUtils.getSlot(2, 7), combat.getStack());

        Item rangedCombat = PersonalShopItem.ItemCategory.RANGED_COMBAT.icon;
        inventory.setItem(GUIUtils.getSlot(2, 5), rangedCombat.getStack());

        Item utility = PersonalShopItem.ItemCategory.UTILITY.icon;
        inventory.setItem(GUIUtils.getSlot(3, 7), utility.getStack());

        Item abilities = PersonalShopItem.ItemCategory.HERO_ABILITIES.icon;
        inventory.setItem(GUIUtils.getSlot(3, 3), abilities.getStack());

        Item blocks = PersonalShopItem.ItemCategory.BLOCKS.icon;
        inventory.setItem(GUIUtils.getSlot(3, 5), blocks.getStack());

        /* TODO
        Close Shop
        Each Shop Item Category ---> Each page for those categories
         */

        player.openInventory(inventory);
    }

    @Override
    public boolean onClick(Player player, int slot, ClickType type) {
        Profile profile = ProfileManager.getInstance().getProfile(player);
        if (slot == GUIUtils.getSlot(2, 7)) {
            new CombatPage(profile);
            return true;
        }

        if (slot == GUIUtils.getSlot(2, 3)) {
            new ArmorPage(profile);
            return true;
        }

        if (slot == GUIUtils.getSlot(2, 5)) {
            new RangedCombatPage(profile);
            return true;
        }

        if (slot == GUIUtils.getSlot(3, 7)) {
            new UtilityPage(profile);
            return true;
        }

        if (slot == GUIUtils.getSlot(3, 3)) {
            new AbilitiesPage(profile);
            return true;
        }

        if (slot == GUIUtils.getSlot(3, 5)) {
            new BlocksPage(profile);
            return true;
        }




        if (slot == 53) {
            player.closeInventory();
            return false;
        }

        return false;
    }

    static class CombatPage implements Menu { // TODO: Try this, make individual classes if needed later on
        @Getter
        private final Inventory inventory;

        CombatPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bCombat"));


            Item stoneSword = PersonalShopItem.STONE_SWORD.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 3), stoneSword.getStack());

            Item ironSword = PersonalShopItem.IRON_SWORD.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 5), ironSword.getStack());

            Item diamondSword = PersonalShopItem.DIAMOND_SWORD.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 7), diamondSword.getStack());




            profile.getPlayer().openInventory(inventory);
        }

        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(3, 3)) { // stone sword
                profile.buyItem(STONE_SWORD);
            } else if (slot == GUIUtils.getSlot(3, 5)) { // iron sword
                profile.buyItem(IRON_SWORD);
            } else if (slot == GUIUtils.getSlot(3, 7)) { // dia sword
                profile.buyItem(DIAMOND_SWORD);
            }
            return true;
        }
    }

    static class ArmorPage implements Menu {
        @Getter
        private final Inventory inventory;

        ArmorPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bArmor"));

            Item chainmailArmor = CHAINMAIL_ARMOR.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 3), chainmailArmor.getStack());

            Item ironArmor = IRON_ARMOR.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 5), ironArmor.getStack());

            Item diamondArmor = DIAMOND_ARMOR.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 7), diamondArmor.getStack());

            profile.getPlayer().openInventory(inventory);
        }

        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(3, 3)) {
                profile.buyItem(CHAINMAIL_ARMOR);
            } else if (slot == GUIUtils.getSlot(3, 5)) {
                profile.buyItem(IRON_ARMOR);
            } else if (slot == GUIUtils.getSlot(3, 7)) {
                profile.buyItem(DIAMOND_ARMOR);
            }

            return true;
        }
    }

    static class RangedCombatPage implements Menu {
        @Getter
        private final Inventory inventory;

        RangedCombatPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bRanged Combat"));

            Item bow = BASE_BOW.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 3), bow.getStack());

            Item strongBow = STRONG_BOW.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 5), strongBow.getStack());

            Item arrows = ARROWS.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 7), arrows.getStack());


            profile.getPlayer().openInventory(inventory);
        }

        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(3, 3)) {
                profile.buyItem(BASE_BOW);
            } else if (slot == GUIUtils.getSlot(3, 5)) {
                profile.buyItem(STRONG_BOW);
            } else if (slot == GUIUtils.getSlot(3, 7)) {
                profile.buyItem(ARROWS);
            }

            return true;
        }
    }

    static class UtilityPage implements Menu {
        @Getter
        private final Inventory inventory;

        UtilityPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bUtility"));

            Item epearl = ENDERPEARL.getIcon();
            inventory.setItem(GUIUtils.getSlot(2, 3), epearl.getStack());

            Item goldenApple = GOLD_APPLE.getIcon();
            inventory.setItem(GUIUtils.getSlot(2, 4), goldenApple.getStack());

            Item speedPot = SPEED_POTION.getIcon();
            inventory.setItem(GUIUtils.getSlot(2, 5), speedPot.getStack());

            Item invisPot = INVISIBILITY_POTION.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 5), invisPot.getStack());

            Item fireball = FIREBALL.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 3), fireball.getStack());

            Item tnt = TNT.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 4), tnt.getStack());

            Item ironPick = IRON_PICKAXE.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 6), ironPick.getStack());

            Item ironAxe = IRON_AXE.getIcon();
            inventory.setItem(GUIUtils.getSlot(2, 6), ironAxe.getStack());


            profile.getPlayer().openInventory(inventory);
        }



        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(2, 3)) {
                profile.buyItem(ENDERPEARL);
            } else if (slot == GUIUtils.getSlot(2, 4)) {
                profile.buyItem(GOLD_APPLE);
            } else if (slot == GUIUtils.getSlot(2, 5)) {
                profile.buyItem(SPEED_POTION);
            } else if (slot == GUIUtils.getSlot(3, 5)) {
                profile.buyItem(INVISIBILITY_POTION);
            } else if (slot == GUIUtils.getSlot(3 ,3)) {
                profile.buyItem(FIREBALL);
            } else if (slot == GUIUtils.getSlot(3, 4)) {
                profile.buyItem(TNT);
            } else if (slot == GUIUtils.getSlot(3, 6)) {
                profile.buyItem(IRON_PICKAXE);
            } else if (slot == GUIUtils.getSlot(2, 6)) {
                profile.buyItem(IRON_AXE);
            }

            return true;
        }
    }

    static class AbilitiesPage implements Menu {
        @Getter
        private final Inventory inventory;

        AbilitiesPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bAbilities"));

            Item ultAccl = ULTIMATE_ACCELERANT.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 4), ultAccl.getStack());

            Item tactAccl = ABILITY_STIM.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 6), tactAccl.getStack());

            profile.getPlayer().openInventory(inventory);
        }

        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);

            if (slot == GUIUtils.getSlot(3, 4)) {
                profile.buyItem(ULTIMATE_ACCELERANT);
            } else if (slot == GUIUtils.getSlot(3, 6)) {
                profile.buyItem(ABILITY_STIM);
            }

            return true;
        }
    }

    static class BlocksPage implements Menu {
        @Getter
        private final Inventory inventory;

        BlocksPage(Profile profile) {
            inventory = Bukkit.createInventory(this, 54, Color.cc("&bBlocks"));

            Item wool = WOOL.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 3), wool.getStack());

            Item endstone = ENDSTONE.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 5), endstone.getStack());

            Item wood = WOOD.getIcon();
            inventory.setItem(GUIUtils.getSlot(3, 7), wood.getStack());

            profile.getPlayer().openInventory(inventory);
        }

        @Override
        public boolean onClick(Player player, int slot, ClickType type) {
            Profile profile = ProfileManager.getInstance().getProfile(player);
            if (slot == GUIUtils.getSlot(3,3 )) {
                profile.buyItem(WOOL);
            } else if (slot == GUIUtils.getSlot(3, 5)) {
                profile.buyItem(ENDSTONE);
            } else if (slot == GUIUtils.getSlot(3, 7)) {
                profile.buyItem(WOOD);
            }

            return true;
        }
    }

}
