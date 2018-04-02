package Monopoly.Implementations;

import Monopoly.Interfaces.IBoard;
import Monopoly.Interfaces.ILocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardImplTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Mock
    ILocation loc1, loc2, loc3, loc4, loc5, loc6, loc7;


    @Test
    void getLocation() {
        List<ILocation> list = Arrays.asList(loc1, loc2, loc3, loc4, loc5, loc6, loc7);

        IBoard board = new BoardImpl(list);
        assertEquals(board.GetLocation(3), loc4);


    }

    @Test
    void getNewLocation() {
        List<ILocation> list = Arrays.asList(loc1, loc2, loc3, loc4, loc5, loc6, loc7);

        IBoard board = new BoardImpl(list);
        assertEquals(board.GetNewLocation(loc3, 2), loc5);
    }
}