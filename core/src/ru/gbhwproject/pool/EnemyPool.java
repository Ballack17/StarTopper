package ru.gbhwproject.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gbhwproject.base.SpritesPool;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.sprites.enemies.EnemyShipLv1;

public class EnemyPool extends SpritesPool<EnemyShipLv1> {

    @Override
    protected EnemyShipLv1 newObject() {
        try {
            return new EnemyShipLv1(new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack")));
        } catch (GameException e) {
            e.printStackTrace();
        }
        return null;
    }
}
