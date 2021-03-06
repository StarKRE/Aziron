package bonus.devourerBonuses.bonuses.health.regenerationRoot;

import management.actionManagement.ActionManager;
import management.actionManagement.actionProccessors.AttackProcessor;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.BattleManager;
import management.playerManagement.Team;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

public final class RegenerationRootProcessor extends AttackProcessor {

    private final PlayerManager playerManager;

    private Player currentPlayer;

    public RegenerationRootProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager) {
        super(actionManager, battleManager);
        this.playerManager = playerManager;
    }

    public final void process(){
        final heroes.abstractHero.hero.Hero currentHero = currentPlayer.getCurrentHero();
        final double treatmentValue = currentHero.getAttack();
        if (currentHero.getHealing(treatmentValue)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getBeforeAttack(currentPlayer));
        }
        if (currentHero.addExperience(treatmentValue)){
            actionManager.getEventEngine().handle();
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(playerManager.getCurrentTeam());
        }
    }

    public final void setTeams(final Team unused_1, final Team unused_2){
        this.currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
    }
}