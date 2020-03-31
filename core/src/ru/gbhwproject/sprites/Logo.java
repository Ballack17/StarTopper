package ru.gbhwproject.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;

public class Logo extends Sprite {

    private static  final float SPEED = 0.007f;

    private Vector2 temp;
    private Vector2 speedVector;
    private Vector2 movePoint;

    public Logo(Texture  texture) throws GameException {
        super(new TextureRegion(texture));
        speedVector = new Vector2();
        movePoint = new Vector2();
        temp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.06f);
    }

    @Override
    public void update (float delta) {
        temp.set(movePoint);
        if(temp.sub(pos).len() > SPEED){
            pos.add(speedVector);
        } else {
            pos.set(movePoint);
            speedVector.setZero();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.movePoint = touch;
        speedVector.set(movePoint.cpy().sub(pos)).setLength(SPEED);
        return false;
    }
}
