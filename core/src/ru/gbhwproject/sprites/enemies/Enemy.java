package ru.gbhwproject.sprites.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Ship;
import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.math.Rnd;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.pool.ExplosionPool;

public class Enemy extends Ship {

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getRight() >= worldBounds.getRight()){
            v.x = - v.x;
        }
        if (getLeft() <= worldBounds.getLeft()) {
            v.x = - v.x;
        }
        if (getTop() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound shootSound,
            float shootVolume,
            int hp,
            float height,
            boolean shootable
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.shootSound = shootSound;
        this.shootVolume = shootVolume;
        this.hp = hp;
        this.v.set(v0);
        this.shootable = shootable;
        setHeightProportion(height);
    }
}