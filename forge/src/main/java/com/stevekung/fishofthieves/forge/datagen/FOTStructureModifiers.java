package com.stevekung.fishofthieves.forge.datagen;

import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.core.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FOTStructureModifiers
{
    private static final Codec<TagKey<Structure>> STRUCTURE_LIST_CODEC = TagKey.hashedCodec(Registry.STRUCTURE_REGISTRY);

    public static void generateStructureModifiers(GatherDataEvent event)
    {
        var generator = event.getGenerator();
        var ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(generator, event.getExistingFileHelper(), FishOfThieves.MOD_ID, ops, ForgeRegistries.Keys.STRUCTURE_MODIFIERS, Map.of(
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_ocean_ruins"), addStructureSpawns(FOTEntities.ANCIENTSCALES.unwrap().get(0), FOTTags.ANCIENTSCALES_SPAWN_IN),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_plentifins_spawn_in"), addStructureSpawns(FOTEntities.PLENTIFINS.unwrap().get(0), FOTTags.PLENTIFINS_SPAWN_IN),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_wreckers_spawn_in"), addStructureSpawns(FOTEntities.WRECKERS.unwrap().get(0), FOTTags.WRECKERS_SPAWN_IN),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_battlegills_spawn_in"), addStructureSpawns(FOTEntities.BATTLEGILLS.unwrap().get(0), FOTTags.BATTLEGILLS_SPAWN_IN)
        )));
    }

    private static StructureModifier addStructureSpawns(MobSpawnSettings.SpawnerData spawnerData, TagKey<Structure> structureTagKey)
    {
        return new Modifier(structureTagKey, spawnerData);
    }

    public record Modifier(TagKey<Structure> structureTagKey, MobSpawnSettings.SpawnerData spawnerData) implements StructureModifier
    {
        private static final RegistryObject<Codec<? extends StructureModifier>> SERIALIZER = RegistryObject.create(FishOfThievesForge.ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL, ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

        @Override
        public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder)
        {
            if (phase == Phase.ADD && structure.is(this.structureTagKey))
            {
                builder.getStructureSettings().getOrAddSpawnOverrides(this.spawnerData.type.getCategory()).addSpawn(this.spawnerData);
            }
        }

        @Override
        public Codec<? extends StructureModifier> codec()
        {
            return SERIALIZER.get();
        }

        public static Codec<Modifier> makeCodec()
        {
            return RecordCodecBuilder.create(builder -> builder.group(
                            STRUCTURE_LIST_CODEC.fieldOf("structure").forGetter(Modifier::structureTagKey),
                            MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawnerData").forGetter(Modifier::spawnerData))
                    .apply(builder, Modifier::new));
        }
    }
}