/*
 * This file is part of the Illarion Nifty-GUI Controls.
 *
 * Copyright © 2013 - Illarion e.V.
 *
 * The Illarion Nifty-GUI Controls is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Nifty-GUI Controls is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Nifty-GUI Controls.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.illarion.nifty.controls.dialog.select.builder;

import de.lessvoid.nifty.builder.ControlBuilder;

/**
 * This is the builder used to create the merchant dialog.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class DialogSelectBuilder extends ControlBuilder {
    /**
     * Build a new input dialog with a set title and ID.
     *
     * @param id    the nifty-gui ID of the dialog
     * @param title the title of the dialog
     */
    public DialogSelectBuilder(final String id, final String title) {
        super(id, CreateDialogSelectControl.NAME);
        set("title", title);
        set("closeable", "true");
        set("hideOnClose", "false");
        itemCount(6);

        alignCenter();
        valignCenter();
    }

    /**
     * Set the ID of this dialog.
     *
     * @param id the ID of the dialog
     */
    public void dialogId(final int id) {
        set("dialogId", Integer.toString(id));
    }

    /**
     * Set the message that is supposed to be displayed in the dialog.
     *
     * @param text the message shown in this dialog
     */
    public void message(final String text) {
        set("message", text);
    }

    public void itemCount(final int count) {
        set("itemCount", Integer.toString(count));
    }
}
