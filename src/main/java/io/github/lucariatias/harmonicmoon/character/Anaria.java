package io.github.lucariatias.harmonicmoon.character;

import io.github.lucariatias.harmonicmoon.HarmonicMoon;
import io.github.lucariatias.harmonicmoon.job.Job;
import io.github.lucariatias.harmonicmoon.sprite.SpriteSheet;

public class Anaria extends Character {

    public Anaria(HarmonicMoon harmonicMoon) {
        super(harmonicMoon, "Anaria", 128, Gender.FEMALE, Job.WARRIOR, new SpriteSheet("/characters/anaria.png", 32, 16), new SpriteSheet("/characters/anaria-fight.png", 64, 64));
    }

}
