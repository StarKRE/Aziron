package bonus.devourerBonuses.bonuses.attack.fusedFromBowel;

import heroes.abstractHero.tallents.abstractSkill.AbstractSkill;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import management.actionManagement.ActionManager;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

final class FusedFromBowelSkillProxyComponent {

    private Player player;

    FusedFromBowelSkillProxyComponent(final Player player) {
        this.player = player;
    }

    final void packSkills(final List<Integer> indexes, final ActionManager actionManager) {
        final heroes.abstractHero.hero.Hero currentHero = player.getCurrentHero();
        final List<Skill> skills = currentHero.getCollectionOfSkills();
        final AGraphicLocation location = player.getCurrentHero().getGraphicLocation();
        final ObservableList<Node> mainSkillContainers = location.getSkillPane().getChildren();

        final List<Node> copyObservableList = new ArrayList<>(){{
            addAll(mainSkillContainers);
        }};

        for (final int index : indexes){
            final AbstractSkill armageddonSkill = new ArmageddonSkill(actionManager);
            final Pane armageddonSkillContainer = armageddonSkill.getContainer();
            armageddonSkillContainer.setLayoutX(mainSkillContainers.get(index).getLayoutX());
            armageddonSkillContainer.setLayoutY(mainSkillContainers.get(index).getLayoutY());
            skills.set(index, armageddonSkill);
            copyObservableList.set(index, armageddonSkillContainer);
        }
        mainSkillContainers.clear();
        mainSkillContainers.addAll(copyObservableList);
    }
}