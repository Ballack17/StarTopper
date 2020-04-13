package ru.gbhwproject.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

    private static final float ANIMATE_INTERVAL = 0.017f;

    private float animateTimer;

    private Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound) throws GameException {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.explosionSound = explosionSound;
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
