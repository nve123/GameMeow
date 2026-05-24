package ru.education.shop;

import static ru.education.service.ShopService.goldPlus;
import static ru.education.service.ShopService.orePlus;
import static ru.education.service.ShopService.woodPlus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Item implements Disposable {
    private Rectangle hitBox;
    private Texture texture;
    private float x;
    private float y;
    private Price price;
    private ItemType itemType;

    public Item(ItemType itemType) {
        switch (itemType) {
            case TOWER -> {
                texture = new Texture(Gdx.files.internal("towerShop50x50.png"));
                price = new Price(10, 20, 15);
            }
            case UPDATE_DMG -> {
                texture = new Texture(Gdx.files.internal("test50x50.png"));
                price = new Price(20, 40, 30);
            }
            case UPDATE_SPEED -> {
                texture = new Texture(Gdx.files.internal("speed_up_50x50.png"));
                price = new Price(20, 40, 30);
            }
            case UPDATE_DMGPLSPLS -> {
                texture = new Texture(Gdx.files.internal("dmg++.png"));
                price = new Price(20 + goldPlus, 40 + orePlus, 30 + woodPlus);
            }
        }

        this.itemType = itemType;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setSlot(float x, float y) {
        this.hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.x = x;
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Price getPrice() {
        return price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
