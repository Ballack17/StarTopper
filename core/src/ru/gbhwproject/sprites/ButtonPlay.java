package ru.gbhwproject.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gbhwproject.base.ScaledButton;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) throws GameException {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }


    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
