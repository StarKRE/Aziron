package controllers.main.matchmaking;

import com.google.inject.Inject;
import controllers.Controller;
import gui.service.locations.GraphicLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import management.actionManagement.ActionManager;
import javafx.fxml.Initializable;
import management.playerManagement.PlayerManager;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerMatchMaking implements Initializable, Controller {

    private final Logger log = Logger.getLogger(ControllerMatchMaking.class.getName());

    @Inject
    private ActionManager actionManager;

    @Inject
    private PlayerManager playerManager;

    @FXML
    private AnchorPane leftLocationPane;

    @FXML
    private AnchorPane rightLocationPane;

    @FXML
    private AnchorPane bonusLocationPane;

    //Global:
    @FXML
    private Button menuButton;

    //Pause menu:
    @FXML
    private AnchorPane pausePane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private Pane paramPane;

    @FXML
    private Pane infoPane;

    @FXML
    private Button buttonResumeGame;

    @FXML
    private Button buttonParams;

    @FXML
    private Button buttonEndMatch;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonInfo;

    private GraphicLocation currentLeftLocation;

    private GraphicLocation currentRightLocation;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        this.currentLeftLocation = new GraphicLocation(leftLocationPane, false);
        this.currentRightLocation = new GraphicLocation(rightLocationPane, true);
    }

    @Override
    public void appearance() {
        System.out.println("Launch");
    }

    //Style & Interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonMenuClicked() {
        this.pausePane.setVisible(true);
        this.log.info(this.playerManager.getCurrentTeam().toString());
        this.log.info(this.playerManager.getOpponentTeam().toString());
    }

    public final void buttonResumeGameClicked() {
        pausePane.setVisible(false);
    }

    public final void buttonParamsClicked() {
        this.menuPane.setVisible(false);
        this.paramPane.setVisible(true);
    }

    public final void buttonInfoClicked() {
        this.menuPane.setVisible(false);
        this.infoPane.setVisible(true);
    }

    @Contract(" -> fail")
    public final void buttonExitClicked() {
        System.exit(1);
    }

    //Getters:
    public final AnchorPane getMenuPane() {
        return this.menuPane;
    }

    public final GraphicLocation getCurrentLeftLocation() {
        return this.currentLeftLocation;
    }

    public final GraphicLocation getCurrentRightLocation() {
        return this.currentRightLocation;
    }

    public final AnchorPane getBonusLocationPane() {
        return this.bonusLocationPane;
    }

    public final ActionManager getActionManager() {
        return this.actionManager;
    }
}