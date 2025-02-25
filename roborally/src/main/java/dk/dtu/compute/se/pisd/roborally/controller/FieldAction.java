/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * This is an abstract class for actions on spaces; since it implements some game logic,
 * it is located in the controller package. But, it is a model class at the same time,
 * since it represents also the game board.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
// XXX A3
public abstract class FieldAction {

    /**
     * The field action to be executed for a given space. In order to be able to do
     * that the GameController associated with the game is passed to this method.
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return whether the action was successfully executed
     */
    public abstract boolean doAction(GameController gameController, Space space);

}
