package ru.gbhwproject.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gbhwproject.base.ScaledButton;
import ru.gbhwproject.exception.GameException;
import ru.gbhwproject.math.Rect;
import ru.gbhwproject.pool.BulletPool;
import ru.gbhwproject.pool.EnemyPool;
import ru.gbhwproject.pool.ExplosionPool;
import ru.gbhwproject.sprites.MainShip;
import ru.gbhwproject.screen.GameScreen;
import ru.gbhwproject.sprites.enemies.Enemy;

public class ButtonNewGame extends ScaledButton {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private MainShip mainShip;
    private boolean gameState;


    public ButtonNewGame(TextureAtlas atlas,BulletPool bulletPool, EnemyPool enemyPool, ExplosionPool explosionPool,MainShip mainShip) throws GameException {
        super(atlas.findRegion("button_new_game"));
        this.bulletPool = bulletPool;
        this.enemyPool = enemyPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
        gameState = true;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.07f);
        setBottom(- 0.1f);
    }

    @Override
    public void action() {
        System.out.println("NewGame");
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        mainShip.setHp(100);
        gameState = true;
    }

    public boolean isGameStarted(){
        return gameState;
    }
    public void gameStateSwitch(){
        if (isGameStarted()){
            gameState = false;
        } else { gameState = true;}
    }
}
