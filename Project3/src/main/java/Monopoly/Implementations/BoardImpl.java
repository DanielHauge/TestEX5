package Monopoly.Implementations;

import Monopoly.Interfaces.IBoard;
import Monopoly.Interfaces.ILocation;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements IBoard {

    private List<ILocation> locationList;

    public BoardImpl(){
        this.locationList = ConstructBoardLocations();
    }

    public BoardImpl(List<ILocation> locationList){
        this.locationList = locationList;
    }

    private BoardImpl GetMe(){
        return this;
    }


    public ILocation GetLocation(int i) {
        return locationList.get(i);
    }

    public ILocation GetNewLocation(ILocation loc, int i) {
        boolean passed = false;
        ILocation next = null;
        if (locationList.indexOf(loc)+i<locationList.size()){
            next = this.GetLocation(locationList.indexOf(loc)+i);
        } else{
            passed = true;
            next = this.GetLocation(locationList.indexOf(loc)+i-50);
        }
        System.out.print("Location: #"+locationList.indexOf(next)+" - ");
        if (passed){System.out.print("Passed the line - ");}
        return next;
    }

    private List<ILocation> ConstructBoardLocations(){
        ArrayList<ILocation> loclist = new ArrayList<ILocation>();
        for (int i = 0; i < 50; i++) {
            loclist.add(new LocationImpl(this.GetMe(), i));
        }
        List<ILocation> results = loclist;
        return results;
    }
}
