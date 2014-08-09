package io.github.lucariatias.harmonicmoon.inventory.item.consumable;

import io.github.lucariatias.harmonicmoon.fight.Combatant;

public class MegaEther extends Consumable {

    public MegaEther() {
        super("Mega Ether", "A tonic that regenerates mana.\nReplenishes 1000 mana.");
    }

    @Override
    public void use(Combatant user, Combatant target) {
        target.setMana(target.getMana() + 1000);
    }

}