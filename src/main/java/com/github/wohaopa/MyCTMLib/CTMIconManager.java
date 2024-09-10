package com.github.wohaopa.MyCTMLib;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CTMIconManager {

    private final IIcon[] icons = new CTMIcon[21];
    private final IIcon icon, iconSmall;

    public CTMIconManager(IIcon iconSmall, IIcon icon) {
        this.icon = icon;
        this.iconSmall = iconSmall;
    }

    private boolean inited = false;

    public void init() {
        for (int i = 1; i <= 4; i++) for (int j = 0; j < 4; j++) icons[i + j * 4] = new CTMIcon(icon, 4, 4, i - 1, j);

        for (int i = 1; i <= 2; i++)
            for (int j = 0; j < 2; j++) icons[i + j * 2 + 16] = new CTMIcon(iconSmall, 2, 2, i - 1, j);
        inited = true;
    }

    public IIcon getIcon(int index) {
        if (index > 0 && index < 21) return icons[index];
        throw new RuntimeException("Invalid index: " + index);
    }

    public boolean hasInited() {
        return inited;
    }

    private static class CTMIcon implements IIcon {

        private final int width;
        private final int height;
        private final float umin;
        private final float umax;
        private final float vmin;
        private final float vmax;
        private final IIcon parentIcon;

        private CTMIcon(IIcon parent, int w, int h, int x, int y) {
            parentIcon = parent;

            umin = parentIcon.getInterpolatedU(16.0 * (x) / w);
            umax = parentIcon.getInterpolatedU(16.0 * (x + 1) / w);
            vmin = parentIcon.getInterpolatedV(16.0 * (y) / h);
            vmax = parentIcon.getInterpolatedV(16.0 * (y + 1) / h);

            width = parentIcon.getIconWidth();
            height = parentIcon.getIconHeight();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinU() {
            return umin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxU() {
            return umax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedU(double d0) {
            return (float) (umin + (umax - umin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinV() {
            return vmin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxV() {
            return vmax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedV(double d0) {
            return (float) (vmin + (vmax - vmin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getIconName() {
            return parentIcon.getIconName();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconWidth() {
            return width;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconHeight() {
            return height;
        }
    }
}
