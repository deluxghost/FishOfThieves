package com.stevekung.fishofthieves.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.client.model.SplashtailModel;
import com.stevekung.fishofthieves.client.renderer.ThievesFishRenderer;
import com.stevekung.fishofthieves.entity.animal.Splashtail;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class SplashtailRenderer extends ThievesFishRenderer<SplashtailVariant, Splashtail, SplashtailModel<Splashtail>>
{
    public SplashtailRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SplashtailModel<>(context.bakeLayer(SplashtailModel.LAYER)));
    }

    @Override
    protected void setupRotations(Splashtail entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        var inWater = entity.isInWater() || entity.isNoFlip();
        var bodyRotBase = inWater ? 1.0f : 1.7f;
        var baseDegree = entity.isDancing() ? -20.0f : 4.0f;
        var bodyRotSpeed = entity.isDancing() ? inWater ? 2.0f : 1.0f : 0.6f;
        var degree = baseDegree * Mth.sin(bodyRotBase * bodyRotSpeed * ageInTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(degree));

        if (!inWater)
        {
            poseStack.translate(entity.isTrophy() ? 0.275f : 0.135f, 0.1f, -0.1f);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));
        }
    }
}