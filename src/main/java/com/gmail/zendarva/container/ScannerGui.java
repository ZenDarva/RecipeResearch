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
    public ScannerGui(Container scannerContainer, EntityScanner scanner) {
        super(scannerContainer);
//        xSize=180;
//        ySize=152;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        this.renderHoveredToolTip(i,i1);
    }
}
