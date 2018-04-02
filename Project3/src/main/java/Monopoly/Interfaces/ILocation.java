package Monopoly.Interfaces;

public interface ILocation {

    public boolean isOwned();

    public IBoard GetBoard();

    public int GetPrice();

    public void SetOwner(String name);

}
