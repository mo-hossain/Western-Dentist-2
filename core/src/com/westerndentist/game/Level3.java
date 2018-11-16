package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Level3 extends Stage {

    private WesternDentist game;
    private Image background = new Image(new Texture("Images/WesternDentist_L3_Background.png"));
    private Image background2 = new Image(new Texture("Images/WesternDentist_L3_Background.png"));
    private Boolean bossTime, noBoss;

    Level3 (final WesternDentist game, final FitViewport viewport) {
        super(viewport);

        // Load level
        // Load UI and Background
        Image ui = new Image(new Texture("Images/WesternDentist_UI.png"));

        // Set Z axis for UI and set background name
        ui.setName("UI_FRAME");
        ui.setZIndex(300000);
        background.setName("BACKGROUND");

        // Add background, player, UI
        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);
        addActor(background);
        addActor(background2);
        addActor(new Player(300, 300));
        addActor(ui);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bossTime = true;
            }},  60000);

        bossTime = false;
        noBoss = true;
        this.game = game;
    }

    @Override
    public void draw() {
        sortActors();
        super.draw();
    }

    @Override
    public void act(float delta) {
        backgroundScrolling(delta);
        if (!bossTime)
            spawnEnemies();
        if (bossTime && noBoss)
            spawnBoss();
        if (bossTime && !noBoss)
            if (bossIsDead())
            {
                // Player beat the level
                // TODO: Play victory Sound?
                // TODO: write code to change level
                game.changeStage(new MainMenu(game, (FitViewport) getViewport()));
            }
        super.act(delta);
    }

    private Boolean bossIsDead()
    {
        for(Actor actor : this.getActors())
        {
            if (L3Boss.class.isInstance(actor))
                return false;
        }
        return true;
    }

    private void sortActors() {
        for (Actor actor : this.getActors()) {
            int z = actor.getZIndex();
            if (Image.class.isInstance(actor) && actor.getName() == "UI_FRAME") {
                if (actor.getZIndex() != 300000) {
                    actor.setZIndex(300000);
                }
            }
            else if (Image.class.isInstance(actor) && actor.getName() == "BACKGROUND") {
                actor.setZIndex(0);
            }
            else if (Player.class.isInstance(actor)) {
                actor.setZIndex(3000);
            }
            else if (L3Boss.class.isInstance(actor))
            {
                actor.setZIndex(3000);
            }
            else if (z > 3000) {
                actor.setZIndex(z - 3000);
            }
            else {
                actor.setZIndex(1);
            }
        }
    }

    private void spawnEnemies() {
        double decider = Math.random() * (1000 - 0);

        if (decider > 960) {
            Gdx.app.log("Level 3", "Spawned Enemy");
            double enemyType = Math.random() * 1000;
            if (enemyType < 250)
                addActor(new BasicEnemy(new Texture("Images/RocketEnemy.png"), 500, 40, 100, new Vector2((float)Math.random() * (520-64), 800)));
            else
                addActor(new BasicEnemy(new Texture("Images/AsteroidEnemy.png"), 200, 50, 100, new Vector2((float)Math.random() * (520-64), 800)));
        }
    }

    private void spawnBoss()
    {
        Gdx.app.log("Level 3", "Mr. Muskrat has Arrived");
        addActor(new L3Boss(getViewport().getScreenWidth()/2 - 180, getViewport().getScreenHeight()));
        noBoss = false;
    }

    private void backgroundScrolling(float delta) {
        background.setY(background.getY() + 30 * delta);
        background2.setY(background2.getY() + 30 * delta);

        if (background.getY() >= 5000) {
            background.setY(-5000);
        }

        if (background2.getY() >= 5000) {
            background2.setY(-5000);
        }
    }
}