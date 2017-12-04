package br.ufsc;

import java.util.ArrayList;

class Forest {
    private Pot[] pots;

    Forest() {
        ArrayList<Pot> ps = new ArrayList<>();

        for (int i=0; i<=20; i++) {
            ps.add(new Pot("Pote " + i));
        }

        pots = new Pot[ps.size()];
        pots = ps.toArray(pots);
        pots[0].addRelatedPots(new Pot[] { pots[1], pots[14] });
        pots[1].addRelatedPots(new Pot[] { pots[2], pots[3], pots[4], pots[0] });
        pots[2].addRelatedPots(new Pot[] { pots[1], pots[8], pots[14] });
        pots[3].addRelatedPots(new Pot[] { pots[8], pots[9], pots[1] });
        pots[4].addRelatedPots(new Pot[] { pots[1], pots[5] });
        pots[5].addRelatedPots(new Pot[] { pots[6], pots[4], pots[7] });
        pots[6].addRelatedPots(new Pot[] { pots[5] });
        pots[7].addRelatedPots(new Pot[] { pots[5] });
        pots[8].addRelatedPots(new Pot[] { pots[2], pots[3], pots[14], pots[17] });
        pots[9].addRelatedPots(new Pot[] { pots[3], pots[11] });
        pots[10].addRelatedPots(new Pot[] { pots[11], pots[13], pots[16] });
        pots[11].addRelatedPots(new Pot[] { pots[10], pots[9], pots[12] });
        pots[12].addRelatedPots(new Pot[] { pots[11] });
        pots[13].addRelatedPots(new Pot[] { pots[10], pots[15] });
        pots[14].addRelatedPots(new Pot[] { pots[0], pots[8] });
        pots[15].addRelatedPots(new Pot[] { pots[13], pots[16], pots[19], pots[17] });
        pots[16].addRelatedPots(new Pot[] { pots[15], pots[10] });
        pots[17].addRelatedPots(new Pot[] { pots[8], pots[18], pots[15] });
        pots[18].addRelatedPots(new Pot[] { pots[17], pots[19] });
        pots[19].addRelatedPots(new Pot[] { pots[15], pots[18] });
    }

    Pot[] getPots() {
        return pots;
    }

    Pot getFirstPot() {
        return pots[0];
    }
}
