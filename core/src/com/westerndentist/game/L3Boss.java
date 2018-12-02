package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class L3Boss extends Boss
{
    private Texture texture = new Texture("Images/Boss_L3.png");
    private Vector2 movement = new Vector2(0, 0);
    private Rectangle bounds = new Rectangle();

    private float speed = -50;
    private float fireRate = 10;
    private int rateCounter = 0;

    private int fireX1 = 0;
    private int fireY1 = 1000;
    private int XScale1 = 100;
    private int YScale1 = 100;
    private int fireX2 = 0;
    private int fireY2 = -1000;
    private int XScale2 = 100;
    private int YScale2 = 100;
    private int fireX3 = 0;
    private int fireY3 = -1000;
    private int XScale3 = 0;
    private int YScale3 = 0;
    private int fireX4 = 0;
    private int fireY4 = 1000;
    private int XScale4 = 0;
    private int YScale4 = 0;
    private Boolean phaseChange = true;
    private int phase = 1;
    private int prevPhase = 1;
    private float score = 10000000;

    private boolean checkLeft = true, checkRight = true, checkTop = true, checkBottom = true;

    /**
     * Empty Constructor
     */
    L3Boss() {
        setPosition(0, 0);
        setBounds(0, 0, texture.getWidth(), texture.getHeight());
        setName("Mr. Muskrat");
        health = 20000;
        // TODO: Boss Spawn Sound?
    }

    /**
     * Overriden Constructor with position vector
     * @param position
     */
    L3Boss(Vector2 position) {
        setPosition(position.x, position.y);
        setBounds(position.x, position.y, texture.getWidth(), texture.getHeight());
        setName("Mr. Muskrat");
        health = 20000;
    }

    /**
     * overriden constructor with position coordinates
     * @param x
     * @param y
     */
    L3Boss(float x, float y) {
        setPosition(x, y, Align.center);
        bounds.set(x, y, texture.getWidth()/2, texture.getHeight()/2);
        bounds.setCenter(x + texture.getWidth() / 4, y - texture.getHeight() / 4);
        setName("Mr. Muskrat");
        health = 20000;
    }

    /**
     * Draw this object on the screen
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    /**
     * Draw this object with a highlighted hitbox
     * @param shapes
     */
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    /**
     * Perform the necessary actions for each frame
     * @param delta
     */
    @Override
    public void act(float delta) {
        score -= 1100 * delta;
        move();
        applyMovement(delta);
        if(getY() <= 350)
        {
            if(health>15000)
                firePhase1(delta);
            else if (health>10000)
                firePhase2(delta);
            else if (health>5000)
                firePhase3(delta);
            else
                firePhase4(delta);
        }
        updateBounds();
        checkCollision();
        killOnDead();
    }

    /**
     * Destroy this object if health drops below 0
     */
    private void killOnDead()
    {
        if (health <= 0)
        {
            this.giveScore();
            remove();
            Gdx.app.log("Mr. Muskrat", "That wasn\'t very Cash Money of you");
        }
    }

    /**
     * When This object is destroyed, add an appropriate score to the player
     */
    @Override
    protected void giveScore() {
        for (Actor actor: getStage().getActors()) {
            if (Player.class.isInstance(actor)) {
                ((Player)actor).addScore(this.score);
                break;
            }
        }
    }

    /**
     * Set the internal movement vector
     */
    private void move() {
        if (getY() > 250)
            movement.y += speed;
        else
            movement.y = 0;
    }

    /**
     * apply appropriate movement to this object based on its movement vector
     * @param delta
     */
    private void applyMovement(float delta) {
        movement.x *= delta;
        movement.y *= delta;
        moveBy(movement.x, movement.y);
        setPosition(getX() + movement.x, getY() + movement.y);

        movement = new Vector2(0, 0);
    }

    /**
     * Logic for the first phase projectile firing
     * @param delta
     */
    private void firePhase1(float delta) {
        prevPhase = phase;
        phase = 1;
        if (prevPhase != phase)
        {
            fireX1 = 0;
            fireY1 = 1000;
            XScale1 = 100;
            YScale1 = 100;
            fireX2 = 0;
            fireY2 = -1000;
            XScale2 = 100;
            YScale2 = 100;
            fireX3 = 0;
            fireY3 = -1000;
            fireX4 = 0;
            fireY4 = 1000;
        }

        if (rateCounter == 0) {
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX1/(float)1000, (float)fireY1/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX2/(float)1000, (float)fireY2/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX3/(float)1000, (float)fireY3/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX4/(float)1000, (float)fireY4/(float)1000, true));
            fireX1 += XScale1;
            fireY1 += YScale1;
            fireX2 += XScale2;
            fireY2 += YScale2;
            fireX3 -= XScale2;
            fireY3 += YScale2;
            fireX4 -= XScale1;
            fireY4 += YScale1;

            // Cycle Bottom to Left & back
            if (fireX1 < -1000 || fireX1 > 1000)
                XScale1 *= -1;
            if (fireY1 < -1000 || fireY1 > 1000)
                YScale1 *= -1;
            if (fireX2 < -1000 || fireX2 > 1000)
                XScale2 *= -1;
            if (fireY2 < -1000 || fireY2 > 1000)
                YScale2 *= -1;

            rateCounter += fireRate;
        }
        if (rateCounter != 0) {
            --rateCounter;
        }

        if (rateCounter < 0) {
            rateCounter = 0;
        }
    }

    /**
     * Logic for phase 2 projectile firing
     * @param delta
     */
    private void firePhase2(float delta)
    {
        prevPhase = phase;
        phase = 2;
        if (prevPhase != phase)
        {
            fireX1 = 0;
            fireY1 = 1000;
            XScale1 = 100;
            YScale1 = 100;
            fireX2 = 0;
            fireY2 = -1000;
            XScale2 = -100;
            YScale2 = -100;
            fireX3 = 1000;
            fireY3 = 0;
            XScale3 = -100;
            YScale3 = -100;
            fireX4 = -1000;
            fireY4 = 0;
            XScale4 = 100;
            YScale4 = 100;
        }

        if (rateCounter == 0) {
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX1/(float)1000, (float)fireY1/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX2/(float)1000, (float)fireY2/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX3/(float)1000, (float)fireY3/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX4/(float)1000, (float)fireY4/(float)1000, true));

            fireX1 += XScale1;
            fireY1 += YScale1;
            fireX2 += XScale2;
            fireY2 += YScale2;
            fireX3 += XScale3;
            fireY3 += YScale3;
            fireX4 += XScale4;
            fireY4 += YScale4;

            // Cycle Bottom to Left & back
            if (fireX1 < -1000 || fireX1 > 1000)
                XScale1 *= -1;
            if (fireY1 < -1000 || fireY1 > 1000)
                YScale1 *= -1;
            if (fireX2 < -1000 || fireX2 > 1000)
                XScale2 *= -1;
            if (fireY2 < -1000 || fireY2 > 1000)
                YScale2 *= -1;
            if (fireX3 < -1000 || fireX3 > 1000)
                XScale3 *= -1;
            if (fireY3 < -1000 || fireY3 > 1000)
                YScale3 *= -1;
            if (fireX4 < -1000 || fireX4 > 1000)
                XScale4 *= -1;
            if (fireY4 < -1000 || fireY4 > 1000)
                YScale4 *= -1;


            rateCounter += fireRate;
        }
        if (rateCounter != 0) {
            --rateCounter;
        }

        if (rateCounter < 0) {
            rateCounter = 0;
        }
    }

    /**
     * logic for phase 3 projectile firing
     * @param delta
     */
    private void firePhase3(float delta)
    {
        prevPhase = phase;
        phase = 3;
        if (prevPhase != phase)
        {
            fireX1 = 0;
            fireY1 = 1000;
            XScale1 = 500;
            YScale1 = 500;
            fireX2 = 0;
            fireY2 = -1000;
            XScale2 = -500;
            YScale2 = -500;
            fireX3 = 1000;
            fireY3 = 0;
            XScale3 = -500;
            YScale3 = -500;
            fireX4 = -1000;
            fireY4 = 0;
            XScale4 = 500;
            YScale4 = 500;
        }

        if (rateCounter == 0) {
            if ((Math.random() * (1000 - 0)) > 500)
            {
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX1 / (float) 1000, (float) fireY1 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX2 / (float) 1000, (float) fireY2 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX3 / (float) 1000, (float) fireY3 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX4 / (float) 1000, (float) fireY4 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 500 / (float) 1000, (float) 500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -500 / (float) 1000, (float) 500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 500 / (float) 1000, (float) -500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -500 / (float) 1000, (float) -500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 250 / (float) 1000, (float) 750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -250 / (float) 1000, (float) 750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 250 / (float) 1000, (float) -750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -250 / (float) 1000, (float) -750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 750 / (float) 1000, (float) 250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -750 / (float) 1000, (float) 250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 750 / (float) 1000, (float) -250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -750 / (float) 1000, (float) -250 / (float) 1000, true));
            }
            else
            {
                if (Math.random()*1000 > 500)
                    getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) (fireX1-100) / (float) 1000, (float) (fireY1+100) / (float) 1000, true));
                else
                    getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) (fireX1+100) / (float) 1000, (float) (fireY1-100) / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX2 / (float) 1000, (float) fireY2 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX3 / (float) 1000, (float) fireY3 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) fireX4 / (float) 1000, (float) fireY4 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 500 / (float) 1000, (float) 500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -500 / (float) 1000, (float) 500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 500 / (float) 1000, (float) -500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -500 / (float) 1000, (float) -500 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 250 / (float) 1000, (float) 750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -250 / (float) 1000, (float) 750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 250 / (float) 1000, (float) -750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -250 / (float) 1000, (float) -750 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 750 / (float) 1000, (float) 250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -750 / (float) 1000, (float) 250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) 750 / (float) 1000, (float) -250 / (float) 1000, true));
                getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float) -300, (float) (getX() + texture.getWidth() / 2 - 10), (float) (getY() + texture.getHeight() / 2 - 10), "Muskrat Missile", (float) -750 / (float) 1000, (float) -250 / (float) 1000, true));
            }
            rateCounter += 2*fireRate;
        }
        if (rateCounter != 0) {
            --rateCounter;
        }

        if (rateCounter < 0) {
            rateCounter = 0;
        }
    }

    /**
     * phase 4 projectile firing logic
     * @param delta
     */
    private void firePhase4(float delta)
    {
        prevPhase = phase;
        phase = 4;
        if (prevPhase != phase)
        {
            fireX1 = 0;
            fireY1 = 1000;
            XScale1 = 100;
            YScale1 = 100;
            fireX2 = 0;
            fireY2 = -1000;
            XScale2 = -100;
            YScale2 = -100;
            fireX3 = 1000;
            fireY3 = 0;
            XScale3 = -100;
            YScale3 = -100;
            fireX4 = -1000;
            fireY4 = 0;
            XScale4 = 100;
            YScale4 = 100;
        }

        if (rateCounter == 0) {
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX1/(float)1000, (float)fireY1/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX2/(float)1000, (float)fireY2/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX3/(float)1000, (float)fireY3/(float)1000, true));
            getStage().addActor(new LinearProjectile(new Texture("images/L3_Projectile.png"), (float)-300, (float)(getX() + texture.getWidth()/2 - 10), (float)(getY() + texture.getHeight()/2 - 10), "Muskrat Missile", (float)fireX4/(float)1000, (float)fireY4/(float)1000, true));
            fireX1 = (int) (Math.random()*1000);
            fireY1 = 1000 - fireX1;
            fireX2 = -1 * ((int) (Math.random()*1000));
            fireY2 = 1000 + fireX2;
            fireX3 = -1 * ((int) (Math.random()*1000));
            fireY3 = -1000 - fireX3;
            fireX4 = ((int) (Math.random()*1000));
            fireY4 = -1000 + fireX4;
            rateCounter += fireRate;
        }
        if (rateCounter != 0) {
            --rateCounter;
        }

        if (rateCounter < 0) {
            rateCounter = 0;
        }
    }

    /**
     * Set a new hitbox in case movement occurred
     */
    private void updateBounds() {
        float x = getX() + (texture.getWidth() / 2) - bounds.getWidth() / 2;
        float y = getY() + (texture.getHeight() / 2) - bounds.getHeight() / 2;

        bounds.setPosition(x, y);
    }

    /**
     * check for collisions for other objects, and handle the collisions appropriately
     */
    private void checkCollision() {
        for (Actor actor : getStage().getActors()) {
            if (Projectile.class.isInstance(actor)) {
                if (bounds.overlaps(((Projectile)actor).getBounds())) {
                    // Collided with projectile, check name to see if friendly or enemy
                    if (actor.getName().equals("Muskrat Missile"))
                        continue;
                    else if (actor.getName().equals("Player"))
                    {
                        Gdx.app.log("Collided with", actor.getName());
                        health -= ((Projectile) actor).getDamage();
                        ((Projectile) actor).destroy();
                        System.out.println("Health: " + health);
                        // TODO: Damage Sound?
                    }
                }
            }

            if (Player.class.isInstance(actor)) {
                if (bounds.overlaps(((Player)actor).getBounds())) {
                    Gdx.app.log("Collided with Player", actor.getName());
                    // TODO: Take/Deal Damage on collision with Player?
                }
            }
        }

        // Check side checks
        checkRight = (getX() + 30 < 520);
        checkLeft = (getX() - 20 > 20);
        checkBottom = (getY() - 20 > 20);
        checkTop = (getY() + 30 < getStage().getViewport().getScreenHeight() - 20);
    }

    /**
     * getter for the bounds of this object
     * @return
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
