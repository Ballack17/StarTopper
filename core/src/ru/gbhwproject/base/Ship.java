package ru.gbhwproject.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.pool.ExplosionPool;
import ru.gbhwproject.sprites.Bullet;
import ru.gbhwproject.sprites.Explosion;
import ru.gbhwproject.sprites.MainShip;
import ru.gbhwproject.utils.Regions;

public abstract class Ship extends Sprite{

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;
    protected Sound shootSound;
    protected int hp;
    protected float shootVolume;

    protected Vector2 v0;
    protected Vector2 v;

    protected float reloadInterval;
    protected float reloadTimer;
    protected float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    protected boolean shootable;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) throws GameException {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        if (getTop() > worldBounds.getTop() && v.y > - 0.04f) {
            pos.mulAdd(new Vector2(v.x,- 0.3f ),delta);
        } else {
            pos.mulAdd(v, delta);
            damageAnimateTimer += delta;
            if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
                frame = 0;
            }
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                if (this.shootable)
                shoot();
            }
        }
    }

    public void destroyBoom() {
    destroy();
    boom();
    }

    public void damage(int damage) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroyBoom();
        }
    }

    public int getDamage() {
        return damage;
    }

    private float getShootVolume() {
        return this.shootVolume;
    }

    private float ShootPositionModifer() {
        if (this.getClass().getName().equals("ru.gbhwproject.sprites.MainShip"))
            return 1f;
        else return -1f;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, new Vector2(pos.x, pos.y + this.getHalfHeight()*this.ShootPositionModifer()), bulletV, bulletHeight, worldBounds, damage);
        shootSound.play(this.getShootVolume());
    }

    protected void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > (getTop() - getHalfHeight()/1.1)
                || bullet.getTop() < (getBottom() + getHalfHeight()/1.1));
    }

}