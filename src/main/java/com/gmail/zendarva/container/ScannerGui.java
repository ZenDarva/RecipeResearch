package com.gmail.zendarva.container;

import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.tile.EntityScanner;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * Created by James on 4/1/2018.
 */
public class ScannerGui extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(RecipeResearch.MODID, "textures/gui/scanner.png");
    private final EntityScanner scanner;

    public ScannerGui(Container scannerContainer, EntityScanner scanner) {
        super(scannerContainer);
        this.scanner = scanner;
        ySize = 165;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        if (scanner.progress > 0) {
            float percent = (float)scanner.progress/(float)scanner.maxProgress;
            int pixel = (int)(percent * 100);
            drawTexturedModalRect(guiLeft + 34, guiTop + 16, 0, 175, pixel, 9);
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
    }

}
