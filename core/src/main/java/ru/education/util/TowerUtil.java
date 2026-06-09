package ru.education.util;

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
    public static DefensiveTower getDefensiveTower(float x, float y, byte numLvl) {
        if (numLvl == 1 || numLvl == 3) {
            TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
                new Texture("towers_assets/plaints_default_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
                new Texture("towers_assets/plaints_shine_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
                new Texture("towers_assets/plaints_dmg_up_tower.png"),
                100,
                125,
                4,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
                new Texture("towers_assets/plaints_speed_up_tower.png"),
                100,
                125,
                2,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
                new Texture("towers_assets/plaints_speed_and_dmg_up.png"),
                100,
                125,
                4,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgPlsPls = new TowerStateAttribute(
                new Texture("towers_assets/plaints_dmg_up_inf_tower.png"),
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
            return new DefensiveTower(x, y, stateAttributeEnumMap, 200, 300);
        } else if (numLvl == 2 || numLvl == 4) {
            TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
                new Texture("towers_assets/desert_default_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
                new Texture("towers_assets/desert_shine_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
                new Texture("towers_assets/desert_dmg_up_tower.png"),
                100,
                125,
                4,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
                new Texture("towers_assets/desert_speed_up_tower.png"),
                100,
                125,
                2,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
                new Texture("towers_assets/desert_speed_and_dmg_up.png"),
                100,
                125,
                4,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgPlsPls = new TowerStateAttribute(
                new Texture("towers_assets/desert_dmg_up_inf_tower.png"),
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
            return new DefensiveTower(x, y, stateAttributeEnumMap, 200, 300);
        } /*else if (numLvl == 3) {
            TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
                new Texture("towers_assets/winter_default_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
                new Texture("towers_assets/winter_shine_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
                new Texture("towers_assets/winter_dmg_up_tower.png"),
                100,
                125,
                4,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
                new Texture("towers_assets/winter_speed_up_winter.png"),
                100,
                125,
                2,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
                new Texture("towers_assets/winter_speed_and_dmg_up.png"),
                100,
                125,
                4,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgPlsPls = new TowerStateAttribute(
                new Texture("towers_assets/winter_dmg_up_inf_tower.png"),
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
            return new DefensiveTower(x, y, stateAttributeEnumMap, 200, 300);
        }*/ else {
            TowerStateAttribute towerStateAttributeDefault = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_default_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeClicked = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_shine_tower.png"),
                100,
                125,
                2,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgUp = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_dmg_up_tower.png"),
                100,
                125,
                4,
                5,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpeedUp = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_speed_up_tower.png"),
                100,
                125,
                2,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeSpdDmg = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_speed_and_dmg_up.png"),
                100,
                125,
                4,
                1,
                new Rectangle(x, y, 70, 125)
            );
            TowerStateAttribute towerStateAttributeDmgPlsPls = new TowerStateAttribute(
                new Texture("towers_assets/mushroom_dmg_up_inf_tower.png"),
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
            return new DefensiveTower(x, y, stateAttributeEnumMap, 200, 300);
        }
    }
}
