package ru.education.service;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ru.education.shop.Item;
import ru.education.shop.ItemType;
import ru.education.shop.Shop;
import ru.education.tower.DefensiveTower;
import ru.education.tower.SlotTower;
import ru.education.tower.TowerState;
import ru.education.user.User;
import ru.education.util.TowerUtil;

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
                    toggleActivateSlots(true);
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

    private void toggleActivateSlots(boolean activate) {
        for (SlotTower slotTower : slotTowerArray) {
            if (slotTower.isFree()) slotTower.setVisible(activate);
        }
    }

    private void buyTower(SlotTower slotTower) {
        defensiveTowerArray.add(
            TowerUtil.getDefensiveTower(
                slotTower.getHitBox().x,
                slotTower.getHitBox().y
            )
        );
        slotTower.setFree(false);
        slotTower.setVisible(false);
        User.getInstance().buyItem(shop.getCurChoice());
    }

    public void shoppingProcess(Vector3 touchPoint) {
        if (shop.getCurChoice().getItemType() == ItemType.TOWER) {
            for (SlotTower slotTower : slotTowerArray) {
                if (
                    slotTower.getHitBox().contains(touchPoint.x, touchPoint.y)
                        && slotTower.isFree()
                        && User.getInstance().canBuy(shop.getCurChoice().getPrice())
                ) {
                    buyTower(slotTower);
                }
            }
            toggleActivateSlots(false);
        } else if (shop.getCurChoice().getItemType() == ItemType.UPDATE_DMG) {
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                if (
                    User.getInstance().canBuy(shop.getCurChoice().getPrice())
                        && defensiveTower.getHitbox().contains(touchPoint.x, touchPoint.y)
                ) {
                    buyUpdate(defensiveTower, ItemType.UPDATE_DMG);
                }
            }
            disableTower();
        } else if (shop.getCurChoice().getItemType() == ItemType.UPDATE_SPEED) {
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                if (
                    User.getInstance().canBuy(shop.getCurChoice().getPrice())
                        && defensiveTower.getHitbox().contains(touchPoint.x, touchPoint.y)
                ) {
                    buyUpdate(defensiveTower, ItemType.UPDATE_SPEED);
                }
            }
            disableTower();
        }
    }

    //TODO:найти участок где активируются таворы и унифицировать метод
    private void disableTower() {
        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            if (defensiveTower.getCurState() == TowerState.CLICKED) {
                defensiveTower.setCurState(defensiveTower.getPrevState());
            }
        }
    }

    private void buyUpdate(DefensiveTower defensiveTower, ItemType itemType) {
        if (itemType == ItemType.UPDATE_DMG) {
            if (defensiveTower.getPrevState() == TowerState.DEFAULT) {
                defensiveTower.setCurState(TowerState.DMG_UP);
                User.getInstance().buyItem(shop.getCurChoice());
            } else if (defensiveTower.getPrevState() == TowerState.SPEED_UP) {
                defensiveTower.setCurState(TowerState.DMG_SPEED_UP);
                User.getInstance().buyItem(shop.getCurChoice());
            }
        } else {
            if (defensiveTower.getPrevState() == TowerState.DEFAULT) {
                defensiveTower.setCurState(TowerState.SPEED_UP);
                User.getInstance().buyItem(shop.getCurChoice());
            } else if (defensiveTower.getPrevState() == TowerState.DMG_UP) {
                defensiveTower.setCurState(TowerState.DMG_SPEED_UP);
                User.getInstance().buyItem(shop.getCurChoice());
            }

        }
    }
}
