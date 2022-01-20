package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class FOTSoundEvents
{
    public static final SoundEvent SPLASHTAIL_DEATH = create("entity.splashtail.death");
    public static final SoundEvent SPLASHTAIL_FLOP = create("entity.splashtail.flop");
    public static final SoundEvent SPLASHTAIL_HURT = create("entity.splashtail.hurt");
    public static final SoundEvent PONDIE_DEATH = create("entity.pondie.death");
    public static final SoundEvent PONDIE_FLOP = create("entity.pondie.flop");
    public static final SoundEvent PONDIE_HURT = create("entity.pondie.hurt");
    public static final SoundEvent ISLEHOPPER_DEATH = create("entity.islehopper.death");
    public static final SoundEvent ISLEHOPPER_FLOP = create("entity.islehopper.flop");
    public static final SoundEvent ISLEHOPPER_HURT = create("entity.islehopper.hurt");
    public static final SoundEvent ANCIENTSCALE_DEATH = create("entity.ancientscale.death");
    public static final SoundEvent ANCIENTSCALE_FLOP = create("entity.ancientscale.flop");
    public static final SoundEvent ANCIENTSCALE_HURT = create("entity.ancientscale.hurt");
    public static final SoundEvent PLENTIFIN_DEATH = create("entity.plentifin.death");
    public static final SoundEvent PLENTIFIN_FLOP = create("entity.plentifin.flop");
    public static final SoundEvent PLENTIFIN_HURT = create("entity.plentifin.hurt");

    public static void init()
    {
        register(SPLASHTAIL_DEATH);
        register(SPLASHTAIL_FLOP);
        register(SPLASHTAIL_HURT);
        register(PONDIE_DEATH);
        register(PONDIE_FLOP);
        register(PONDIE_HURT);
        register(ISLEHOPPER_DEATH);
        register(ISLEHOPPER_FLOP);
        register(ISLEHOPPER_HURT);
        register(ANCIENTSCALE_DEATH);
        register(ANCIENTSCALE_FLOP);
        register(ANCIENTSCALE_HURT);
        register(PLENTIFIN_DEATH);
        register(PLENTIFIN_FLOP);
        register(PLENTIFIN_HURT);
    }

    private static SoundEvent create(String key)
    {
        return new SoundEvent(new ResourceLocation(FishOfThieves.MOD_ID, key));
    }

    private static SoundEvent register(SoundEvent soundEvent)
    {
        return Registry.register(Registry.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }
}