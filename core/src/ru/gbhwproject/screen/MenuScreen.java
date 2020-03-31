package ru.gbhwproject.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.BaseScreen;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.sprites.Background;
import ru.gbhwproject.sprites.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture lg;
    private Logo logo;
    private Background background;
    private Vector2 pos;
    private Vector2 pos2;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        try {
            background = new Background(bg);
        } catch (GameException e) {throw new RuntimeException(e);}
        pos = new Vector2();

        lg = new Texture("Starship32.png");
        try {
            logo = new Logo(lg);
        } catch (GameException e) {throw new RuntimeException(e);}
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        lg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        logo.update(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

}

