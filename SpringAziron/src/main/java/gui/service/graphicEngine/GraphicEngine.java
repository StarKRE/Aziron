package gui.service.graphicEngine;

import gui.service.locations.GraphicLocation;
import heroes.abstractHero.abilities.bonus.Bonus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.windows.WindowType;
import heroes.abstractHero.hero.Hero;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.AGame;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.pipeline.APipeline;
import management.service.engine.EventEngine;
import management.battleManagement.BattleManager;
import management.playerManagement.GameMode;
import management.playerManagement.Player;
import management.playerManagement.Team;
import management.playerManagement.PlayerManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public final class GraphicEngine {

    private static final Logger log = Logger.getLogger(GraphicEngine.class.getName());

    @Inject
    private AGame aGame;

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private APipeline pipeline;

    private Team leftTeam;

    private Team rightTeam;

    private GraphicLocation currentLeftLocation;

    private GraphicLocation currentRightLocation;

    private boolean bindEnable = true;

    public final void install(){
        final ControllerMatchMaking matchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        this.currentLeftLocation = matchMaking.getCurrentLeftLocation();
        this.currentRightLocation = matchMaking.getCurrentRightLocation();
        this.leftTeam = this.playerManager.getLeftATeam();
        this.rightTeam = this.playerManager.getRightATeam();
        //Time:
        this.installTeamTimer(leftTeam, currentLeftLocation, BattleManager.getStartTime());
        this.installTeamTimer(rightTeam, currentRightLocation, BattleManager.getStartTime());
        showLocation();
    }

    private void installTeamTimer(final Team team, final GraphicLocation location, final int time){
        team.setTime(time);
        final Timeline timeline = team.getTimeline();
        timeline.setOnFinished(event -> {
            log.info("GG");
            battleManager.endGame();
        });
        final ObservableList<KeyFrame> keyFrames = team.getTimeline().getKeyFrames();
        keyFrames.add(new KeyFrame(Duration.seconds(1), event -> showLocation(location, team)));
        keyFrames.add(new KeyFrame(Duration.seconds(1), event -> {
            final ActionEvent frameDurationEvent = ActionEventFactory.getFrameDurationEvent(team.getCurrentPlayer()
                    .getCurrentHero());
//            eventEngine.handle(frameEvent);
        }));
    }

    private

    private void wireActionManagerToSkills(final ActionManager actionManager, final Team leftATeam, final Team rightATeam){
        final List<heroes.abstractHero.hero.Hero> allHeroes = new ArrayList<>(){{
            this.add(leftATeam.getCurrentPlayer().getCurrentHero());
            this.add(rightATeam.getCurrentPlayer().getCurrentHero());
            if (playerManager.getGameMode() == GameMode._2x2){
                this.add(leftATeam.getAlternativePlayer().getCurrentHero());
                this.add(rightATeam.getAlternativePlayer().getCurrentHero());
            }
        }};
        for (final Hero hero: allHeroes){
            final List<Skill> skills = hero.getCollectionOfSkills();
            for (final Skill skill : skills){
                skill.setActionManager(actionManager);
                log.info("Successfully wired action manager" + skill.getName());
            }
        }
    }

    public final void showLocation(){
        if (bindEnable){
            showLocation(currentLeftLocation, leftTeam);
            showLocation(currentRightLocation, rightTeam);
        }
    }

    private void showLocation(final AGraphicLocation location, final Team team){
        final Player currentPlayer = team.getCurrentPlayer();
        final heroes.abstractHero.hero.Hero hero = currentPlayer.getCurrentHero();
        hero.setGraphicLocation(location);
        location.setFace(currentPlayer.getCurrentHero().getFace());
        //Profile:
        location.setName(currentPlayer.getProfile().getName());
        //Attack:
        location.setAttack((hero.getAttack().intValue()));
        //Health:
        location.setHitPoints(hero.getHitPoints().intValue());
        location.setSupplyHealth(hero.getHealthSupply().intValue());
        location.setTreatment(hero.getTreatment().intValue());
        //Experience:
        location.setLevel(hero.getLevel());
        location.setExperience(hero.getCurrentExperience().intValue());
        final int level = hero.getLevel();
        if (level - 1 < 9){
           location.setRequiredExperience(hero.getListOfRequiredExperience().get(level - 1).intValue());
        } else {
            location.setRequiredExperience("");
        }
        //Skills:
        location.setupSuperSkills(hero);
        for (final Skill skill : hero.getCollectionOfSkills()){
            final boolean levelReached = hero.getLevel() >= skill.getRequiredLevel();
            if (skill.isReady() && levelReached){
                skill.getSprite().setVisible(true);
            }
        }
        final Player alternativePlayer = team.getAlternativePlayer();
        if (alternativePlayer != null){
            location.setupSwapSkill(alternativePlayer.getCurrentHero());
//        log.debug("SWAP_TEMP:" + team.getAlternativePlayer().getCurrentHero().getSwapSkill().getTemp());
//        log.debug("ALIVE: " + team.getAlternativePlayer().isAlive());
//        log.debug("READY_SWAP: " + team.getAlternativePlayer().getCurrentHero().getSwapSkill().isReady());
            if (alternativePlayer.isAlive() && alternativePlayer.getCurrentHero().getSwapSkill().isReady()){
                log.info("SWAP_SKILL_IS_VISIBLE");
                location.getSwapSkillPane().setVisible(true);
            } else {
                log.info("SWAP_SKILL_IS_INVISIBLE");
                location.getSwapSkillPane().setVisible(false);
            }
        }
        //Time:
        location.setTime(team.getTime());
    }

    public final void show3Bonuses(final List<Bonus> bonusList
            , final int firstBonus, final int secondBonus, final int thirdBonus){
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        final AnchorPane bonusLocationPane = controllerMatchMaking.getBonusLocationPane();
        bonusLocationPane.toFront();
        final Pane bonusPane = (Pane) bonusLocationPane.getChildren().get(0);
        final ImageView firstSprite = bonusList.get(firstBonus).getSprite();
        firstSprite.setLayoutX(0.0);
        bonusPane.getChildren().add(firstSprite);
        final ImageView secondSprite = bonusList.get(secondBonus).getSprite();
        secondSprite.setLayoutX(335.0);
        bonusPane.getChildren().add(secondSprite);
        final ImageView thirdSprite = bonusList.get(thirdBonus).getSprite();
        thirdSprite.setLayoutX(660.0);
        bonusPane.getChildren().add(thirdSprite);

        log.info("CHOOSE BONUS: " + bonusList.get(firstBonus).getName());
        log.info("CHOOSE BONUS: " + bonusList.get(secondBonus).getName());
        log.info("CHOOSE BONUS: " + bonusList.get(thirdBonus).getName());
        log.info("Bonuses load successfully");
    }

    public final void hideBonuses(){
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        final AnchorPane bonusLocationPane = controllerMatchMaking.getBonusLocationPane();
        bonusLocationPane.toBack();
        final Pane bonusPane = (Pane) bonusLocationPane.getChildren().get(0);
        bonusPane.getChildren().clear();
    }

    public AGraphicLocation getCurrentLeftLocation() {
        return currentLeftLocation;
    }

    public AGraphicLocation getCurrentRightLocation() {
        return currentRightLocation;
    }

    public boolean isBindEnable() {
        return bindEnable;
    }

    public void setBindEnable(boolean bindEnable) {
        this.bindEnable = bindEnable;
    }
}
