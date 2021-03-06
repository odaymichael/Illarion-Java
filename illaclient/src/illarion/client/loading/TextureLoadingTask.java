/*
 * This file is part of the Illarion Client.
 *
 * Copyright © 2013 - Illarion e.V.
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
package illarion.client.loading;

import illarion.common.util.ProgressMonitor;
import org.illarion.engine.Engine;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * The purpose of this class is to load the texture resources that are required for the game.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
@NotThreadSafe
final class TextureLoadingTask implements LoadingTask {
    /**
     * The engine used to load the data.
     */
    @Nonnull
    private final Engine usedEngine;

    /**
     * The progress monitor that tracks the progress of the texture loading.
     */
    @Nonnull
    private final ProgressMonitor monitor;

    /**
     * Create a new texture loading task.
     *
     * @param engine the engine used to load the textures
     */
    TextureLoadingTask(@Nonnull final Engine engine) {
        usedEngine = engine;
        //noinspection MagicNumber
        monitor = new ProgressMonitor(4.f);
    }


    @Override
    public void load() {
        if (isLoadingDone()) {
            monitor.setProgress(1.f);
        } else {
            monitor.setProgress(usedEngine.getAssets().getTextureManager().loadRemaining());
        }
    }

    @Override
    public boolean isLoadingDone() {
        return usedEngine.getAssets().getTextureManager().isLoadingDone();
    }

    @Nonnull
    @Override
    public ProgressMonitor getProgressMonitor() {
        return monitor;
    }
}
