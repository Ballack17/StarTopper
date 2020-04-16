package ru.gbhwproject.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.math.Rnd;

public class Star extends Sprite {



    private Vector2 v;
    private float vDefault;
    private Rect worldBounds;

    private float animateInterval = 0.5f;
    private float animateTimer;
    private float height;

    private static final int INVALID_POINTER = -1;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public Star(TextureAtlas atlas) throws GameException {
        super(atlas.findRegion("star"));
        height = Rnd.nextFloat(0.003f, 0.012f);
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.03f, -0.06f);
        v = new Vector2(vx, vy);
        vDefault = v.x;
        animateTimer = Rnd.nextFloat(0, 0.5f);
    }

    public Star(TextureAtlas atlas, boolean speed) throws GameException {
        super(atlas.findRegion("star"));
        height = Rnd.nextFloat(0.004f, 0.008f);
        float vx = Rnd.nextFloat(-0.06f, 0.1f);
        float vy = Rnd.nextFloat(-0.5f, -2f);
        v = new Vector2(vx, vy);
        animateTimer = Rnd.nextFloat(0, 0.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(height);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        this.pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        scale += 0.01f;
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0;
            scale = 1;
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
    }

    public void setV(float vx) {
        this.v.x = v.x + vx;
    }

    public void setvDefault() {this.v.x = vDefault;}

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
            if (touch.x < worldBounds.pos.x) {
                if (leftPointer != INVALID_POINTER) {
                    return false;
                }
                leftPointer = pointer;
                setV(0.3f);
            } else {
                if (rightPointer != INVALID_POINTER) {
                    return false;
                }
                rightPointer = pointer;
                setV(-0.3f);
            }
            return false;
        }


    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                setV(-0.3f);
            } else {
                setvDefault();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                setV(0.3f);
            } else {
                setvDefault();
            }
        }
        return false;
    }
}
