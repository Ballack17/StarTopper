package ru.gbhwproject.pool;

import ru.gbhwproject.base.SpritesPool;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.sprites.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
    @Override
    protected Explosion newObject() {
        try {
            return new Explosion();
        } catch (GameException e) {
            e.printStackTrace();
        }
        return null;
    }
}
