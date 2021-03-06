package bonus.lvBonuses.bonuses.health;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

public final class HRegenerationAmulet extends Bonus implements DynamicEngineService {

    private static final double HEALING_BOOST = 75;

    public HRegenerationAmulet(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getPrototypeEngineComponent());
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private boolean isWorking;

            private boolean isUpgradedHealing;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.isWorking = true;
                this.isUpgradedHealing = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getHero() == player) {
                    final heroes.abstractHero.hero.Hero hero = player.getCurrentHero();
                    switch (actionEvent.getActionType()) {
                        case START_TURN:
                            this.isUpgradedHealing = false;
                            break;
                        case BEFORE_TREATMENT:
                            hero.setTreatment(hero.getTreatment() + HEALING_BOOST);
                            this.isUpgradedHealing = true;
                            break;
                        case AFTER_TREATMENT:
                            hero.setTreatment(hero.getTreatment() - HEALING_BOOST);
                            break;
                        case END_TURN:
                        case SKIP_TURN:
                            if (!isUpgradedHealing) {
                                this.isWorking = false;
                            }
                    }
                }
            }

            @Override
            public final String getName() {
                return "RegenerationAmulet";
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