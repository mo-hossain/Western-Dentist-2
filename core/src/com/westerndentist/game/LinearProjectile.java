package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LinearProjectile extends Projectile {

    private float xFactor, yFactor, firstX, firstY;
    private float angle;
    private float modFactor = 2;
    private boolean curve, dir;

    private Vector2 origin;

    private boolean risingX = true, risingY = true;

    /**
     * Constructor -- only supported
     * @param texture
     * @param initialSpeed
     * @param x
     * @param y
     * @param tag
     * @param xFactor
     * @param yFactor
     * @param curve
     */
    LinearProjectile(Texture texture, float initialSpeed, float x, float y, String tag, float xFactor, float yFactor, boolean curve) {
        super(texture, initialSpeed, x, y, tag);

        this.xFactor = xFactor;
        this.yFactor = yFactor;

        firstX = xFactor;
        firstY = yFactor;


        if (curve) {
            origin = new Vector2(x, y);
        }

        this.curve = curve;
    }

    /**
     * perform necessary actions for this frame
     * @param delta
     */
    @Override
    public void act(float delta) {
        //super.act(delta);

        moveBy(xFactor * speed * delta, yFactor * speed * delta);

        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            remove();
        }
    }

    /**
     * return the angle of the projectile's motion based on x and y components
     * @param x
     * @param y
     * @return
     */
    private float getAngle(float x, float y) {
        if (x == 1 && y == 0) {
            return 0;
        }
        else if (x == 0 && y == 1) {
            return 90;
        }
        else if (x == -1 && y == 0) {
            return 180;
        }
        else if (x == 0 && y == -1) {
            return 270;
        }
        else if (x == 1 && y == 1) {
            return 45;
        }
        else if (x == -1 && y == 1) {
            return 135;
        }
        else if (x == -1 && y == -1) {
            return 225;
        }
        else if (x == 1 && y == -1) {
            return 315;
        }
        else {
            return 0;
        }
    }
}
