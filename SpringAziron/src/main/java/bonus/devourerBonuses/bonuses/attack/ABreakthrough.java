package bonus.devourerBonuses.bonuses.attack;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import management.playerManagement.Player;

import java.util.List;
import java.util.logging.Logger;

public final class ABreakthrough extends Bonus{

    private static final Logger log = Logger.getLogger(ABreakthrough.class.getName());

    private static final double DAMAGE = 350.0;

    public ABreakthrough(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();

        final heroes.abstractHero.hero.Hero opponentHero = opponentPlayer.getCurrentHero();
        final List<Skill> opponentSkills = opponentHero.getCollectionOfSkills();
        for (final Skill skill: opponentSkills){
            final boolean levelReached = opponentHero.getLevel() >= skill.getRequiredLevel();
            if (skill.isReady() && levelReached){
                if (currentPlayer.getCurrentHero().getDamage(DAMAGE)){
                    log.info("-" + DAMAGE + " HP");
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
                skill.reset();
            }
        }
    }
}