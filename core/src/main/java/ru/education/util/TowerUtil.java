package ru.education.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.EnumMap;

import ru.education.tower.DefensiveTower;
import ru.education.tower.TowerState;
import ru.education.tower.TowerStateAttribute;

public class TowerUtil {
    public static DefensiveTower getDefensiveTower(float x, float y){
        TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
            new Texture("tower.png"),
            70,
            125,
            2,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
            new Texture("towershine.png"),
            70,
            125,
            2,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
            new Texture("towerdmgup.png"),
            70,
            125,
            4,
            5,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
            new Texture("towerspeedup.png"),
            70,
            125,
            2,
            1,
            new Rectangle(x, y, 70, 125)
        );
        TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
            new Texture("tower_speed_ang_gmd_up.png"),
            70,
            125,
            4,
            1,
            new Rectangle(x, y, 70, 125)
        );
        EnumMap<TowerState, TowerStateAttribute> stateAttributeEnumMap = new EnumMap<>(TowerState.class);
        stateAttributeEnumMap.put(TowerState.DEFAULT, towerStateAttributeDefault);
        stateAttributeEnumMap.put(TowerState.CLICKED, towerStateAttributeClicked);
        stateAttributeEnumMap.put(TowerState.DMG_UP, towerStateAttributeDmgUp);
        stateAttributeEnumMap.put(TowerState.DMG_SPEED_UP, towerStateAttributeSpdDmg);
        stateAttributeEnumMap.put(TowerState.SPEED_UP, towerStateAttributeSpeedUp);
        return new DefensiveTower(x, y, stateAttributeEnumMap);
    }
}
