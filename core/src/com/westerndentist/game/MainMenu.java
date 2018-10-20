package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu extends Stage {
    MainMenu(Viewport viewport) {
        super(viewport);
        splashScreen();
    }

    public void splashScreen() {
        Image splash = new Image(new Texture("images/JEGA.jpg"));
        SequenceAction sequence = Actions.sequence();
        sequence.addAction(Actions.alpha(0));
        sequence.addAction(Actions.delay(1));
        sequence.addAction(Actions.fadeIn(1));
        sequence.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                Sound intro = Gdx.audio.newSound(Gdx.files.internal("sounds/JEGA.mp3"));
                intro.play(1.0f);
            }
        }));
        sequence.addAction(Actions.delay(2.5f));
        sequence.addAction(Actions.fadeOut(1));
        sequence.addAction(Actions.delay(1));
        splash.addAction(sequence);
        addActor(splash);
    }
}