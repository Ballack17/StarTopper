package ru.gbhwproject.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.math.Rect;
import ru.gbhwproject.math.Rnd;
import ru.gbhwproject.pool.EnemyPool;
import ru.gbhwproject.sprites.enemies.Enemy;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.08f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.4f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_HP = 1;
    private static final float ENEMY_SMALL_SHOOT_VOLUME = 0.1f;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.125f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.55f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_HP = 5;
    private static final float ENEMY_MEDIUM_SHOOT_VOLUME = 0.4f;

    private static final float ENEMY_BIG_HEIGHT = 0.15f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.75f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_BIG_HP = 10;
    private static final float ENEMY_BIG_SHOOT_VOLUME = 0.9f;

    private Rect worldBounds;
    private Sound shootSound;
    private TextureRegion bulletRegion;

    private float generateInterval = 1.5f;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegion;
    private final TextureRegion[] enemyMediumRegion;
    private final TextureRegion[] enemyBigRegion;

    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;

    private final EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds, Sound shootSound) {
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0, 1, 2, 2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegion = Regions.split(enemy1, 1, 2, 2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(enemy2, 1, 2, 2);
        this.enemySmallV = new Vector2(0, -0.2f);
        this.enemyMediumV = new Vector2(0, -0.03f);
        this.enemyBigV = new Vector2(0, -0.005f);
    }


    public void generate(float delta) {
        float type = (float) Math.random();
        if (type < 0.995) {generateSolo(delta);}
        else {generateSeries(delta);}
    }

    public void generateSolo(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                smallSet(enemy);
            } else if (type < 0.8f) {
                middleSet(enemy);
            } else {
                bigSet(enemy);
            }
            setPos(enemy);
        }
    }

    public void generateSeries(float delta) {
        int type = (int) Rnd.nextFloat(3f,7f);
        float vx = Rnd.nextFloat(-0.3f, 0.3f);
        float vy = Rnd.nextFloat(-0.3f, -0.5f);
        for (int i = 0; i < type; i++) {
            Enemy enemy = enemyPool.obtain();
            smallSetSerie(enemy, new Vector2(vx, vy));
            setPos(enemy);
            enemy.pos.x = enemy.pos.x + enemy.getWidth()*i;
            enemy.pos.y = enemy.pos.y + enemy.getHeight()*i;
        }

    }

    private void smallSet(Enemy enemy) {
        enemy.set(
                enemySmallRegion,
                enemySmallV,
                bulletRegion,
                ENEMY_SMALL_BULLET_HEIGHT,
                ENEMY_SMALL_BULLET_VY,
                ENEMY_SMALL_DAMAGE,
                ENEMY_SMALL_RELOAD_INTERVAL,
                shootSound,
                ENEMY_SMALL_SHOOT_VOLUME,
                ENEMY_SMALL_HP,
                ENEMY_SMALL_HEIGHT,
                true
                );
    }
    private void smallSetSerie(Enemy enemy, Vector2 v) {
        enemy.set(
                enemySmallRegion,
                v,
                bulletRegion,
                ENEMY_SMALL_BULLET_HEIGHT,
                ENEMY_SMALL_BULLET_VY,
                ENEMY_SMALL_DAMAGE,
                ENEMY_SMALL_RELOAD_INTERVAL,
                shootSound,
                ENEMY_SMALL_SHOOT_VOLUME,
                ENEMY_SMALL_HP,
                0.06f,
                false
        );
    }

    private void middleSet(Enemy enemy){
        enemy.set(
                enemyMediumRegion,
                enemyMediumV,
                bulletRegion,
                ENEMY_MEDIUM_BULLET_HEIGHT,
                ENEMY_MEDIUM_BULLET_VY,
                ENEMY_MEDIUM_DAMAGE,
                ENEMY_MEDIUM_RELOAD_INTERVAL,
                shootSound,
                ENEMY_MEDIUM_SHOOT_VOLUME,
                ENEMY_MEDIUM_HP,
                ENEMY_MEDIUM_HEIGHT,
                true
                );
        }

    private void bigSet(Enemy enemy) {
        enemy.set(
                enemyBigRegion,
                enemyBigV,
                bulletRegion,
                ENEMY_BIG_BULLET_HEIGHT,
                ENEMY_BIG_BULLET_VY,
                ENEMY_BIG_DAMAGE,
                ENEMY_BIG_RELOAD_INTERVAL,
                shootSound,
                ENEMY_BIG_SHOOT_VOLUME,
                ENEMY_BIG_HP,
                ENEMY_BIG_HEIGHT,
                true
                );
        }

    private void setPos(Enemy enemy){
        enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
        enemy.setBottom(worldBounds.getTop());
        }

    }

