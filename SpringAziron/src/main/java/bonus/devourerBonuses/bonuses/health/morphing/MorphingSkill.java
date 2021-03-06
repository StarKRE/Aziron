package bonus.devourerBonuses.bonuses.health.morphing;

import heroes.abstractHero.tallents.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class MorphingSkill extends AbstractSkill {

    private static final Logger log = Logger.getLogger(MorphingSkill.class.getName());

    private static final String NAME = "Morphing";

    private static final int RELOAD = 7;

    private static final int REQUIRED_LEVEL = 1;

    private static final double TREATMENT_SKILL_COEFFICIENT = 1.1;

    private static final List<Double> SKILL_COEFFICIENTS = Collections
            .singletonList(TREATMENT_SKILL_COEFFICIENT);

    public MorphingSkill(final ImageView sprite, final ImageView description
            , final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager
            , final PlayerManager playerManager) {
        final double treatmentValue = parent.getTreatment() * coefficients.get(0);
        parent.setTreatment(treatmentValue);
        log.info("+10% BEFORE_TREATMENT");
    }

    @Override
    public final void showAnimation() {

    }
}
