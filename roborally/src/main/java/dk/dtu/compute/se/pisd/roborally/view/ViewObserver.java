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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import javafx.application.Platform;

/**
 * This is an extension of the {@link Observer} from the observer pattern.
 * This guarantees that graphical updates are done in the JavaFX application
 * thread, as required by JavaFX.
 *
 * Subclasses should implement the method {@link #updateView(Subject)} instead
 * of the method {@link #update(Subject)}.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public interface ViewObserver extends Observer {

    void updateView(Subject subject);

    /**
     * Implements the update method of the {@link Observer} in such a way that
     * the update is done in the JavaFX application thread, if necessary.
     *
     * @param subject the subject which changed
     */
    @Override
    default void update(Subject subject) {
        // This default implementation of the update method makes sure that ViewObserver implementations
        // are doing the update only in the FX application thread. In any case the update
        // are delegated to the updateView() method in the implementations of the
        // respective ViewObserver.
        if (Platform.isFxApplicationThread()) {
            updateView(subject);
        } else {
            Platform.runLater(() -> updateView(subject));
        }
    }

}
