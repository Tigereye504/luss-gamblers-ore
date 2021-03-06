package net.tigereye.hellishmaterials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.tigereye.hellishmaterials.armor.BatetArmorMaterial;
import net.tigereye.hellishmaterials.armor.VuldArmorMaterial;
import net.tigereye.hellishmaterials.items.BatetMaterial;
import net.tigereye.hellishmaterials.items.LussMaterial;
import net.tigereye.hellishmaterials.items.VuldMaterial;
import net.tigereye.hellishmaterials.registration.HMItems;

public class Utils {

    private Utils() {}

    public static boolean isVuld(ItemStack tool) {
        if (tool == null) {
            return false;
        }
        boolean isValidMaterial = false;
        Item item = tool.getItem();
        if (item instanceof ToolItem) {
            isValidMaterial = ((ToolItem) item).getMaterial() instanceof VuldMaterial;
        } else if (item instanceof ArmorItem) {
            isValidMaterial = ((ArmorItem) item).getMaterial() instanceof VuldArmorMaterial;
        }
        return item.isIn(HMItems.TAG_VULD) || isValidMaterial;
    }

    public static boolean isBatet(ItemStack tool) {
        if (tool == null) {
            return false;
        }
        boolean isValidMaterial = false;
        Item item = tool.getItem();
        if (item instanceof ToolItem) {
            isValidMaterial = ((ToolItem) item).getMaterial() instanceof BatetMaterial;
        } else if (item instanceof ArmorItem) {
            isValidMaterial = ((ArmorItem) item).getMaterial() instanceof BatetArmorMaterial;
        }
        return item.isIn(HMItems.TAG_BATET) || isValidMaterial;
    }

    public static boolean isLuss(ItemStack tool) {
        if (tool == null) {
            return false;
        }
        boolean isValidMaterial = false;
        Item item = tool.getItem();
        if (item instanceof ToolItem) {
            isValidMaterial = ((ToolItem) item).getMaterial() instanceof LussMaterial;
        }
        return item.isIn(HMItems.TAG_LUSS) || isValidMaterial;
    }

}
