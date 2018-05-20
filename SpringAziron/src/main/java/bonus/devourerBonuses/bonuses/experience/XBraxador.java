package bonus.devourerBonuses.bonuses.experience;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.service.engine.EventEngine;

public final class XBraxador extends Bonus {

    private static final double EXPERIENCE_COEFFICIENT = 1.15;

    private static final double HEALING_COEFFICIENT = 1.15;

    public XBraxador(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final double treatment = hero.getTreatment();
        final EventEngine eventEngine = actionManager.getEventEngine();
        if (hero.getHealing(treatment * HEALING_COEFFICIENT)){
            eventEngine.setRepeatHandling(true);
        }
        if (hero.addExperience(treatment * EXPERIENCE_COEFFICIENT)){
            eventEngine.setRepeatHandling(true);
        }
    }
}
