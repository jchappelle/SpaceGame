package com.jchappelle.sg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.audio.MusicId;

class MainMenuScreen extends ScreenAdapter {

    private Stage stage;
    private GameManager gameMgr;

    public MainMenuScreen(GameManager screenMgr){
        this.gameMgr = screenMgr;

        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show(){
        gameMgr.getAudioManager().playMusic(MusicId.Theme1);
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        TextButton newGame = menuButton("New Game", skin, ScreenId.GAME);
        TextButton preferences = menuButton("Preferences", skin, ScreenId.PREFERENCES);
        TextButton exit = menuButton("Exit", skin, null);

        table.add(new Label( "Space Game", skin)).center();
        table.row().expandY();
        table.row().pad(5);
        table.row().pad(5);
        table.row().pad(5);
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferences).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
    }

    private TextButton menuButton(String label, Skin skin, final ScreenId destinationScreenId){
        TextButton result = new TextButton(label, skin);
        result.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(destinationScreenId == null){
                    Gdx.app.exit();
                }
                else{
                    gameMgr.changeScreen(destinationScreenId);
                }
            }
        });
        return result;
    }

    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    @Override
    public void hide(){
        super.hide();
    }
}
