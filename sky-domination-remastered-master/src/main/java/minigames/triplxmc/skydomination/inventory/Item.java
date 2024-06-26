package minigames.triplxmc.skydomination.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import triplx.core.api.chat.Color;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Item {

    private Material material;
    private int shortData;

    private ItemMeta meta;
    private ItemStack stack;

    private int stackSize;

    public Item(Material material, int shortData, String displayName) {
        ItemStack stack = new ItemStack(material, 1, (short) shortData);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
    }

    public Item(Material material, String displayName) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
    }
    public Item(Material material, int shortData, String displayName, int stackSize) {
        ItemStack stack = new ItemStack(material, 1, (short) shortData);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
        this.stackSize = stackSize;
        this.stack.setAmount(stackSize);

    }

    public Item(Material material, String displayName, int stackSize) {
        ItemStack stack = new ItemStack(material, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Color.cc(displayName));
        stack.setItemMeta(meta);
        this.meta = meta;
        this.stack = stack;
        this.stackSize = stackSize;

        this.stack.setAmount(stackSize);
    }



    public Item setName(String newName) {
        meta.setDisplayName(Color.cc(newName));
        stack.setItemMeta(meta);
        return this;
    }

    public Item addLore(String lore) {
        List<String> loreList = meta.getLore();
        if (loreList == null) loreList = new ArrayList<>();
        loreList.add(Color.cc(lore));
        meta.setLore(loreList);
        stack.setItemMeta(meta);
        return this;
    }

    public Item addEnchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        this.stack.setItemMeta(this.meta);
        return this;
    }



    public static class Potion extends Item {
        PotionMeta pmeta = (PotionMeta) this.getMeta();

        public Potion(PotionEffect effect) {
            super(Material.POTION, effect.getType().getName() + " Potion");
            pmeta.addCustomEffect(effect, true);
            this.getStack().setItemMeta(pmeta);
        }



    }



}
