package ru.education.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import ru.education.fontBuilder.FontBuilder;

public class Shop implements Disposable {
    private final float x;
    private final float y;
    private float xNext;
    private final Array<Item> items;
    private boolean isActive;
    private Item curChoice;
    private final BitmapFont font;

    public Shop(float x, float y) {
        this.x = x;
        this.y = y;

        isActive = false;

        xNext = x;
        font = FontBuilder.generate(8, Color.WHITE, "fonts/Curtsweeper-Regular.otf", true);

        items = new Array<>();
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Array<Item> getItems() {
        return items;
    }

    public void addItem(ItemType itemType) {
        Item item = new Item(itemType);
        item.setSlot(xNext, y);
        xNext += item.getHitBox().width;
        items.add(item);
    }

    public void draw(SpriteBatch batch) {
        for (Item item : items) {
            batch.draw(item.getTexture(), item.getX(), item.getY());
            font.draw(
                batch,
                "g:" + item.getPrice().getGold() +
                    "\n\no:" + item.getPrice().getOre() +
                    "\n\nw:" + item.getPrice().getWood(),
                item.getX(),
                item.getY() + item.getHitBox().height + 50f
            );
        }
    }

    public void setCurChoice(Item item) {
        curChoice = item;
    }

    public Item getCurChoice() {
        return curChoice;
    }

    @Override
    public void dispose() {
        font.dispose();
        for (Item item : items) {
            item.dispose();
        }
    }
}
