/*
 * This file is part of the Illarion Mapeditor.
 *
 * Copyright © 2012 - Illarion e.V.
 *
 * The Illarion Mapeditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Mapeditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Mapeditor.  If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.mapedit.gui;

import illarion.mapedit.events.ToolSelectedEvent;
import org.apache.log4j.Logger;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tim
 */
public class ToolSettingsPanel extends JPanel {
    public static final Logger LOGGER = Logger.getLogger(ToolSettingsPanel.class);
    private JComponent lastChild;

    public ToolSettingsPanel() {
        AnnotationProcessor.process(this);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,0));
    }

    @SuppressWarnings("unused")
    @EventSubscriber(eventClass = ToolSelectedEvent.class)
    public void onToolSelected(final ToolSelectedEvent e) {
        LOGGER.debug("Tool Selected " + e.getTool());
        if (lastChild != null) {
            remove(lastChild);
        }
        lastChild = e.getTool().getSettingsPanel();
        LOGGER.debug("Panel: " + lastChild);
        if (lastChild != null) {
            add(lastChild, BorderLayout.CENTER);
            lastChild.setVisible(true);
        }
        validate();
        repaint();
    }
}
