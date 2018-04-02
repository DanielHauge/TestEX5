package Monopoly.Implementations;

import Monopoly.Interfaces.IDie;
import Monopoly.Interfaces.ILocation;
import Monopoly.Interfaces.IPiece;
import Monopoly.Interfaces.IPlayer;

public class PlayerImpl implements IPlayer {

    private String name;
    private int balance;
    private IPiece piece;

    public PlayerImpl(String name, int balance, IPiece piece){
        this.name = name;
        this.balance = balance;
        this.piece = piece;
    }

    public void TakeTurn(IDie[] dice) {

        System.out.print("Player: "+name+" - ");
        int number = 0;

        for (IDie die : dice) {
            number+= die.Roll();
        }

        System.out.print("Rolled: "+number+" - ");

        ILocation current = piece.GetLocation();

        ILocation newLocation = current.GetBoard().GetNewLocation(current, number);


        piece.Move(newLocation);

        System.out.print(" ENDTURN\n");

    }

    public void BuyLocation() {

        ILocation loc = piece.GetLocation();
        if (balance>=loc.GetPrice()){
            loc.SetOwner(name);
        }



    }
}
