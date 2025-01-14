package com.stevekung.fishofthieves.item;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTTags;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class FOTMobBucketItem extends MobBucketItem
{
    private final EntityType<?> entityType;
    private final Consumer<Int2ObjectOpenHashMap<String>> dataFixMap;

    public FOTMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, Consumer<Int2ObjectOpenHashMap<String>> dataFixMap, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.entityType = entityType;
        this.dataFixMap = dataFixMap;
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag compoundTag)
    {
        super.verifyTagAfterLoad(compoundTag);
        ThievesFish.fixData(compoundTag, this.dataFixMap);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        var compoundTag = itemStack.getTag();

        if (this.entityType.is(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE))
        {
            if (itemStack.hasTag())
            {
                if (compoundTag.contains(ThievesFish.VARIANT_TAG, Tag.TAG_STRING))
                {
                    var type = this.createTooltip(compoundTag.getString(ThievesFish.VARIANT_TAG));

                    if (compoundTag.getBoolean(ThievesFish.TROPHY_TAG))
                    {
                        type.append(", ").append(Component.translatable("entity.fishofthieves.trophy"));
                    }
                    tooltipComponents.add(type);
                }
            }
            else if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
            {
                var variantMap = Util.make(new Int2ObjectOpenHashMap<>(), this.dataFixMap);
                tooltipComponents.add(this.createTooltip(variantMap.get(0)));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items)
    {
        super.fillItemCategory(tab, items);

        if (this.allowedIn(tab) && FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            for (var i = 1; i < 5; i++)
            {
                items.add(create(this, this.dataFixMap, i));
            }
        }
    }

    private MutableComponent createTooltip(String location)
    {
        return Component.translatable("entity.fishofthieves.%s.%s".formatted(Registry.ENTITY_TYPE.getKey(this.entityType).getPath(), ResourceLocation.tryParse(location).getPath())).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    private static ItemStack create(Item item, Consumer<Int2ObjectOpenHashMap<String>> dataFixMap, int index)
    {
        var itemStack = new ItemStack(item);
        var compoundTag = itemStack.getOrCreateTag();
        var variant = Util.make(new Int2ObjectOpenHashMap<>(), dataFixMap);
        compoundTag.putInt("CustomModelData", index);
        compoundTag.putString(ThievesFish.VARIANT_TAG, variant.get(index));
        return itemStack;
    }
}