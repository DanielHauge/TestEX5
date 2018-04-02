package Monopoly.Implementations;

import Monopoly.Interfaces.IBoard;
import Monopoly.Interfaces.ILocation;

public class LocationImpl implements ILocation {

    private String owner;
    private IBoard board;
    private int price;

    public LocationImpl(IBoard board, int price){
        this.board = board;
        this.price = price;
    }

    public boolean isOwned() {
        return owner!=null;
    }

    public IBoard GetBoard() {
        return board;
    }

    public int GetPrice() { return this.price; }

    public void SetOwner(String name) {
        if (!this.isOwned()){
            owner = name;
        }
    }
}
