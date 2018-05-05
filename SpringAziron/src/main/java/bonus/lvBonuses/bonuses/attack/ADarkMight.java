package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import management.playerManagement.Player;

public final class ADarkMight extends Bonus implements DynamicHandleService {

    private static final double ATTACK_BOOST = 1;

    public ADarkMight(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking;

            private double temporaryAttackBoost;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.isWorking = true;
                this.temporaryAttackBoost = 0;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                final Player player = actionEvent.getHero();
                if (actionType == ActionType.START_TURN && (this.player == player)){
                    final Hero hero = player.getCurrentHero();
                    hero.setAttack(hero.getAttack() + ATTACK_BOOST);
                    temporaryAttackBoost++;
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
                if (actionType == ActionType.BEFORE_TREATMENT && (this.player == player)){
                    final Hero hero = player.getCurrentHero();
                    double previousAttack = hero.getAttack() - temporaryAttackBoost;
                    if (previousAttack < 0){
                        previousAttack = 0;
                    }
                    hero.setAttack(previousAttack);
                    this.isWorking = false;
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
            }

            @Override
            public final String getName() {
                return "DarkMight";
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
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}