package ru.gbhwproject.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setTop(0.1f);
    }
}