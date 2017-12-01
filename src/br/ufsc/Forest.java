package br.ufsc;

class Forest {
    private Pot[] pots;

    Forest(Pot[] pots) {
        this.pots = pots;
    }

    Pot[] getPots() {
        return pots;
    }

    Pot getFirstPot() {
        return pots[0];
    }
}
