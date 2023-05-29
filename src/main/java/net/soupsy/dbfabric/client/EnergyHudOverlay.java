package net.soupsy.dbfabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;
import net.soupsy.dbfabric.util.IEntityDataSaver;

public class EnergyHudOverlay implements HudRenderCallback {
    private static final Identifier FULL_ENERGY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/energy/energy_full.png");
    private static final Identifier HALF_ENERGY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/energy/energy_half.png");
    private static final Identifier EMPTY_ENERGY = new Identifier(DragonBallFabric.MOD_ID,
            "textures/energy/energy_empty.png");
    private static final Identifier DOUBLE_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-doublebar.png");
    private static final Identifier SINGLE_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-singlebar.png");
    private static final Identifier TOP_FULL_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-top-full.png");
    private static final Identifier TOP_EMPTY_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-top-empty.png");
    private static final Identifier BOTTOM_FULL_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-bottom-full.png");
    private static final Identifier BOTTOM_EMPTY_BAR = new Identifier(DragonBallFabric.MOD_ID,
            "textures/bars/energybar-bottom-empty.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta){
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width/2;
            y = height;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // create the double bar
        RenderSystem.setShaderTexture(0, DOUBLE_BAR);
        DrawableHelper.drawTexture(matrixStack, x-95, y-60, 0,0,190,18,190,18);

        // calc color
        int meter = (((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getInt("energy"));
        boolean inPowerUp = (((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getBoolean("power-up"));

        if(!inPowerUp){
            // yellow bar
            RenderSystem.setShaderColor(1.0F, 0.8F, 0.0F, 1.0F);
        }else{
            //red bar
            RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
        }
        // GOOD COLORS
        // Cute Green   -   RenderSystem.setShaderColor(0.1F, 0.8F, 0.2F, 1.0F);
        // Almost Vibrant Yellow   -   RenderSystem.setShaderColor(1.0F, 0.8F, 0.2F, 1.0F);


        // add the energy empty bar
        RenderSystem.setShaderTexture(0, TOP_EMPTY_BAR);
        DrawableHelper.drawTexture(matrixStack, x-95, y-60, 0,0,190,10,190,10);

        // add full energy bar
        RenderSystem.setShaderTexture(0, TOP_FULL_BAR);
        DrawableHelper.drawTexture(matrixStack, x-95, y-60, 0,0, Math.round((meter*190)/100),10,190,10);

        // add bottom bars
        RenderSystem.setShaderColor(0.0F, 0.8F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, BOTTOM_EMPTY_BAR);
        DrawableHelper.drawTexture(matrixStack, x-95, y-52, 0,0,190,10,190,10);

        RenderSystem.setShaderTexture(0, BOTTOM_FULL_BAR);
        DrawableHelper.drawTexture(matrixStack, x-95, y-52, 0,0,190,10,190,10);

        // return normal rendering
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
