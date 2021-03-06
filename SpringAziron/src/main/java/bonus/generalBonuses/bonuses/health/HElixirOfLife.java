package bonus.generalBonuses.bonuses.health;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

import java.util.logging.Logger;

public final class HElixirOfLife extends Bonus {

    private static final Logger log = Logger.getLogger(HElixirOfLife.class.getName());

    private static final double HEALING_BOOST = 50;

    public HElixirOfLife(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        if (currentHero.getHealing(HEALING_BOOST)) {
            log.info("+50 HP");
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }
}