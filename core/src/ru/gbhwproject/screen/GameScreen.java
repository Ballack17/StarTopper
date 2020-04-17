package ru.gbhwproject.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.gbhwproject.base.BaseScreen;
import ru.gbhwproject.base.Font;
import ru.gbhwproject.pool.ExplosionPool;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.pool.EnemyPool;
import ru.gbhwproject.sprites.Background;
import ru.gbhwproject.sprites.Bullet;
import ru.gbhwproject.sprites.ButtonExit;
import ru.gbhwproject.sprites.ButtonNewGame;
import ru.gbhwproject.sprites.GameOver;
import ru.gbhwproject.sprites.MainShip;
import ru.gbhwproject.sprites.Star;
import ru.gbhwproject.sprites.enemies.Enemy;
import ru.gbhwproject.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    public enum State {PLAYING, PAUSE, GAME_OVER}

    private static final int STAR_COUNT = 96;
    private TextureAtlas atlasShip;
    private static final float FONT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final String SCORE = "Score: ";
    private static final String HIGHSCORE = "High Score: ";

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas atlasMenu;

    private Star[] stars;
    private MainShip mainShip;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;
    private ButtonExit buttonExit;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosion;
    private State state;
    private State prevState;

    private int score;
    public  int highScore;
    public Preferences prefs;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;
    private StringBuilder sbScore;
    private StringBuilder sbHighscore;

    private int frags;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        atlasShip = new TextureAtlas(Gdx.files.internal("textures/gameAtlas.pack"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosion);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds, bulletSound);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        sbScore = new StringBuilder();
        sbHighscore = new StringBuilder();

        score=0;
        prefs = Gdx.app.getPreferences("My Preferences"); //получаем файл персональных данных
        highScore = prefs.getInteger("highscore"); //получаем текущий лучший результат
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        initSprites();
        state = State.PLAYING;
    }

    public void startNewGame() {
        state = State.PLAYING;
        mainShip.startNewGame(worldBounds);
        frags = 0;
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        score = 0;
    }

    @Override
    public void pause() {
        prevState = state;
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        state = prevState;
        music.play();
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
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonExit.resize(worldBounds);
     }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        atlasShip.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        laserSound.dispose();
        explosion.dispose();
        font.dispose();
        prefs.putInteger("highscore", highScore);
        prefs.flush(); //убедиться, что настройки сохранены
        super.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
            for (Star star: stars) {star.touchDown(touch, pointer, button);}
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        }
        return false;
    }



    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
            for (Star star: stars) {star.touchUp(touch, pointer, button);}
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch,pointer,button);
        }
        return false;
    }


    private void initSprites() {
        try {
            background = new Background(bg);
            mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
            stars = new Star[STAR_COUNT];
            for (int i = 0; i < STAR_COUNT; i++) {
                if (i < STAR_COUNT - 32){stars[i] =  new Star(atlas);}
                else {stars[i] = new Star(atlas, true);}
            }
            mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
            gameOver = new GameOver(atlas);
            buttonExit = new ButtonExit(atlasMenu);
            buttonNewGame = new ButtonNewGame(atlas, this);

        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.update(delta);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth() - 0.005f;
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroyBoom();
                frags++;
                score += enemy.getScore();
                if (score > highScore) {highScore = score;}
                mainShip.damage(enemy.getDamage());
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                        score += enemy.getScore();
                        if (score > highScore) {highScore = score;}
                    }
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.getHp()==0) {
            state = State.GAME_OVER;
        }
    }

    public void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }

        switch (state) {
            case PLAYING:
                mainShip.draw(batch);
                enemyPool.drawActiveSprites(batch);
                bulletPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                gameOver.draw(batch);
                buttonNewGame.draw(batch);
                buttonExit.draw(batch);
                break;
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        sbScore.setLength(0);
        sbHighscore.setLength(0);
        font.draw(batch, sbHighscore.append(HIGHSCORE).append(highScore), worldBounds.getRight() - FONT_MARGIN, worldBounds.getBottom() + 0.05f, Align.right);
        font.draw(batch, sbScore.append(SCORE).append(score), worldBounds.getLeft() + FONT_MARGIN, worldBounds.getBottom() + 0.05f);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + FONT_MARGIN, worldBounds.getTop() - FONT_MARGIN);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - FONT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - FONT_MARGIN, worldBounds.getTop() - FONT_MARGIN, Align.right);
    }

}
