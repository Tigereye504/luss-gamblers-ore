package net.tigereye.hellishmaterials.registration;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.tigereye.hellishmaterials.blocks.BatetOre;
import net.tigereye.hellishmaterials.blocks.LussOre;
import net.tigereye.hellishmaterials.blocks.VuldOre;
import net.tigereye.hellishmaterials.events.BlockDropStacksCallback;
import net.tigereye.hellishmaterials.events.LivingEntityDropLootCallback;
import net.tigereye.hellishmaterials.mechanics.LussLuck;

//registers listeners for events
//this includes ore generation, as it turns out
public class HM_Listeners {
    public static void registerListeners(){
        Registry.BIOME.forEach(LussOre::SpawnLussInBiome);
        Registry.BIOME.forEach(VuldOre::SpawnVuldInBiome);
        Registry.BIOME.forEach(BatetOre::SpawnBatetInBiome);
        
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> LussOre.SpawnLussInBiome(biome));
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> VuldOre.SpawnVuldInBiome(biome));
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> BatetOre.SpawnBatetInBiome(biome));

        BlockDropStacksCallback.EVENT.register((state, world, pos, blockEntity, entity, stack, stacksToDrop) -> {
            if (entity instanceof PlayerEntity) {
                if(stack.getItem().isIn(HM_Items.TAG_LUSS))
                {
                    stacksToDrop = LussLuck.ToolListItemStackRandomizer(stacksToDrop, stack, ((PlayerEntity)entity).getLuck());
                }
                else if(stack.getItem().isIn(HM_Items.TAG_VULD))
                {
                    stacksToDrop.clear();
                }
            }
            return TypedActionResult.pass(stacksToDrop);
        });
        
        LivingEntityDropLootCallback.EVENT.register((source, causedByPlayer, loot) -> {
            if (source.getAttacker() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) source.getAttacker();
                ItemStack stack = player.getStackInHand(player.getActiveHand());
                if(stack.getItem().isIn(HM_Items.TAG_LUSS))
                {
                    loot = LussLuck.ToolListItemStackRandomizer(loot, stack, player.getLuck());
                }
                else if(stack.getItem().isIn(HM_Items.TAG_VULD))
                {
                    loot.clear();
                }
            }
            return TypedActionResult.pass(loot);
        });
    }
}