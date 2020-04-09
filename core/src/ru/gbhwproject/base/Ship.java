package ru.gbhwproject.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.sprites.Bullet;
import ru.gbhwproject.utils.Regions;

public abstract class Ship extends Sprite{


    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected Sound shootSound;
    protected int hp;
    protected float shootVolume;
    protected boolean shootable;

    protected Vector2 v0;
    protected Vector2 v;

    protected float reloadInterval;
    protected float reloadTimer;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) throws GameException {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() > worldBounds.getTop() && v.y > - 0.04f) {
            pos.mulAdd(new Vector2(v.x,- 0.3f ),delta);
        } else {
            pos.mulAdd(v, delta);
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                if (this.shootable)
                shoot();
            }
        }
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

}