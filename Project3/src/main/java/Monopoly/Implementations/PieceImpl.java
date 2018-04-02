package Monopoly.Implementations;

import Monopoly.Interfaces.ILocation;
import Monopoly.Interfaces.IPiece;

public class PieceImpl implements IPiece {

    private ILocation location;

    public PieceImpl(ILocation location){
        this.location = location;
    }


    public void Move(ILocation location) {
        this.location = location;
    }

    public ILocation GetLocation() {
        return this.location;
    }
}
