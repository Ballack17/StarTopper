package ru.gbhwproject.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gbhwproject.base.Ship;
import ru.gbhwproject.base.Sprite;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.ExplosionPool;
import ru.gbhwproject.utils.Regions;

public class Explosion extends Sprite {

    private boolean isActive = false;
    private Rect worldBounds;
    private TextureRegion explosionRegion;
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));

    public Explosion() throws GameException {
        explosionRegion = atlas.findRegion("explosion");
        this.regions = Regions.split(explosionRegion, 9, 9, 75);
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);

   }

    @Override
    public void update(float delta) {
        if (animationLong >= 60 / this.regions.length) {
            frame = (frame + 1);
            animationLong = 0;
            if (frame == 74) { destroy(); frame = 0;}
        } else {
            animationLong += 1;
        }
    }

    public void set (Vector2 pos)
       {this.pos = pos;}
}
