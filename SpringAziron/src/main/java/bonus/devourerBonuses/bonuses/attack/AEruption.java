package bonus.devourerBonuses.bonuses.attack;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Team;
import management.playerManagement.Player;

import java.util.logging.Logger;


public final class AEruption extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(AEruption.class.getName());

    private static final double DAMAGE = 10;

    public AEruption(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ERUPTION IS ACTIVATED");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private Team opponentTeam;

            private double hitPoints;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getCurrentHero().getHitPoints();
                this.opponentTeam = playerManager.getOpponentTeam();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final heroes.abstractHero.hero.Hero currentHero = player.getCurrentHero();
                final double comparison = hitPoints - currentHero.getHitPoints();
                if (comparison > 0 && currentHero.getHitPoints() < 0) {
                    log.info("COMPARISON: " + comparison);
                    final Player opponentPlayer = opponentTeam.getCurrentPlayer();
                    if (opponentPlayer.getCurrentHero().getDamage(DAMAGE)){
                        actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player
                                , opponentPlayer.getCurrentHero(), DAMAGE));
                    }
                }
                this.hitPoints = currentHero.getHitPoints();
            }

            @Override
            public final String getName() {
                return "Eruption";
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