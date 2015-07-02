package com.seventh_root.harmonicmoon.fight;

import com.seventh_root.harmonicmoon.enemy.Enemy;
import com.seventh_root.harmonicmoon.fight.party.CharacterFightParty;
import com.seventh_root.harmonicmoon.HarmonicMoon;
import com.seventh_root.harmonicmoon.character.Character;
import com.seventh_root.harmonicmoon.fight.party.EnemyFightParty;
import com.seventh_root.harmonicmoon.skill.Skill;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FightOptionBox {

    private HarmonicMoon harmonicMoon;
    private BufferedImage image;
    private FightOption[] options;

    private boolean mousePressed;

    private Character.Fight character;

    public FightOptionBox(HarmonicMoon harmonicMoon, FightPanel fightPanel) {
        this.harmonicMoon = harmonicMoon;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/message.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        fightPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                mousePressed = false;
                HarmonicMoon harmonicMoon = FightOptionBox.this.harmonicMoon;
                int x = 16, y = 496 - image.getHeight();
                for (FightOption option : options) {
                    Rectangle optionBounds = new Rectangle(x, y, image.getWidth() / 2 - 32, 16);
                    Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                    mousePoint.translate(- (int) harmonicMoon.getLocationOnScreen().getX(), - (int) harmonicMoon.getLocationOnScreen().getY());
                    if (optionBounds.contains(mousePoint)) {
                        option.doSelect();
                        return;
                    }
                    y += 16;
                    if ((y - (496 - image.getHeight())) / 16 == 6) {
                        y = 496 - image.getHeight();
                        x += image.getWidth() / 2;
                    }
                }
            }
        });
    }

    public void setOptions(FightOption... options) {
        this.options = options;
    }

    public FightOption[] getOptions() {
        return options;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(image, 0, 480 - image.getHeight(), null);
        if (options != null) {
            int x = 16, y = 496 - image.getHeight();
            for (FightOption option : options) {
                Rectangle optionBounds = new Rectangle(x, y, image.getWidth() / 2 - 32, 16);
                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                mousePoint.translate(- (int) harmonicMoon.getLocationOnScreen().getX(), - (int) harmonicMoon.getLocationOnScreen().getY());
                if (optionBounds.contains(mousePoint)) {
                    graphics.setColor(mousePressed ? Color.DARK_GRAY : Color.LIGHT_GRAY);
                    for (int i = -2; i <= 2; i++) {
                        graphics.drawRect((int) optionBounds.getX() - i, (int) optionBounds.getY() - i, (int) optionBounds.getWidth() + (2 * i), (int) optionBounds.getHeight() + (2 * i));
                    }
                }
                graphics.setColor(Color.WHITE);
                graphics.drawRect((int) optionBounds.getX(), (int) optionBounds.getY(), (int) optionBounds.getWidth(), (int) optionBounds.getHeight());
                graphics.setFont(harmonicMoon.getMessageFont());
                graphics.drawString(option.getName(), x + 16, y + 12);
                y += 16;
                if ((y - (496 - image.getHeight())) / 16 == 6) {
                    y = 496 - image.getHeight();
                    x += image.getWidth() / 2;
                }
            }
        }
    }

    public Character.Fight getCharacter() {
        return character;
    }

    public void setCharacter(Character.Fight character) {
        this.character = character;
    }

    public void resetOptions() {
        final Fight fight = harmonicMoon.getFightPanel().getFight();
        this.options = new FightOption[] {
                new FightOption("Attack", new Runnable() {
                    @Override
                    public void run() {
                        EnemyFightParty enemyParty = FightOptionBox.this.harmonicMoon.getFightPanel().getFight().getEnemyParty();
                        options = new FightOption[enemyParty.getMembers().size()];
                        int i = 0;
                        for (final Enemy enemy : enemyParty.getMembers()) {
                            options[i] = new FightOption(enemy.getName(), new Runnable() {
                                @Override
                                public void run() {
                                    fight.addTurnAction(character.attack(enemy));
                                    showNext(fight);
                                }
                            });
                            i++;
                        }
                    }
                }),
                new FightOption("Defend", new Runnable() {
                    @Override
                    public void run() {
                        fight.addTurnAction(character.defend());
                        showNext(fight);
                    }
                }),
                new FightOption("Use skill", new Runnable() {
                    @Override
                    public void run() {
                        options = new FightOption[character.getSkills().size()];
                        for (int i = 0; i < character.getSkills().size(); i++) {
                            final Skill skill = character.getSkills().get(i);
                            options[i] = new FightOption(skill.getName(), new Runnable() {
                                @Override
                                public void run() {
                                    EnemyFightParty enemyParty = FightOptionBox.this.harmonicMoon.getFightPanel().getFight().getEnemyParty();
                                    options = new FightOption[enemyParty.getMembers().size()];
                                    int i = 0;
                                    for (final Enemy enemy : enemyParty.getMembers()) {
                                        options[i] = new FightOption(enemy.getName(), new Runnable() {
                                            @Override
                                            public void run() {
                                                fight.addTurnAction(character.useSkill(fight, skill, enemy, null));
                                                showNext(fight);
                                            }
                                        });
                                        i++;
                                    }
                                }
                            });
                        }
                    }
                }),
                new FightOption("Use item", new Runnable() {
                    @Override
                    public void run() {
                        showNext(fight);
                    }
                }),
                new FightOption("Run", new Runnable() {
                    @Override
                    public void run() {
                        HarmonicMoon harmonicMoon = FightOptionBox.this.harmonicMoon;
                        harmonicMoon.getFightPanel().endFight();
                        harmonicMoon.getFightPanel().setActive(false);
                        harmonicMoon.setPanel("map_" + harmonicMoon.getWorldPanel().getWorld().getName());
                        harmonicMoon.getWorldPanel().setActive(true);
                    }
                })
        };
    }

    public void clearOptions() {
        this.options = new FightOption[0];
    }

    private void showNext(Fight fight) {
        CharacterFightParty party = fight.getCharacterParty();
        int memberIndex = party.getMembers().indexOf(character);
        if (memberIndex + 1 < party.getMembers().size()) {
            setCharacter(party.getMembers().get(memberIndex + 1));
            resetOptions();
        } else {
            fight.doTurn();
            clearOptions();
        }
    }

}