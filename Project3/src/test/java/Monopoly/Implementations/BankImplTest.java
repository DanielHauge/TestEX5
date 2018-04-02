package Monopoly.Implementations;

import Monopoly.Interfaces.IBank;
import Monopoly.Interfaces.IPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankImplTest {


    Map<IPlayer, Integer> standings;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        standings = new HashMap<IPlayer, Integer>();
        standings.put(p1, 100);
        standings.put(p2, 200);
        standings.put(p3, 300);
        standings.put(p4, 400);
    }

    @Mock
    IPlayer p1, p2, p3, p4;


    @Test
    void loan() {

        IBank bank = new BankImpl(standings);

        bank.Loan(p1, 150);
        bank.Loan(p2, 50);
        bank.Loan(p3, 300);
        bank.Loan(p4, 5);

        int s1 = ((BankImpl) bank).standings.get(p1);
        assertEquals(s1, -50);
        int s2 = ((BankImpl) bank).standings.get(p2);
        assertEquals(s2, 150);
        int s3 = ((BankImpl) bank).standings.get(p3);
        assertEquals(s3, 0);
        int s4 = ((BankImpl) bank).standings.get(p4);
        assertEquals(s4, 395);



    }

    @Test
    void pay() {

        IBank bank = new BankImpl(standings);

        bank.Pay(p1, 150);
        bank.Pay(p2, 50);
        bank.Pay(p3, 300);
        bank.Pay(p4, 5);

        int s1 = ((BankImpl) bank).standings.get(p1);
        assertEquals(s1, 250);
        int s2 = ((BankImpl) bank).standings.get(p2);
        assertEquals(s2, 250);
        int s3 = ((BankImpl) bank).standings.get(p3);
        assertEquals(s3, 600);
        int s4 = ((BankImpl) bank).standings.get(p4);
        assertEquals(s4, 405);
    }
}