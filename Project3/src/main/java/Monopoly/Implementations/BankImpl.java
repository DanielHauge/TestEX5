package Monopoly.Implementations;

import Monopoly.Interfaces.IBank;
import Monopoly.Interfaces.IPlayer;

import java.util.Map;

public class BankImpl implements IBank {

    Map<IPlayer, Integer> standings;


    public BankImpl(Map<IPlayer, Integer> standings){
        this.standings = standings;
    }


    public void Loan(IPlayer player, int Amount) {

        int currently = standings.get(player);
        standings.put(player, currently-Amount);

    }

    public void Pay(IPlayer player, int Amount) {

        int currently = standings.get(player);
        standings.put(player, currently+Amount);

    }
}
