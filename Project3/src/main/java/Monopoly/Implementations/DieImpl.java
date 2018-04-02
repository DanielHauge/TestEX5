package Monopoly.Implementations;

import Monopoly.Interfaces.IDie;

public class DieImpl implements IDie {
    public int Roll() {
        return (int)(Math.random() * 6) +1;
    }
}
