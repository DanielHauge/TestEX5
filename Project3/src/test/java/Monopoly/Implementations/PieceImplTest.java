package Monopoly.Implementations;

import Monopoly.Interfaces.ILocation;
import Monopoly.Interfaces.IPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PieceImplTest {


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock ILocation location;

    @Mock ILocation newLocation;

    @Test
    void move() {
        IPiece piece = new PieceImpl(location);
        piece.Move(newLocation);
        assertEquals(piece.GetLocation(), newLocation);
    }

    @Test
    void getLocation() {
        IPiece piece = new PieceImpl(location);
        assertEquals(piece.GetLocation(), location);
    }
}