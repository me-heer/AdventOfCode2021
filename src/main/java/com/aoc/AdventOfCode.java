package com.aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class AdventOfCode {
    Scanner input;

    AdventOfCode() {
        input = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    }

    abstract void solve();
}
