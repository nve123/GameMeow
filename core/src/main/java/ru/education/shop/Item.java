package ru.education.shop;

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
                texture = new Texture(Gdx.files.internal("UI/towerShop50x50.png"));
                price = new Price(10, 20, 15);
            }
            case UPDATE_DMG -> {
                texture = new Texture(Gdx.files.internal("UI/dmg_up.png"));
                price = new Price(20, 40, 30);
            }
            case UPDATE_SPEED -> {
                texture = new Texture(Gdx.files.internal("UI/speed_up_50x50.png"));
                price = new Price(20, 40, 30);
            }
            case UPDATE_DMGPLSPLS -> {
                texture = new Texture(Gdx.files.internal("UI/dmg++.png"));
                price = new Price(20, 40, 30 );
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

    public void updateDmgPlusPrice(int extraGold, int extraOre, int extraWood) {
        if (this.itemType == ItemType.UPDATE_DMGPLSPLS) {
            this.price = new Price(
                this.price.getGold() + extraGold,
                this.price.getOre() + extraOre,
                this.price.getWood() + extraWood
            );
        }
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
