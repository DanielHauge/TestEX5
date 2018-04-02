package Monopoly.Interfaces;

public interface IBoard {


    public ILocation GetLocation(int i);

    public ILocation GetNewLocation(ILocation loc, int i);


}
