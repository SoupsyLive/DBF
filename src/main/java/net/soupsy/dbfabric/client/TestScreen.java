package net.soupsy.dbfabric.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soupsy.dbfabric.DragonBallFabric;

@Environment(EnvType.CLIENT)
public class TestScreen extends Screen {

    private final Screen parent;
    public TestScreen(Screen parent) {
        super(Text.literal("My Test Screen"));
        this.parent = parent;
    }

    public ButtonWidget button1;
    public ButtonWidget button2;

    private static final Identifier ID_CARD = new Identifier(DragonBallFabric.MOD_ID,
            "textures/gui/id_card.png");

    @Override
    protected void init(){
        button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {
            System.out.println("You clicked Button 1");
        })
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of Button 1")))
                .build();

        button2 = ButtonWidget.builder(Text.literal("Button 2"), button -> {
            System.out.println("You clicked Button 2");
        })
                .dimensions(width / 2 + 5, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of Button 2")))
                .build();

        addDrawableChild(button1);
        addDrawableChild(button2);

    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta){
        super.render(drawContext, mouseX, mouseY, delta);
        assert client != null;
        drawContext.drawCenteredTextWithShadow(client.inGameHud.getTextRenderer(), Text.literal("You must see me"), width /2, height/2, 0xffffff);

        //drawContext.drawNineSlicedTexture(BLUE_BOX, width /2, height/2, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100);


        assert client.player != null;
        int x = (width/2) - (174/2) + 32;
        int y = (height/2) + 30;
        int size = 33;
        float mX = mouseX*(-1) + ((float) x);
        float mY = mouseY*(-1) + ((float) y) - 23; //-23 to get to eye level

        //drawContext.drawTexture(ID_CARD, (width/2)-(174/2), (height/2)-(112/2), 0, 0, 174, 111, 232, 111);

        InventoryScreen.drawEntity(drawContext, x, y, size, mX, mY, client.player);

        drawContext.drawTexture(ID_CARD, (width/2)-(174/2), (height/2)-(112/2), 0, 0, 174, 111, 232, 111);
    }

    @Override
    public void close(){
        client.setScreen(parent);
    }

}
