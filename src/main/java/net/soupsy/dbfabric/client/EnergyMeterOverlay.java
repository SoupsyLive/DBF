package net.soupsy.dbfabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.util.IEntityDataSaver;

public class EnergyMeterOverlay implements HudRenderCallback {
    private static final Identifier CIRCLE_METER_OUTLINE = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-empty.png");
    private static final Identifier CIRCLE_METER_BACKGROUND = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-meter-background.png");
    private static final Identifier CIRCLE_METER_FULL = new Identifier(DragonBallFabric.MOD_ID,
            "textures/meter/energy-circle-meter.png");



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


        RenderSystem.setShaderColor(1.0F, 0.8F, 0.2F, 1.0F);
        //RenderSystem.setShaderTexture(2, CIRCLE_METER_FULL);
        drawContext.drawTexture(CIRCLE_METER_FULL, x-140, y, 0,0,48,48,48,48);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
