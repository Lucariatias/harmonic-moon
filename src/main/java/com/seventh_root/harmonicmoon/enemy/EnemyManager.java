package com.seventh_root.harmonicmoon.enemy;

import com.seventh_root.harmonicmoon.HarmonicMoon;
import com.seventh_root.harmonicmoon.sprite.Sprite;
import com.seventh_root.harmonicmoon.sprite.SpriteSheet;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnemyManager {

    private Map<Class<? extends Enemy>, SpriteSheet> enemySpriteSheets = new HashMap<>();
    private Map<Class<? extends Enemy>, Sprite> enemyWaitSprites = new HashMap<>();
    private Map<Class<? extends Enemy>, Sprite> enemyAttackSprites = new HashMap<>();
    private Map<Class<? extends Enemy>, Sprite> enemyDamagedSprites = new HashMap<>();
    private Map<Class<? extends Enemy>, Sprite> enemyInjuredSprites = new HashMap<>();

    public EnemyManager(HarmonicMoon harmonicMoon) {
        try {
            SpriteSheet slimeSpriteSheet = new SpriteSheet(harmonicMoon, ImageIO.read(getClass().getResourceAsStream("/enemies/slime.png")), 64, 64);
            enemySpriteSheets.put(Slime.class, slimeSpriteSheet);
            enemyWaitSprites.put(Slime.class, slimeSpriteSheet.getSprite(0, 0, 8, 10));
            enemyAttackSprites.put(Slime.class, slimeSpriteSheet.getSprite(0, 1, 8, 5));
            enemyDamagedSprites.put(Slime.class, slimeSpriteSheet.getSprite(0, 0, 8, 5));
            enemyInjuredSprites.put(Slime.class, slimeSpriteSheet.getSprite(0, 2, 8, 5));
            SpriteSheet soldierSpriteSheet = new SpriteSheet(harmonicMoon, ImageIO.read(getClass().getResourceAsStream("/enemies/soldier.png")), 64, 64);
            enemySpriteSheets.put(Soldier.class, soldierSpriteSheet);
            enemyWaitSprites.put(Soldier.class, soldierSpriteSheet.getSprite(0, 0, 8, 10));
            enemyAttackSprites.put(Soldier.class, soldierSpriteSheet.getSprite(0, 1, 8, 5));
            enemyDamagedSprites.put(Soldier.class, soldierSpriteSheet.getSprite(0, 0, 8, 5));
            enemyInjuredSprites.put(Soldier.class, soldierSpriteSheet.getSprite(0, 2, 8, 5));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public SpriteSheet getSpriteSheet(Class<? extends Enemy> enemy) {
        return enemySpriteSheets.get(enemy);
    }

    public Sprite getWaitSprite(Class<? extends Enemy> enemy) {
        return new Sprite(enemyWaitSprites.get(enemy));
    }

    public Sprite getAttackSprite(Class<? extends Enemy> enemy) {
        return new Sprite(enemyAttackSprites.get(enemy));
    }

    public Sprite getDamagedSprite(Class<? extends Enemy> enemy) {
        return new Sprite(enemyDamagedSprites.get(enemy));
    }

    public Sprite getInjuredSprite(Class<? extends Enemy> enemy) {
        return new Sprite(enemyInjuredSprites.get(enemy));
    }

}