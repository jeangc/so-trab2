package br.ufsc;

class Forest {
    private Pot[] pots;

    Forest(Pot[] pots) {
        this.pots = pots;
    }

    public Pot[] getPots() {
        return pots;
    }

    Pot getFirstPot() {
        return pots[0];
    }
}
