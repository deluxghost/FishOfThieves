package com.stevekung.fishofthieves.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;

public interface ThievesFish<T extends FishData> extends GlowFish, PartyFish
{
    String OLD_VARIANT_TAG = "Variant";
    String OLD_NAME_TAG = "Name";

    String VARIANT_TAG = "variant";
    String TROPHY_TAG = "Trophy";

    T getVariant();

    void setVariant(T variant);

    Holder<T> getSpawnVariant(boolean fromBucket);

    Registry<T> getRegistry();

    Consumer<Int2ObjectOpenHashMap<String>> getDataFix();

    boolean isTrophy();

    void setTrophy(boolean trophy);

    default void saveToBucket(CompoundTag compound)
    {
        var variant = this.getRegistry().getKey(this.getVariant());
        compound.putString(VARIANT_TAG, variant.toString());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
    }

    default void loadFromBucket(CompoundTag compound)
    {
        ThievesFish.fixData(compound, this.getDataFix());

        if (compound.contains(VARIANT_TAG))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(compound.getString(VARIANT_TAG)));

            if (variant != null)
            {
                this.setVariant(variant);
            }
        }
        if (compound.contains(TROPHY_TAG))
        {
            this.setTrophy(compound.getBoolean(TROPHY_TAG));
        }
    }

    default SpawnGroupData defaultFinalizeSpawn(LivingEntity livingEntity, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_STRING))
        {
            var variant = this.getRegistry().get(ResourceLocation.tryParse(dataTag.getString(VARIANT_TAG)));

            if (variant != null)
            {
                this.setVariant(variant);
            }

            this.setTrophy(dataTag.getBoolean(TROPHY_TAG));
            return spawnData;
        }
        if (livingEntity.getRandom().nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability)
        {
            this.setTrophy(true);
            livingEntity.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        this.setVariant(this.getSpawnVariant(reason == MobSpawnType.BUCKET).value());
        return spawnData;
    }

    default Holder<T> getSpawnVariant(LivingEntity livingEntity, TagKey<T> tagKey, T defaultSpawn, boolean fromBucket)
    {
        return this.getRegistry().getTag(tagKey).flatMap(named -> named.getRandomElement(livingEntity.getRandom())).filter(variant -> fromBucket || variant.value().getCondition().test(SpawnSelectors.get(livingEntity))).orElseGet(() -> Holder.direct(defaultSpawn));
    }

    static void fixData(CompoundTag compound, Consumer<Int2ObjectOpenHashMap<String>> consumer)
    {
        if (compound.contains(OLD_VARIANT_TAG, Tag.TAG_INT))
        {
            int variant = compound.getInt(OLD_VARIANT_TAG);
            var oldMap = Util.make(new Int2ObjectOpenHashMap<>(), consumer);
            compound.remove(OLD_VARIANT_TAG);
            compound.putString(VARIANT_TAG, oldMap.get(variant));
        }
        if (compound.contains(OLD_NAME_TAG))
        {
            compound.remove(OLD_NAME_TAG);
        }
    }
}