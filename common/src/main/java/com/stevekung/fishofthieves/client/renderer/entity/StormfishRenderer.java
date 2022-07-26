package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.StormfishModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Stormfish;
import com.stevekung.fishofthieves.registry.variants.StormfishVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class StormfishRenderer extends ThievesFishRenderer<StormfishVariant, Stormfish, StormfishModel<Stormfish>>
{
    public StormfishRenderer(EntityRendererProvider.Context context)
    {
        super(context, new StormfishModel<>(context.bakeLayer(StormfishModel.LAYER)));
    }

    @Override
    protected void setupRotations(Stormfish stormfish, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(stormfish, poseStack, ageInTicks, rotationYaw, partialTicks);
        var bodyRotBase = 1.0f;
        var baseDegree = stormfish.isPartying() ? -20.0f : 5.0f;
        var bodyRotSpeed = stormfish.isPartying() ? stormfish.isInWater() ? 2.0f : 1.0f : 0.65f;

        if (!stormfish.isInWater())
        {
            bodyRotBase = 1.7f;
        }

        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!stormfish.isInWater())
        {
            poseStack.translate(0.2f, 0.1f, 0.0f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}