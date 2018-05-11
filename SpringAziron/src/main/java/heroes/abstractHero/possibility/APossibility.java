package heroes.abstractHero.possibility;

import annotations.sourceAnnotations.NonFinal;
import controllers.main.matchmaking.ControllerLocation;
import heroes.abstractHero.hero.Hero;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

public abstract class APossibility {

    private static final Logger log = Logger.getLogger(APossibility.class.getName());

    //Possibility name:
    private final String name;

    //Main characteristics:
    private int START_STEP = 1;

    protected int step = 1;

    protected int reload;

    protected int requiredLevel;

    protected List<Double> coefficients;

    //Access:
    private boolean possibilityAccess;

    //Parent:
    protected Hero parentHero;

    protected ControllerLocation controllerLocation;

    //PlayerManager:
    protected PlayerManager playerManager;

    protected APossibility(final String name, final int reload, final int requiredLevel
            , final List<Double> coefficients, final ImageView mainImage, final ImageView descriptionImage
            , final List<Media> voices, final Media animationSound) {
        //Possibility name:
        this.name = name;
        this.reload = reload;
        this.requiredLevel = requiredLevel;
        this.coefficients = coefficients;
        this.possibilityAccess = true;
        //GUI:
        this.guiHolder = new AGUIHolder(this, mainImage, descriptionImage, voices, animationSound);
    }

    @NonFinal
    public void reload() {
        if (this.parentHero.getLevel() >= this.requiredLevel) {
            this.step++;
        } else {
            this.step = START_STEP;
        }
    }

    @NonFinal
    public void reset() {
        this.step %= this.reload;
        this.guiHolder.mainImage.setVisible(false);
    }

    private void makePossibilityRequest() {
        this.controllerLocation.makePossibilityRequest(this.getActionEvent());
    }

    @NonFinal
    protected ActionEvent getActionEvent() {
        return ActionEventFactory.getBeforeUsedPossibility(this.parentHero, this);
    }

    //GUI:
    protected AGUIHolder guiHolder;

    @NonFinal
    protected static class AGUIHolder {

        protected static final int START_OPACITY = 0;

        protected ImageView mainImage;

        protected ImageView descriptionImage;

        protected Pane container;

        //Audio:
        private Media animationSound;

        private List<Media> voices;

        protected AGUIHolder(final APossibility parent, final ImageView mainImage, final ImageView descriptionImage
                , final List<Media> voices, final Media animationSound) {
            mainImage.setOnMouseEntered(event -> this.showDescription());
            mainImage.setOnMouseExited(event -> this.hideDescription());
            mainImage.setOnMouseClicked(event -> parent.makePossibilityRequest());
            this.descriptionImage = descriptionImage;
            this.mainImage = mainImage;
            this.voices = voices;
            this.animationSound = animationSound;
        }

        public final void bindToLocation(final Pane parentPane, final double mainImageX, final double mainImageY
                , final double descriptionX, final double descriptionY, final boolean invert) {
            this.descriptionImage.setLayoutY(descriptionY); //-127
            this.descriptionImage.setOpacity(START_OPACITY);
            //init mainImage:
            final int inversion = invert ? -1 : 1;
            this.container = new Pane() {{
                final ObservableList<Node> elements = getChildren();
                this.setScaleX(inversion);
                this.setLayoutX(mainImageX);
                this.setLayoutY(mainImageY);
                elements.add(mainImage);
            }};
            parentPane.getChildren().add(this.container);
        }

        @NonFinal
        protected void showDescription() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this.descriptionImage);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        @NonFinal
        protected void hideDescription() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this.descriptionImage);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }

        @NonFinal
        public void showAnimation() {
        }

        public final Pane getContainer() {
            return this.container;
        }

        public final Media getAnimationSound() {
            return this.animationSound;
        }

        public final List<Media> getVoices() {
            return this.voices;
        }
    }

    public final void installControllerLocation(final ControllerLocation controllerLocation)
            throws APossibilityInstallationException {
        if (this.controllerLocation == null) {
            this.controllerLocation = controllerLocation;
        } else {
            throw new APossibilityInstallationException("ControllerLocation already installed!");
        }
    }

    public final void installPlayerManager(final PlayerManager playerManager) throws APossibilityInstallationException {
        if (this.playerManager == null) {
            this.playerManager = playerManager;
        } else {
            throw new APossibilityInstallationException("PlayerManager already installed");
        }
    }

    public final void installParentHero(final Hero parentHero) throws APossibilityInstallationException {
        if (this.parentHero == null) {
            this.parentHero = parentHero;
        } else {
            throw new APossibilityInstallationException("Parent hero already installed");
        }
    }

    /**
     * Getters & setters:
     */

    public final boolean isPossibilityAccess() {
        return possibilityAccess;
    }

    public final void setPossibilityAccess(final boolean possibilityAccess) {
        this.possibilityAccess = possibilityAccess;
    }

    public final String getName() {
        return this.name;
    }


    public final int getStep() {
        return this.step;
    }

    public final void setStep(final int step) {
        this.step = step;
    }

    public final int getReload() {
        return reload;
    }

    public final void setReload(final int reload) {
        this.reload = reload;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final void setRequiredLevel(final int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public final List<Double> getCoefficients() {
        return this.coefficients;
    }

    public final Hero getParentHero() {
        return this.parentHero;
    }

    public final AGUIHolder getGuiHolder() {
        return this.guiHolder;
    }

    public final void setGuiHolder(final AGUIHolder guiHolder) {
        this.guiHolder = guiHolder;
    }
}