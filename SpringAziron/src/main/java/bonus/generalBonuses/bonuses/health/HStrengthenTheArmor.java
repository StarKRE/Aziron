package bonus.generalBonuses.bonuses.health;

import heroes.abstractHero.abilities.bonus.Bonus;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.logging.Logger;


public final class HStrengthenTheArmor extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(HStrengthenTheArmor.class.getName());

    private static final double ARMOR_COEFFICIENT = 0.4;

    public HStrengthenTheArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ARMOR UP");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private Player alternativePlayer;

            private double hitPoints;

            private double healthSupply;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getCurrentHero().getHitPoints();
                this.alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final heroes.abstractHero.hero.Hero currentHero = player.getCurrentHero();
                final double hitPointsComparison = hitPoints - currentHero.getHitPoints();
                final double healthSupplyComparison = healthSupply - currentHero
                        .getHitPoints();
                log.info("ARMOR HANDLE");
                if (hitPointsComparison > 0) {
                    log.info("COMPARISON: " + hitPointsComparison);
                    final double ARMOR = hitPointsComparison * ARMOR_COEFFICIENT;
                    currentHero.setHitPoints(currentHero.getHitPoints() + ARMOR);
                    log.info("ARMOR: " + ARMOR);
                    if (healthSupplyComparison > 0) {
                        final double supplyArmor = healthSupplyComparison
                                * ARMOR_COEFFICIENT;
                        currentHero.setHealthSupply(currentHero.getHealthSupply()
                                + supplyArmor);
                    }
                }
                this.healthSupply = currentHero.getHealthSupply();
                this.hitPoints = currentHero.getHitPoints();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getHero() == player || actionEvent.getHero() == alternativePlayer)) {
                    isWorking = false;
                    log.info("ARMOR DOWN");
                }
            }

            @Override
            public final String getName() {
                return "StrengthenTheArmor";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean isWorking) {
                this.isWorking = isWorking;
            }
        };
    }
}
