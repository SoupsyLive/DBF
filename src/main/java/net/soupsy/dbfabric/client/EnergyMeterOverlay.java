package net.soupsy.dbfabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.playerStorage.PlayerStatsStorage;
import net.soupsy.dbfabric.util.IEntityDataSaver;
import net.soupsy.dbfabric.util.PlayerStorage;
import org.joml.*;

import java.lang.Math;
import java.util.UUID;

public class EnergyMeterOverlay implements HudRenderCallback {
    private static final Identifier CIRCLE_METER_OUTLINE = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-empty.png");
    private static final Identifier CIRCLE_METER_BACKGROUND = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-meter-background.png");
    private static final Identifier CIRCLE_METER_FULL = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-meter.png");

    private static final Identifier METER_1 = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-fill-5.png");
    private static final Identifier METER_2 = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-fill-4.png");
    private static final Identifier METER_3 = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-fill-3.png");
    private static final Identifier METER_3_EMPTY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-empty-3.png");
    private static final Identifier METER_4 = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-fill-2.png");
    private static final Identifier METER_4_EMPTY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-empty-2.png");
    private static final Identifier METER_5 = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-fill-1.png");
    private static final Identifier METER_5_EMPTY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/extra_meter/energy-circle-meter-empty-1.png");





    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta){
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width/2;
            y = height;
            assert client.player != null;
            if(!client.player.isCreative()){
                y = y-50;
            }
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, CIRCLE_METER_OUTLINE);
        drawContext.drawTexture(CIRCLE_METER_OUTLINE, x-140, y, 0,0,48,48,48,48);


        drawContext.drawTexture(CIRCLE_METER_BACKGROUND, x-140, y, 0,0,48,48,48,48);


        /*

        //Vector3f tempVector = new Vector3f(0, 0, 0);
        AxisAngle4f tempAxis = new AxisAngle4f( 0, 0,0,0);
        Quaternionf tempQuat = new Quaternionf(tempAxis);


        MatrixStack posestack;


        posestack = drawContext.getMatrices();
        posestack.push();
        posestack.multiply(tempQuat);

         */

        IEntityDataSaver playerData = (IEntityDataSaver) client.player;
        int energy = playerData.getPersistentData().getInt("energy");
        UUID uuid = client.player.getUuid();



        if(PlayerStorage.isPowerupActive("Powered-Players", uuid)){
            RenderSystem.setShaderColor(1.0F, 0.2F, 0.2F, 1.0F);
        }else{
            RenderSystem.setShaderColor(1.0F, 0.8F, 0.2F, 1.0F);
        }
        drawContext.drawTexture(CIRCLE_METER_BACKGROUND, x-140, y, 0,0,48,48,48,48);

        int energyPercent = Math.round(100*((float) energy / PlayerStatsStorage.getMaxEnergy(uuid)));
        String percentText = energyPercent+"%";

        drawMeter(drawContext, energyPercent, x, y);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        TextRenderer textRenderer = MinecraftClient.getInstance().inGameHud.getTextRenderer();
        //textRenderer.fontHeight = 8;

        drawContext.drawCenteredTextWithShadow(textRenderer, percentText, x-116, y+20, 0xffffff);




    }

    private void drawMeter(DrawContext drawContext, Integer energyPercent, Integer x, Integer y){
        if(energyPercent == 0){
            return;
        }

        if(energyPercent > 20-1){
            drawContext.drawTexture(METER_1, x-140, y, 0,0,48,48,48,48);
        }else{
            drawContext.drawTexture(METER_1, x-140, y, 0,0,(int) Math.round(12+(energyPercent*1.35)),48,48,48);
            return;
        }

        if(energyPercent > 50-1){
            drawContext.drawTexture(METER_2, x-140, y, 0,0,48,48,48,48);
        }else{
            drawContext.drawTexture(METER_2, x-140, y, 0,0,48,(int) Math.round(8+((energyPercent-20)*1.07)),48,48);
        }

        if(energyPercent > 70-1){
            drawContext.drawTexture(METER_3, x-140, y, 0,0,48,48,48,48);
        }else{
            drawContext.drawTexture(METER_3, x-140, y, 0,0, 48,48,48,48);
            drawContext.drawTexture(METER_3_EMPTY, x-140, y, 0,0, (int) Math.round(39-((energyPercent-50)*1.45)),48,48,48);
        }

        if(energyPercent > 77-1){
            drawContext.drawTexture(METER_4, x-140, y, 0,0,48,48,48,48);
        }else{
            drawContext.drawTexture(METER_4, x-140, y, 0,0, 48,48,48,48);
            drawContext.drawTexture(METER_4_EMPTY, x-140, y, 0,0, 48,(int) Math.round(41-((energyPercent-70)*(10/7))),48,48);
        }

        if(energyPercent == 100){
            drawContext.drawTexture(METER_5, x-140, y, 0,0,48,48,48,48);
        }else{
            drawContext.drawTexture(METER_5, x-140, y, 0,0, 48,48,48,48);
            drawContext.drawTexture(METER_5_EMPTY, x-140, y, 0,0, 48,(int) Math.round(31-((energyPercent-77)*0.826)),48,48);
        }
    }
}
