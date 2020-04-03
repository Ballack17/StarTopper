package ru.gbhwproject.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;

public class Ship extends Sprite {

    private static  final float SPEED = 0.005f;
    private Rect worldBounds;

    private Vector2 temp;
    private Vector2 speedVector;
    private Vector2 movePoint;

    private boolean isMoving;

    public Ship(Texture  texture) throws GameException {
        super(new TextureRegion(texture));
        speedVector = new Vector2();
        movePoint = new Vector2();
        temp = new Vector2();
        isMoving = false;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update (float delta) {
        if (getRight() > worldBounds.getRight() && speedVector.x == 1) {speedVector.x = 0;}
        if (getLeft() < worldBounds.getLeft() && speedVector.x == -1) {speedVector.x =0;}
        if(!isMoving) {
            temp.x = movePoint.x - pos.x;
            if (Math.abs(temp.x) > SPEED) {
                pos.x += (speedVector.x * SPEED);
            } else {
                pos.x = movePoint.x;
                speedVector.x = 0;
            }
        }else {
            pos.x += (speedVector.x * SPEED);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        isMoving = false;
        this.movePoint.x = touch.x;
        speedVector.x = (movePoint.x - pos.x)/Math.abs(movePoint.x - pos.x);
        return false;
    }

    public boolean keyDown(int keycode) {
        switch(keycode) {
            case (22):
                speedVector.x = 0;
                isMoving = true;
                speedVector.x = 1;
                break;
            case (21):
                speedVector.x = 0;
                isMoving = true;
                speedVector.x = -1;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case (22):
                speedVector.x = 0;
                isMoving = false;
                movePoint.x = pos.x;
                break;
            case (21):
                speedVector.x = 0;
                isMoving = false;
                movePoint.x = pos.x;
                break;
        }
        return false;
    }
}
