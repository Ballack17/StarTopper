package ru.gbhwproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarTopperHarley extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img_BG;
	//TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img_BG = new Texture("spaceBG.jpg");
		//region = new TextureRegion(img, 10,10,15,15);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img_BG,-120,-120);
		batch.draw(img, 0, 0,75,75);
		//batch.draw(region,150,150);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		img_BG.dispose();
	}
}
