package com.stevekung.fishofthieves.forge.datagen;

import java.util.Map;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class FOTBiomeModifier
{
    public static void generateBiomeModifiers(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());

        //@formatter:off
        dataGenerator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(dataGenerator, existingFileHelper, FishOfThieves.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, Map.of(
                FishOfThieves.res("add_splashtails"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, FishOfThieves.CONFIG.spawnRate.fishWeight.splashtail, 4, 8), FOTTags.Biomes.SPAWNS_SPLASHTAILS),
                FishOfThieves.res("add_pondies"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, FishOfThieves.CONFIG.spawnRate.fishWeight.pondie, 2, 4), FOTTags.Biomes.SPAWNS_PONDIES),
                FishOfThieves.res("add_islehoppers"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, FishOfThieves.CONFIG.spawnRate.fishWeight.islehopper, 2, 4), FOTTags.Biomes.SPAWNS_ISLEHOPPERS),
                FishOfThieves.res("add_ancientscales"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, FishOfThieves.CONFIG.spawnRate.fishWeight.ancientscale, 4, 8), FOTTags.Biomes.SPAWNS_ANCIENTSCALES),
                FishOfThieves.res("add_plentifins"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, FishOfThieves.CONFIG.spawnRate.fishWeight.plentifin, 4, 8), FOTTags.Biomes.SPAWNS_PLENTIFINS),
                FishOfThieves.res("add_wildsplash"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, FishOfThieves.CONFIG.spawnRate.fishWeight.wildsplash, 2, 4), FOTTags.Biomes.SPAWNS_WILDSPLASH),
                FishOfThieves.res("add_devilfish"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.devilfish, 1, 2), FOTTags.Biomes.SPAWNS_DEVILFISH),
                FishOfThieves.res("add_battlegills"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, FishOfThieves.CONFIG.spawnRate.fishWeight.battlegill, 2, 4), FOTTags.Biomes.SPAWNS_BATTLEGILLS),
                FishOfThieves.res("add_wreckers"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, FishOfThieves.CONFIG.spawnRate.fishWeight.wrecker, 4, 8), FOTTags.Biomes.SPAWNS_WRECKERS),
                FishOfThieves.res("add_stormfish"), spawn(ops, new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, FishOfThieves.CONFIG.spawnRate.fishWeight.stormfish, 4, 8), FOTTags.Biomes.SPAWNS_STORMFISH))
        ));
        //@formatter:on
    }

    private static BiomeModifier spawn(RegistryOps<JsonElement> ops, MobSpawnSettings.SpawnerData spawnerData, TagKey<Biome> tagKey)
    {
        return ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), tagKey), spawnerData);
    }
}