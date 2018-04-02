package Monopoly.Implementations;

import Monopoly.Interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

class PlayerImplTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    IPiece piece;

    @Mock ILocation currentLoc;

    @Mock ILocation newLoc;

    @Mock IBoard board;

    @Mock
    IDie die1;

    @Mock
    IDie die2;

    @Test
    void takeTurn() {
        IDie[] dice = {die1, die2};

        given(piece.GetLocation()).willReturn(currentLoc);
        given(currentLoc.GetBoard()).willReturn(board);
        given(board.GetNewLocation(eq(currentLoc), anyInt())).willReturn(newLoc);
        given(die1.Roll()).willReturn(5);
        given(die2.Roll()).willReturn(5);

        IPlayer player = new PlayerImpl("Daniel", 100, piece);
        player.TakeTurn(dice);

        verify(die1).Roll();
        verify(die2).Roll();
        verify(piece).GetLocation();
        verify(currentLoc).GetBoard();
        verify(board).GetNewLocation(eq(currentLoc), anyInt());
        verify(piece).Move(newLoc);



    }

    @Test
    void buyLocation() {

        given(piece.GetLocation()).willReturn(currentLoc);
        given(currentLoc.GetPrice()).willReturn(80);

        IPlayer player = new PlayerImpl("Daniel", 100, piece);
        player.BuyLocation();

        verify(piece).GetLocation();
        verify(currentLoc).GetPrice();
        verify(currentLoc).SetOwner("Daniel");


    }
}