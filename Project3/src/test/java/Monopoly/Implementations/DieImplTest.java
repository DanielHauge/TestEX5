package Monopoly.Implementations;

import Monopoly.Interfaces.IDie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class DieImplTest {

    @Test
    void roll() {

        IDie die = new DieImpl();
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++) {
            list.add(die.Roll());
        }

        for (Integer integer : list) {
            Assertions.assertTrue(0<integer&&integer<7);
        }


    }
}