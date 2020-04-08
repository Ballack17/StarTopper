package ru.gbhwproject.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.gbhwproject.base.BaseScreen;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.pool.EnemyPool;
import ru.gbhwproject.sprites.Background;
import ru.gbhwproject.sprites.Bullet;
import ru.gbhwproject.sprites.MainShip;
import ru.gbhwproject.sprites.Star;
import ru.gbhwproject.sprites.enemies.Enemy;
import ru.gbhwproject.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 96;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas atlasShip;

    private Star[] stars;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private MainShip mainShip;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlasShip = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds, bulletSound);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        initSprites();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
     }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        super.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void initSprites() {
        try {
            background = new Background(bg);
            stars = new Star[STAR_COUNT];
            for (int i = 0; i < STAR_COUNT; i++) {
                if (i < STAR_COUNT - 32){stars[i] =  new Star(atlas);}
                else {stars[i] = new Star(atlas, true);}
            }
            mainShip = new MainShip(atlasShip, bulletPool, laserSound);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet: bulletList) {
                if (bullet.isDestroyed()) {
                    continue;
                }
                float hitDist = enemy.getHalfWidth() + bullet.getHalfHeight();
                if (bullet.getOwner().getClass().getName().equals("ru.gbhwproject.sprites.MainShip")) {
                    if (enemy.pos.dst(bullet.pos) < hitDist) {
                        enemy.destroy();
                        explosionSound.play(0.03f, 1.3f, 0f);
                        bullet.destroy();
                    }
                }
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth() - 0.015f;
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();
            }
        }
    }

    public void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        enemyPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
