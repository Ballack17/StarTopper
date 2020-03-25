package ru.gbhwproject.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture imgBg;
    private Vector2 pos;
    private float speed;
    private Vector2 speedVector;
    private Vector2 newpos;
    private float deltaupdate;
    private float moving;
    private float speedOfRotation;
    private boolean isRotating;
    private float rotate;

    @Override
    public void show() {
        super.show();
        img = new Texture("Starship32.png");
        imgBg = new Texture("spaceBG.jpg");
        pos = new Vector2(300,200);
        newpos = new Vector2();
        speedVector = new Vector2();
        speed = 5f;
        speedOfRotation = 1f;
        isRotating = false;
        rotate = 0;
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();


    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0) // press left button to move
        {
            deltaupdate = 0f;
            moving = 0f;
            newpos.set(screenX, Gdx.graphics.getHeight() - screenY);
            speedVector = new Vector2(newpos.x - pos.x, newpos.y - pos.y);
            deltaupdate = speedVector.len();
            speedVector.set(speedVector.x * speed / deltaupdate, speedVector.y * speed / deltaupdate);
        }
        if (button == 1) // press right button to start or stop rotation
        {
            if (isRotating) {isRotating = false;}
            else isRotating = true;
        }
        return false;
    }

    @Override  // Я не устоял не добавить :)    scroll to change the speed!
    public boolean scrolled(int amount) {
        speedOfRotation += amount;
        return false;
    }

    private void update(float delta) {
        if (moving <= deltaupdate){
            pos.add(speedVector);
            moving += speed;}
        if (isRotating){
            rotate += speedOfRotation;
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgBg,-120,-160);
        batch.draw(new TextureRegion(img), pos.x - img.getWidth()/2, pos.y - img.getHeight()/2, img.getWidth()/2, img.getHeight()/2, img.getWidth(), img.getHeight(), 1.5f, 1.5f,rotate);
        batch.end();
    }

}

