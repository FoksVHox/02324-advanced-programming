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

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class CardFieldView extends GridPane implements ViewObserver {

    // This data format helps avoid transfers of e.g. Strings from other
    // programs which can copy/paste Strings or even transfer from other
    // instances of RoboRally
    final public static DataFormat ROBO_RALLY_CARD =
            new DataFormat(
                    "application/x.roborally.command;uuid=" +
                    UUID.randomUUID());

    final public static int CARDFIELD_WIDTH = 65;
    final public static int CARDFIELD_HEIGHT = 100;

    final public static Border BORDER_DEFAULT = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2)));
    final public static Border BORDER_READY = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(4)));
    final public static Border BORDER_ACTIVE = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, null, new BorderWidths(4)));
    final public static Border BORDER_DONE = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4)));

    final public static Background BG_DEFAULT = new Background(new BackgroundFill(Color.WHITE, null, null));
    final public static Background BG_DRAG = new Background(new BackgroundFill(Color.GRAY, null, null));
    final public static Background BG_DROP = new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null));

    final public static Background BG_NONE = new Background(new BackgroundFill(Color.LIGHTGRAY,  null, null));
    final public static Background BG_INVISIBLE = new Background(new BackgroundFill(Color.DARKGRAY,  null, null));

    private CommandCardField field;

    private Label label;

    private GameController gameController;

    public CardFieldView(@NotNull GameController gameController, @NotNull CommandCardField field) {
        this.gameController = gameController;
        this.field = field;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 5, 5, 5));

        this.setBorder(BORDER_DEFAULT);
        this.setBackground(BG_DEFAULT);

        this.setPrefWidth(CARDFIELD_WIDTH);
        this.setMinWidth(CARDFIELD_WIDTH);
        this.setMaxWidth(CARDFIELD_WIDTH);
        this.setPrefHeight(CARDFIELD_HEIGHT);
        this.setMinHeight(CARDFIELD_HEIGHT);
        this.setMaxHeight(CARDFIELD_HEIGHT);

        label = new Label("This is a slightly longer text");
        label.setWrapText(true);
        label.setMouseTransparent(true);
        this.add(label, 0, 0);

        this.setOnDragDetected(new OnDragDetectedHandler());
        this.setOnDragOver(new OnDragOverHandler());
        this.setOnDragEntered(new OnDragEnteredHandler());
        this.setOnDragExited(new OnDragExitedHandler());
        this.setOnDragDropped(new OnDragDroppedHandler());
        this.setOnDragDone(new OnDragDoneHandler());

        field.attach(this);
        field.player.board.attach(this);
        field.player.attach(this);
        update(field);
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == field && subject != null) {
            CommandCard card = field.getCard();
            if (card == null) {
                label.setText("");
                this.setBackground(BG_NONE);
            } else if (field.isVisible()) {
                label.setText(card.getName());
                this.setBackground(BG_DEFAULT);
            } else {
                label.setText("<Card back>");
                this.setBackground(BG_INVISIBLE);
            }
        }
    }

    private class OnDragDetectedHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView source = (CardFieldView) t;
                CommandCardField cardField = source.field;
                if (cardField != null &&
                        cardField.getCard() != null &&
                        cardField.player != null &&
                        cardField.player.board != null &&
                        cardField.player.board.getPhase().equals(Phase.PROGRAMMING)) {
                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    Image image = source.snapshot(null, null);
                    db.setDragView(image);
                    ClipboardContent content = new ClipboardContent();
                    content.put(ROBO_RALLY_CARD, cardField.getCard().command.ordinal());
                    db.setContent(content);
                    source.setBackground(BG_DRAG);
                }
            }
            event.consume();
        }

    }


    // TODO redundant with DragEnterHandler (but some functionality needs to be moved from
    //      here to DragEnterHandler
    private class OnDragOverHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        (cardField.getCard() == null || event.getGestureSource() == target) &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragEnteredHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        target.setBackground(BG_DROP);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragExitedHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            event.getDragboard().hasContent(ROBO_RALLY_CARD)) {
                        target.setBackground(BG_NONE);
                    }
                }
            }
            event.consume();
        }

    }

    private class OnDragDroppedHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView target = (CardFieldView) t;
                CommandCardField cardField = target.field;

                Dragboard db = event.getDragboard();
                if (cardField != null &&
                        cardField.getCard() == null &&
                        cardField.player != null &&
                        cardField.player.board != null) {
                    if (event.getGestureSource() != target &&
                            db.hasContent(ROBO_RALLY_CARD)) {
                        Object object = db.getContent(ROBO_RALLY_CARD);
                        if (object instanceof Integer) {
                            int number = (Integer) object;
                            if (number < Command.values().length) {
                                Command command = Command.values()[number];
                                cardField.setCard(new CommandCard(command));
                                event.setDropCompleted(true);
                                event.consume();
                                return;
                            }
                        }
                    }
                }
                event.setDropCompleted(false);
                target.setBackground(BG_NONE);
            }
            event.consume();
        }

    }

    private class OnDragDoneHandler implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            Object t = event.getTarget();
            if (t instanceof CardFieldView) {
                CardFieldView source = (CardFieldView) t;
                if (event.isAccepted()) {
                    source.field.setCard(null);
                    // the view will update automatically since the field changed
                    // source.setBackground(BG_NONE);
                } else {
                    // leave the card on the field, since drag and drop was cancelled
                    // just switch back to defaut colour of a card
                    source.setBackground(BG_DEFAULT);
                }
            }
            event.consume();
        }

    }

}




