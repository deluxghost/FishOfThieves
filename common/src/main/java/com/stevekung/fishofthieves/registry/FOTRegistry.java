package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FOTRegistry
{
    public static final ResourceKey<Registry<SplashtailVariant>> SPLASHTAIL_VARIANT_REGISTRY = createRegistryKey("splashtail_variant");
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = Registry.registerSimple(SPLASHTAIL_VARIANT_REGISTRY, registry -> SplashtailVariant.RUBY);
    public static final ResourceKey<Registry<PondieVariant>> PONDIE_VARIANT_REGISTRY = createRegistryKey("pondie_variant");
    public static final Registry<PondieVariant> PONDIE_VARIANT = Registry.registerSimple(PONDIE_VARIANT_REGISTRY, registry -> PondieVariant.CHARCOAL);
    public static final ResourceKey<Registry<IslehopperVariant>> ISLEHOPPER_VARIANT_REGISTRY = createRegistryKey("islehopper_variant");
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = Registry.registerSimple(ISLEHOPPER_VARIANT_REGISTRY, registry -> IslehopperVariant.STONE);
    public static final ResourceKey<Registry<AncientscaleVariant>> ANCIENTSCALE_VARIANT_REGISTRY = createRegistryKey("ancientscale_variant");
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = Registry.registerSimple(ANCIENTSCALE_VARIANT_REGISTRY, registry -> AncientscaleVariant.ALMOND);
    public static final ResourceKey<Registry<PlentifinVariant>> PLENTIFIN_VARIANT_REGISTRY = createRegistryKey("plentifin_variant");
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = Registry.registerSimple(PLENTIFIN_VARIANT_REGISTRY, registry -> PlentifinVariant.OLIVE);

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String registryName)
    {
        return ResourceKey.createRegistryKey(new ResourceLocation(FishOfThieves.MOD_ID, registryName));
    }
}