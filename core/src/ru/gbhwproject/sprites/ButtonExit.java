package ru.gbhwproject.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gbhwproject.base.ScaledButton;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;

public class ButtonExit extends ScaledButton {
    public ButtonExit(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setRight(worldBounds.getRight() - 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
