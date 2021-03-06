package management.actionManagement.actionProccessors;

import management.actionManagement.ActionManager;
import management.battleManagement.BattleManager;
import management.playerManagement.Team;
import management.playerManagement.Player;
import management.processors.Processor;

//Not final!
public class TreatmentProcessor implements Processor {

    protected final ActionManager actionManager;

    protected final BattleManager battleManager;

    private Team team;

    public TreatmentProcessor(final ActionManager actionManager, final BattleManager battleManager){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
    }

    @Override
    public void process() {
        final Player currentPlayer = team.getCurrentPlayer();
        final heroes.abstractHero.hero.Hero currentHero = currentPlayer.getCurrentHero();
        final double treatmentValue = currentHero.getTreatment();
        if (currentHero.getHealing(treatmentValue)) {
            actionManager.getEventEngine().handle();
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(team);
        }
    }

    public void setTeam(final Team team){
        this.team = team;
    }
}