package ru.gbhwproject.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.math.Rnd;

public class EnemyShipLv1 extends Sprite {

    private final float HEIGHT = 0.05f;

    private Vector2 v;
    private Rect worldBounds;

    public EnemyShipLv1(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("enemy0"), 1, 2, 1);
        float vx = Rnd.nextFloat(-0.01f, 0.2f);
        float vy = Rnd.nextFloat(-0.1f, -0.4f);
        v = new Vector2(vx, vy);
    }

    public void set(Vector2 pos0, float height, Rect worldBounds)
    {this.pos.set(pos0);
     setHeightProportion(height);
     this.worldBounds = worldBounds;}

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        float posX = Rnd.nextFloat(worldBounds.getLeft() + getWidth(), worldBounds.getRight() - getWidth());
        float posY = worldBounds.getTop();
        this.pos.set(posX, posY + getHalfHeight());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getTop() < worldBounds.getBottom()) {
            destroy();
        }
        if (getRight() > worldBounds.getRight()) {
            v.set(-v.x, v.y);
        }
        if (getLeft() < worldBounds.getLeft()) {
            v.set(-v.x, v.y);
        }
    }
}