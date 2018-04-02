package Monopoly.Implementations;

import Monopoly.Interfaces.IBoard;
import Monopoly.Interfaces.ILocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class LocationImplTest {


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    IBoard board;


    @Test
    void isOwned() {
        ILocation loc = new LocationImpl(board, 50);
        assertFalse(loc.isOwned());

        loc.SetOwner("Daniel");
        assertTrue(loc.isOwned());

    }

    @Test
    void getBoard() {
        ILocation loc = new LocationImpl(board, 50);
        assertEquals(loc.GetBoard(), board);
    }

    @Test
    void getPrice() {
        ILocation loc = new LocationImpl(board, 50);
        assertEquals(loc.GetPrice(), 50);
    }

}