package management.pipeline.pipeNodes.defaultPipeNode;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.AbstractPipeNode;
import management.pipeline.pipes.APipe;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponent.EngineComponent;

public abstract class DefaultPipeNode extends AbstractPipeNode {

    protected APipeline pipeline;

    protected PlayerManager playerManager;

    protected Hero hero;

    public DefaultPipeNode(final Hero hero, final PlayerManager playerManager, final APipeline pipeline){
        this.playerManager = playerManager;
        this.pipeline = pipeline;
        this.hero = hero;
    }

    @Override
    public final ActionEvent handleEvent(final ActionEvent actionEvent) {
        for (final EngineComponent engineComponent : this.engineComponentList) {
            engineComponent.handle(actionEvent);
        }
        for (final APipe pipe : this.innerPipeMap.values()){
            pipe.push(actionEvent);
        }
        return actionEvent;
    }

    @Override
    public final Hero getHero() {
        return this.hero;
    }
}