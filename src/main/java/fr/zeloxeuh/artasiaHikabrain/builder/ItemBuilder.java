package fr.zeloxeuh.artasiaHikabrain.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    /**
     * Crée un nouvel ItemBuilder avec le matériau spécifié
     * @param material Le matériau de l'item
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Crée un nouvel ItemBuilder avec le matériau et la quantité spécifiés
     * @param material Le matériau de l'item
     * @param amount La quantité d'items
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
    }

    /**
     * Définit le nom de l'item
     * @param name Le nom à donner à l'item
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Définit le lore (description) de l'item
     * @param lore Liste des lignes du lore
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Définit le lore (description) de l'item
     * @param lore Liste des lignes du lore
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    /**
     * Ajoute une ligne au lore existant
     * @param line La ligne à ajouter
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder addLoreLine(String line) {
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(line);
        meta.setLore(lore);
        return this;
    }

    /**
     * Ajoute un enchantement à l'item
     * @param enchant L'enchantement à ajouter
     * @param level Le niveau de l'enchantement
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        meta.addEnchant(enchant, level, true);
        return this;
    }

    /**
     * Ajoute un effet de brillance sans enchantement visible
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setGlow() {
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Cache certains attributs de l'item
     * @param flags Les flags à cacher
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder hideFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Définit l'item comme incassable
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setUnbreakable() {
        meta.setUnbreakable(true);
        return this;
    }

    /**
     * Définit le propriétaire d'une tête de joueur
     * @param owner Le nom du propriétaire
     * @return L'instance de ItemBuilder
     */
    public ItemBuilder setSkullOwner(String owner) {
        if (meta instanceof SkullMeta) {
            ((SkullMeta) meta).setOwner(owner);
        }
        return this;
    }

    /**
     * Construit l'item final
     * @return L'ItemStack créé
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}