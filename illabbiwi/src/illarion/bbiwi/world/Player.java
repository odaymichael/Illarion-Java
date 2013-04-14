/*
 * This file is part of the Illarion BBIWI.
 *
 * Copyright © 2013 - Illarion e.V.
 *
 * The Illarion BBIWI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion BBIWI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion BBIWI.  If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.bbiwi.world;

import illarion.common.data.CharacterAttribute;
import illarion.common.data.Skill;
import illarion.common.types.CharacterId;
import illarion.common.types.Location;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a single player on the server.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public class Player {
    /**
     * The character ID of the player.
     */
    @Nonnull
    private final CharacterId characterId;

    /**
     * The name of the player.
     */
    @Nonnull
    private String name;

    /**
     * The location of the player.
     */
    @Nonnull
    private final Location location;

    /**
     * The attributes of the player character.
     */
    @Nonnull
    private final Map<CharacterAttribute, Integer> attributes;

    /**
     * The skills of the player character.
     */
    @Nonnull
    private final Map<Skill, Integer> skills;

    /**
     * This is {@code true} as long as the player is online.
     */
    private boolean online;

    /**
     * Create a new player character.
     *
     * @param id   the ID of the character
     * @param name the name of the character
     */
    public Player(@Nonnull final CharacterId id, @Nonnull final String name) {
        characterId = id;
        this.name = name;
        location = new Location();
        attributes = new EnumMap<CharacterAttribute, Integer>(CharacterAttribute.class);
        skills = new HashMap<Skill, Integer>();
    }

    /**
     * Update the location a character is located at.
     *
     * @param loc the new location
     */
    public void setLocation(@Nonnull final Location loc) {
        location.set(loc);
    }

    /**
     * Set the value of the online flag.
     *
     * @param on the value of the online flag
     */
    public void setOnline(final boolean on) {
        online = on;
    }

    /**
     * Set the name of the character.
     *
     * @param newName the name of the character
     */
    public void setName(@Nonnull final String newName) {
        name = newName;
    }
}
