package Monopoly.Implementations;

import Monopoly.Interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class GameHandlerImpl implements IGameHandler {


    static List<IPlayer> pl;
    static IBoard board;
    static IDie[] dice;


    public static void main(String[] args){


        board = new BoardImpl();
        pl = ConstructPlayers();
        dice = new IDie[]{new DieImpl(), new DieImpl()};

        for (int i = 0; i < 10 ; i++) {

            for (IPlayer p : pl) {
                System.out.print("TURN START: #"+i+" - ");
                p.TakeTurn(dice);

                /// If Player has enough money, buy location.
                /// If Player lands on location that is owned by someone -> Pay Player, if not enough money Loan money from bank
                /// If Player passes the line -> Get money
            }
        }
    }

    public static List<IPlayer> ConstructPlayers(){
        ArrayList<IPlayer> players = new ArrayList<IPlayer>();
        for (int i = 0; i < 5 ; i++) {
            players.add(new PlayerImpl("Player"+i, i,new PieceImpl(board.GetLocation(0))));
        }
        List<IPlayer> results = players;
        return results;
    }

}
