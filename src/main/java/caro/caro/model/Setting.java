package caro.caro.model;

import java.util.HashMap;
import java.util.Map;

public interface Setting {
    //mapping score
    Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("00000", 0);
        put("00001", 2);
        put("00010", 2);
        put("00011", 5);
        put("00100", 2);
        put("00101", 4);
        put("00110", 5);
        put("00111", 400);
        put("01000", 2);
        put("01001", 4);
        put("01010", 4);
        put("01011", 800);
        put("01100", 5);
        put("01101", 800);
        put("01110", 1000);
        put("01111", 10000);
        put("10000", 2);
        put("10001", 4);
        put("10010", 4);
        put("10011", 20);
        put("10100", 4);
        put("10101", 20);
        put("10110", 800);
        put("10111", 10000);
        put("11000", 5);
        put("11001", 20);
        put("11010", 800);
        put("11011", 10000);
        put("11100", 400);
        put("11101", 10000);
        put("11110", 10000);
        put("11111", 100000);
    }};
    int localBeam = 15;
    int maxDepth = 4;
    int atkMultiplier = 1;
    int defMultiplier = 2;
}
