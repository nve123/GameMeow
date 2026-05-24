package ru.education.util;

import static ru.education.service.ShopService.dmgPlus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumMap;

import ru.education.tower.DefensiveTower;
import ru.education.tower.TowerState;
import ru.education.tower.TowerStateAttribute;

public class TowerUtil {
    private static int dmg = 5;
    public TowerUtil(){

    }
    public static DefensiveTower getDefensiveTower(float x, float y) {
        updateDmg();
        TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
            new Texture("tower (3).png"),
            100,
            125,
            2,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
            new Texture("shine.png"),
            100,
            125,
            2,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
            new Texture("dmg.png"),
            100,
            125,
            4,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
            new Texture("speed.png"),
            100,
            125,
            2,
            1,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
            new Texture("speed_dmg.png"),
            100,
            125,
            4,
            1,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeDmgPlsPls = new TowerStateAttribute(
            new Texture("speed_dmg++.png"),
            100,
            125,
            dmg,
            1,
            new Rectangle(x, y, 70, 125)
        );
        EnumMap<TowerState, TowerStateAttribute> stateAttributeEnumMap = new EnumMap<>(TowerState.class);
        stateAttributeEnumMap.put(TowerState.DEFAULT, towerStateAttributeDefault);
        stateAttributeEnumMap.put(TowerState.CLICKED, towerStateAttributeClicked);
        stateAttributeEnumMap.put(TowerState.DMG_UP, towerStateAttributeDmgUp);
        stateAttributeEnumMap.put(TowerState.DMG_SPEED_UP, towerStateAttributeSpdDmg);
        stateAttributeEnumMap.put(TowerState.SPEED_UP, towerStateAttributeSpeedUp);
        stateAttributeEnumMap.put(TowerState.DMGPLSPLS, towerStateAttributeDmgPlsPls);
        return new DefensiveTower(x, y, stateAttributeEnumMap,200, 300);
    }
    public static void updateDmg(){
        dmg += dmgPlus;
    }
}
