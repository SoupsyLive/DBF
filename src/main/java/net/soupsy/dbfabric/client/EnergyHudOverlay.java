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

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
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
        RenderSystem.setShaderTexture(0, EMPTY_ENERGY);
        for(int i = 0; i<10;i++){
            DrawableHelper.drawTexture(matrixStack, x-94+(i*9), y-50, 0,0,7,7,7,7);
        }
        RenderSystem.setShaderTexture(0, FULL_ENERGY);

        int meter = (((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getInt("power"));

        for(int i = 0; i<10;i++){
            if(meter/2 > i){
                DrawableHelper.drawTexture(matrixStack, x-94+(i*9), y-50, 0,0,7,7,7,7);
            } else if (meter%2 == 1) {
                RenderSystem.setShaderTexture(0, HALF_ENERGY);
                DrawableHelper.drawTexture(matrixStack, x-94+(i*9), y-50, 0,0,7,7,7,7);
                break;
            } else {
                break;
            }
        }
    }
}
