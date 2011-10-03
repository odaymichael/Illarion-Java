/*
 * This file is part of the Illarion Graphics Engine.
 *
 * Copyright © 2011 - Illarion e.V.
 *
 * The Illarion Graphics Engine is free software: you can redistribute i and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The Illarion Graphics Engine is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * the Illarion Graphics Interface. If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.graphics.lwjgl;

import gnu.trove.list.array.TIntArrayList;

import illarion.graphics.Texture;
import illarion.graphics.TextureAtlas;

import org.lwjgl.opengl.GL11;

/**
 * The implementation of the Texture for usage with LWJGL.
 * 
 * @author Martin Karing
 * @version 2.00
 * @since 2.00
 */
public final class TextureLWJGL implements Texture {
    /**
     * This variable stores the next unique ID a newly created texture will get.
     */
    private static int nextUID = 1;

    /**
     * The height of the image itself.
     */
    private int height = 0;

    /**
     * The parent texture atlas this texture was created from.
     */
    private TextureAtlasLWJGL parent = null;

    /**
     * The height of the texture atlas that is the parent of this image.
     */
    private int texHeight = 0;

    /**
     * The width of the texture atlas that is the parent of this image.
     */
    private int texWidth = 0;

    /**
     * The unique texture ID of this texture.
     */
    private final int uid;

    /**
     * The width of the image itself.
     */
    private int width = 0;
    /**
     * The x coordinate of the location of the image.
     */
    private int x = 0;

    /**
     * The y coordinate of the location of the image.
     */
    private int y = 0;

    /**
     * Create a empty texture.
     */
    public TextureLWJGL() {
        uid = nextUID++;
    }

    /**
     * Create a new texture with specified width and height and a texture size.
     * Assumes that the X and Y coordinates of the image are at 0.
     * 
     * @param newWidth the width of the image
     * @param newHeight the height of the image
     * @param newTexWidth the width of the parent texture
     * @param newTexHeight the height of the parent texture
     */
    public TextureLWJGL(final int newWidth, final int newHeight,
        final int newTexWidth, final int newTexHeight) {
        this(newWidth, newHeight, newTexWidth, newTexHeight, 0, 0);
    }

    /**
     * Create a new texture with specified width and height and a texture size.
     * Also the position of the texture on the texture map is set.
     * 
     * @param newWidth the width of the image
     * @param newHeight the height of the image
     * @param newTexWidth the width of the parent texture
     * @param newTexHeight the height of the parent texture
     * @param newX the x coordinate of the image on the parent texture
     * @param newY the y coordinate of the image on the parent texture
     */
    public TextureLWJGL(final int newWidth, final int newHeight,
        final int newTexWidth, final int newTexHeight, final int newX,
        final int newY) {
        this();

        x = newX;
        y = newY;

        height = newHeight;
        width = newWidth;
        texWidth = newTexWidth;
        texHeight = newTexHeight;

        calculateRatio();
    }
    
    private float relX1;
    private float relY1;
    private float relX2;
    private float relY2;

    /**
     * Calculate the relative values in this class. After calling this function
     * all values that are relative to the size of the atlas are valid. Has to
     * be called for sure before this values are read.
     */
    private void calculateRatio() {
        relX1 = ((float) x) / texWidth;
        relX2 = ((float) x + width) / texWidth;
        
        relY1 = ((float) y) / texHeight;
        relY2 = ((float) y + height) / texHeight;

        textureDataChanged();
    }

    /**
     * Get the height of the original image.
     * 
     * @return The height of the original image.
     */
    @Override
    public final int getImageHeight() {
        return height;
    }

    /**
     * Get the width of the original image.
     * 
     * @return The width of the original image.
     */
    @Override
    public final int getImageWidth() {
        return width;
    }

    /**
     * Get the x coordinate of the position of the image on the texture atlas.
     * 
     * @return the x coordinate of the image position
     */
    @Override
    public final int getImageX() {
        return x;
    }

    /**
     * Get the y coordinate of the position of the image on the texture atlas.
     * 
     * @return the y coordinate of the image position
     */
    @Override
    public final int getImageY() {
        return y;
    }

    /**
     * Get the texture atlas assigned to this texture.
     * 
     * @return the texture atlas assigned to this texture
     */
    @Override
    public final TextureAtlasLWJGL getParent() {
        return parent;
    }

    /**
     * Get the x coordinate of the texture relative to the size of the texture
     * the image is located on.
     * 
     * @return the relative x coordinate.
     */
    public final float getRelX1() {
        return relX1;
    }
    
    /**
     * Get the X coordinate plus the width of the texture as relative value to
     * the image it is located on.
     * 
     * @return the relative x coordinate plus the relative width
     */
    public final float getRelX2() {
        return relX2;
    }

    /**
     * Get the y coordinate of the texture relative to the size of the texture
     * the image is located on.
     * 
     * @return the relative y coordinate.
     */
    public final float getRelY1() {
        return relY1;
    }

    /**
     * Get the Y coordinate plus the height of the texture as relative value to
     * the image it is located on.
     * 
     * @return the relative y coordinate plus the relative height
     */
    public final float getRelY2() {
        return relY2;
    }

    /**
     * Get the height of the parent texture this image is located on.
     * 
     * @return the height of the texture atlas
     */
    public final int getTextureHeight() {
        return texHeight;
    }

    /**
     * Get the width of the parent texture this image is located on.
     * 
     * @return the width of the texture atlas
     */
    public final int getTextureWidth() {
        return texWidth;
    }

    /**
     * Get the unique ID of this texture.
     * 
     * @return the unique ID of this texture.
     */
    public final int getUID() {
        return uid;
    }

    /**
     * Call this function in case this texture is not needed anymore and needs
     * to be removed from the system.
     */
    public final void remove() {
        if (parent != null) {
            parent.decreaseLoadCounter();
            parent.checkUsed();
            parent = null;
        }
    }

    @Override
    public final void reportUsed() {
        if (parent != null) {
            parent.increaseLoadCounter();
        }
    }

    /**
     * Set the dimension of the image this texture instance defines.
     * 
     * @param newWidth The width of the image
     * @param newHeight The height of the image
     */
    @Override
    public final void setImageDimension(final int newWidth, final int newHeight) {
        final boolean calculateNeeded =
            ((width != newWidth) || (height != newHeight));
        width = newWidth;
        height = newHeight;

        if (calculateNeeded) {
            calculateRatio();
        }
    }

    /**
     * Set the location of the image on the texture atlas the image is located
     * on.
     * 
     * @param newX the x coordinate of the image on the parent texture
     * @param newY the y coordinate of the image on the parent texture
     */
    @Override
    public final void setImageLocation(final int newX, final int newY) {
        final boolean calculateNeeded = ((x != newX) || (y != newY));
        x = newX;
        y = newY;

        if (calculateNeeded) {
            calculateRatio();
        }
    }

    /**
     * Set the parent texture of this texture.
     * 
     * @param parentAtlas the parent texture atlas of this texture
     */
    @Override
    @SuppressWarnings("nls")
    public void setParent(final TextureAtlas parentAtlas) {
        if (!(parentAtlas instanceof TextureAtlasLWJGL)) {
            throw new IllegalArgumentException(
                "Invalid implementation of the texture atlas");
        }
        if (parent != null) {
            parent.decreaseLoadCounter();
        }
        parent = (TextureAtlasLWJGL) parentAtlas;
        parent.increaseLoadCounter();
    }

    /**
     * Set the dimension of the parent texture this image is located on.
     * 
     * @param newTexWidth the width of the parent texture
     * @param newTexHeight the height of the parent texture
     */
    public final void setTextureDimension(final int newTexWidth,
        final int newTexHeight) {
        final boolean calculateNeeded =
            ((texHeight != newTexHeight) || (texWidth != newTexWidth));
    
        texHeight = newTexHeight;
        texWidth = newTexWidth;
    
        if (calculateNeeded) {
            calculateRatio();
        }
    }
    
    /**
     * A list of used display list IDs. This is used to clean up the prepared
     * display lists in case its needed.
     */
    private static TIntArrayList usedDisplayLists = new TIntArrayList();

    /**
     * Clean up all textures. After this is done none of the textures can be
     * used anymore.
     */
    @SuppressWarnings("nls")
    public static void dispose() {
        final int n = usedDisplayLists.size();
        if (n == 0) {
            return;
        }
        
        int minDisplayList = Integer.MAX_VALUE;
        int maxDisplayList = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            final int listID = usedDisplayLists.get(i);
            minDisplayList = Math.min(minDisplayList, listID);
            maxDisplayList = Math.max(maxDisplayList, listID);
        }

        GL11.glDeleteLists(minDisplayList,
            (maxDisplayList - minDisplayList) + 1);
    }

    /**
     * This variable stores if the display list needs to be refreshed.
     */
    private boolean displayListDirty = false;

    /**
     * The ID of the display list used to draw this texture.
     */
    private int displayListID = -1;

    /**
     * Get the ID of the display list needed to render this texture. In case
     * there is none, the display list is created and compiled automatically.
     * 
     * @return the ID of the display list that needs to be called to render this
     *         list
     */
    @SuppressWarnings("nls")
    public int getDisplayListID() {
        if ((displayListID == -1) || displayListDirty) {
            if (displayListID == -1) {
                displayListID = GL11.glGenLists(1);
                usedDisplayLists.add(displayListID);
            }

            GL11.glNewList(displayListID, GL11.GL_COMPILE);
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

            GL11.glTexCoord2d(getRelX1(), getRelY1());
            GL11.glVertex2f(-0.5f, -0.5f);
            GL11.glTexCoord2d(getRelX1(), getRelY2());
            GL11.glVertex2f(-0.5f, 0.5f);
            GL11.glTexCoord2d(getRelX2(), getRelY1());
            GL11.glVertex2f(0.5f, -0.5f);
            GL11.glTexCoord2d(getRelX2(), getRelY2());
            GL11.glVertex2f(0.5f, 0.5f);

            GL11.glEnd();
            GL11.glEndList();

            displayListDirty = false;
        }

        return displayListID;
    }

    private void textureDataChanged() {
        displayListDirty = true;
    }

    @Override
    public Texture getSubTexture(int x, int y, int width, int height) {
        final TextureLWJGL result = new TextureLWJGL(width, height, getTextureWidth(), getTextureHeight(), getImageX() + x, getImageY() + y);
        result.setParent(getParent());
        return result;
    }

    @Override
    public void cleanup() {
        if (displayListID != -1) {
            GL11.glDeleteLists(displayListID, 1);
            displayListID = -1;
            usedDisplayLists.remove(displayListID);
        }
    }
}
