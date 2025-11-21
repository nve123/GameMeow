package ru.education.service;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.education.shop.Item;
import ru.education.shop.ItemType;
import ru.education.shop.Shop;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
import ru.education.tower.TowerState;

public class ShopService {
    private final Array<SlotTower> slotTowerArray;
    private final Array<DefensiveTower> defensiveTowerArray;
    private final Shop shop;

    public ShopService(Array<SlotTower> slotTowerArray, Array<DefensiveTower> defensiveTowerArray, Shop shop) {
        this.slotTowerArray = slotTowerArray;
        this.defensiveTowerArray = defensiveTowerArray;
        this.shop = shop;
    }

    public void shopItemClickProcessing(Vector3 touchPoint) {
        for (Item item : shop.getItems()) {
            if (item.getHitBox().contains(touchPoint.x, touchPoint.y)) {
                if (item.getItemType() == ItemType.TOWER) {
                    activateSlots();
                } else if (item.getItemType() == ItemType.UPDATE_DMG) {
                    activateCanUpdate(ItemType.UPDATE_DMG);
                } else if (item.getItemType() == ItemType.UPDATE_SPEED) {
                    activateCanUpdate(ItemType.UPDATE_SPEED);
                }
                shop.setCurChoice(item);
                shop.setActive(true);
            }
        }
    }

    private void activateCanUpdate(ItemType itemType) {
        TowerState canUpgradeTowerState;
        if (itemType == ItemType.UPDATE_DMG) {
            canUpgradeTowerState = TowerState.SPEED_UP;
        } else {
            canUpgradeTowerState = TowerState.DMG_UP;
        }
        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            if (defensiveTower.getCurState() == TowerState.DEFAULT || defensiveTower.getCurState() == canUpgradeTowerState) {
                defensiveTower.setCurState(TowerState.CLICKED);
            }
        }
    }

    private void activateSlots() {
        for (SlotTower slotTower : slotTowerArray) {
            if (slotTower.isFree()) slotTower.setVisible(true);
        }
    }
}
