package bonus.generalBonuses.bonuses.special;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class SWayOfWizard extends Bonus {

    private static final Logger log = Logger.getLogger(SWayOfWizard.class.getName());

    private static final double SKILL_BOOST = 0.03;

    public SWayOfWizard(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        for (final Skill skill : skills) {
            final List<Double> coefficients = skill.getCoefficients();
            final List<Double> newCoefficients = new ArrayList<>();
            for (final double coefficient : coefficients) {
                newCoefficients.add(coefficient * (1 + SKILL_BOOST));
            }
            skill.setCoefficients(newCoefficients);
        }
        log.info("SKILLS ARE UPGRADED BY 3%");
    }
}
