/*
 * This file is part of the Illarion Client.
 *
 * Copyright © 2012 - Illarion e.V.
 *
 * The Illarion Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Client.  If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.client.world.interactive;

import illarion.client.net.client.*;
import illarion.client.world.World;
import illarion.client.world.items.ContainerSlot;
import illarion.client.world.items.MerchantList;
import illarion.common.annotation.NonNull;
import illarion.common.types.ItemCount;
import illarion.common.types.ItemId;

/**
 * This class holds the interactive representation of a inventory slot.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class InteractiveContainerSlot implements Draggable, DropTarget {
    /**
     * The container slot this interactive reference points to.
     */
    private final ContainerSlot parentSlot;

    /**
     * Create a new instance of this interactive slot and set the container slot it refers to.
     *
     * @param slot the container slot this reference points to
     */
    public InteractiveContainerSlot(final ContainerSlot slot) {
        parentSlot = slot;
    }

    /**
     * Drag a inventory item to a character. Does nothing currently.
     */
    @Override
    public void dragTo(@NonNull final InteractiveChar targetChar, @NonNull final ItemCount count) {
        // nothing
    }

    /**
     * Drag the item in this inventory slot to another inventory slot.
     *
     * @param targetSlot the slot to drag the item to
     */
    @Override
    public void dragTo(@NonNull final InteractiveInventorySlot targetSlot, @NonNull final ItemCount count) {
        if (!targetSlot.acceptItem(getItemId())) {
            return;
        }

        World.getNet().sendCommand(new DragScInvCmd(getContainerId(), getSlotId(), targetSlot.getSlotId(), count));
    }

    /**
     * The ID of the item in this slot.
     *
     * @return the ID of the item in this slot
     */
    public ItemId getItemId() {
        return parentSlot.getItemID();
    }

    public int getContainerId() {
        return parentSlot.getContainerId();
    }

    /**
     * Drag the item in the inventory to a location on the map.
     *
     * @param targetTile the target location on the map
     */
    @Override
    public void dragTo(@NonNull final InteractiveMapTile targetTile, @NonNull final ItemCount count) {
        World.getNet().sendCommand(new DragScMapCmd(getContainerId(), getSlotId(), targetTile.getLocation(), count));
    }

    @Override
    public void dragTo(@NonNull final InteractiveContainerSlot targetSlot, @NonNull final ItemCount count) {
        if (!isValidItem() || !targetSlot.acceptItem(getItemId())) {
            return;
        }

        World.getNet().sendCommand(new DragScScCmd(getContainerId(), getSlotId(), targetSlot.getContainerId(),
                targetSlot.getSlotId(), count));
    }

    /**
     * Get the ID of the slot.
     *
     * @return the location ID
     */
    public int getSlotId() {
        return parentSlot.getLocation();
    }

    public boolean acceptItem(final ItemId itemId) {
        return !isValidItem() || itemId.equals(getItemId());
    }

    public boolean isValidItem() {
        return (getItemId() != null) && (getItemId().getValue() > 0);
    }

    public ContainerSlot getSlot() {
        return parentSlot;
    }

    public void lookAt() {
        if (!isValidItem()) {
            return;
        }

        World.getNet().sendCommand(new LookAtContainerCmd(getContainerId(), getSlotId()));
    }

    /**
     * Open the item as a container
     */
    public void openContainer() {
        if (!isValidItem()) {
            return;
        }

        World.getNet().sendCommand(new OpenInContainerCmd(getContainerId(), getSlotId()));
    }

    /**
     * Sell the item from the slot to the trader.
     */
    public void sell() {
        if (!World.getPlayer().hasMerchantList()) {
            return;
        }

        final MerchantList merchantList = World.getPlayer().getMerchantList();
        if (merchantList == null) {
            return;
        }

        World.getNet().sendCommand(new SellContainerItemCmd(merchantList.getId(), getContainerId(), getSlotId(),
                parentSlot.getCount()));
    }

    public void use() {
        World.getNet().sendCommand(new UseContainerCmd(getContainerId(), getSlotId()));
    }
}
