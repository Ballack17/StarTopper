package ru.gbhwproject.pool;

import ru.gbhwproject.base.SpritesPool;
import ru.gbhwproject.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

}