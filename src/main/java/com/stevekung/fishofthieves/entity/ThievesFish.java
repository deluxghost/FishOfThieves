package com.stevekung.fishofthieves.entity;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public interface ThievesFish
{
    String VARIANT_TAG = "Variant";
    String TROPHY_TAG = "Trophy";
    String NAME_TAG = "Name";
    net.minecraft.tags.Tag.Named<EntityType<?>> THIEVES_FISH = TagFactory.ENTITY_TYPE.create(new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));

    FishVariant getVariant();
    FishVariant getVariant(CompoundTag compound);
    void setVariant(int id);
    boolean isTrophy();
    void setTrophy(boolean trophy);

    default void saveToBucket(ItemStack itemStack, int variant, String name)
    {
        var compound = itemStack.getOrCreateTag();
        compound.putInt(VARIANT_TAG, variant);
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putString(NAME_TAG, name);
    }

    default void loadFromBucket(int variant, CompoundTag compound)
    {
        this.setVariant(variant);
        this.setTrophy(compound.getBoolean(TROPHY_TAG));
    }

    default SpawnGroupData defaultFinalizeSpawn(LivingEntity livingEntity, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag, Enum<? extends FishVariant> variant)
    {
        if (reason == MobSpawnType.BUCKET)
        {
            if (dataTag != null && dataTag.contains(VARIANT_TAG, Tag.TAG_INT))
            {
                this.setVariant(dataTag.getInt(VARIANT_TAG));
                this.setTrophy(dataTag.getBoolean(TROPHY_TAG));
            }
            return spawnData;
        }
        if (livingEntity.getRandom().nextInt(15) == 0)
        {
            this.setTrophy(true);
            livingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5.0D);
            livingEntity.setHealth(5.0f);
        }
        this.setVariant(variant.ordinal());
        return spawnData;
    }

    @FunctionalInterface
    interface Condition
    {
        boolean spawn(SpawnConditionContext context);
    }

    interface FishVariant
    {
        String getName();
        int getId();
    }

    record SpawnConditionContext(ServerLevel level, BlockPos blockPos) {}
}