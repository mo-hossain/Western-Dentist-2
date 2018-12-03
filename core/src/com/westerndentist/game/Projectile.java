package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Projectile extends Actor{

    private Texture texture;
    protected float speed = 0;
    private float damage = 10;
    private Rectangle bounds = new Rectangle();

    Projectile(Texture texture, float initialSpeed, float x, float y, String tag) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
        setName(tag);
        bounds.set(x + texture.getWidth() / 8, y + texture.getWidth() / 8, texture.getWidth() / 4, texture.getHeight() / 4);
    }

    Projectile(Texture texture, float initialSpeed, float x, float y, String tag, float damage) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
        setName(tag);
        this.damage = damage;
        bounds.set(x + texture.getWidth() / 8, y + texture.getWidth() / 8, texture.getWidth() / 4, texture.getHeight() / 4);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getName().equals("Player") && damage > 10) {
            setColor((255 - damage)/255, (255 - damage)/255, 1, 0.8f);
        }
        else if (damage == 10 && getName().equals("Player")) {
            setColor(1, 1, 1, 0.8f);
        }
        batch.setColor(getColor());
        batch.draw(texture, getX(), getY(), texture.getWidth() / 2, texture.getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!(this instanceof PositionProjectile || this instanceof NonVerticalProjectile || this instanceof LinearProjectile)) {
            moveBy(0, (speed * delta));
        }
        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            addAction(Actions.removeActor());
        }
        if (getX() > 800 || getX() < -200) {
            addAction(Actions.removeActor());
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    public float getDamage() {
        return damage;
    }

    public void destroy() {
        remove();
    }

    protected void updateBounds() {
        bounds.setPosition(getX() + texture.getWidth() / 8, getY()+ texture.getWidth() / 8);
    }

    public Rectangle getBounds() {
        return bounds;
    }


}
