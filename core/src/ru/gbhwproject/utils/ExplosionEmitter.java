package ru.gbhwproject.utils;

import ru.gbhwproject.base.Ship;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.ExplosionPool;
import ru.gbhwproject.sprites.Explosion;

public class ExplosionEmitter {

    private ExplosionPool explosionPool;
    private Rect worldBounds;

    public ExplosionEmitter(ExplosionPool explosionPool, Rect worldBounds) {
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    public void explose(Ship ship) {
        Explosion explosion = explosionPool.obtain();
        explosion.set(ship.pos);
    }

}
