package ru.gbhwproject.sprites.bonuses;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;

public class PowerUp extends Sprite {
    public PowerUp(TextureRegion region) throws GameException {
        super(region);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
