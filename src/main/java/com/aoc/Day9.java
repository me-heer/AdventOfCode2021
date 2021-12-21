package com.aoc;

import java.util.ArrayList;
import java.util.Scanner;

public class Day9 extends AdventOfCode {

    @Override
    public void solve() {
        Scanner in = super.input;
        ArrayList<String> input = new ArrayList<>();
        while (in.hasNextLine()) {
            input.add(in.nextLine());
        }

        ArrayList<Integer> lowPoints = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            for(int j = 0; j < input.get(i).length(); j++) {
                int current = Integer.parseInt(String.valueOf(input.get(i).charAt(j)));
                int down, up, left, right;
                if(i == 0 && j == 0) {
                    right = Integer.parseInt(String.valueOf(input.get(i).charAt(j+1)));
                    down = Integer.parseInt(String.valueOf(input.get(i+1).charAt(j)));
                    if(current < right && current < down)
                        lowPoints.add(current);
                } else if(i == 0 && j == input.get(i).length() - 1) {
                    down = Integer.parseInt(String.valueOf(input.get(i+1).charAt(j)));
                    left = Integer.parseInt(String.valueOf(input.get(i).charAt(j-1)));
                    if(current < left && current < down)
                        lowPoints.add(current);
                } else if(i == input.size() - 1 && j == 0) {
                    up = Integer.parseInt(String.valueOf(input.get(i-1).charAt(j)));
                    right = Integer.parseInt(String.valueOf(input.get(i).charAt(j+1)));
                    if(current < up && current < right)
                        lowPoints.add(current);
                } else if(i == input.size() - 1 && j == input.get(i).length() - 1) {
                    left = Integer.parseInt(String.valueOf(input.get(i).charAt(j-1)));
                    up = Integer.parseInt(String.valueOf(input.get(i-1).charAt(j)));
                    if(current < left && current < up)
                        lowPoints.add(current);
                } else {
                    down = i != input.size() - 1 ? Integer.parseInt(String.valueOf(input.get(i+1).charAt(j))) : Integer.MAX_VALUE;
                    up =  i != 0 ? Integer.parseInt(String.valueOf(input.get(i-1).charAt(j))) : Integer.MAX_VALUE;
                    left = j != 0 ? Integer.parseInt(String.valueOf(input.get(i).charAt(j-1))) : Integer.MAX_VALUE;
                    right = j != input.get(i).length() - 1 ? Integer.parseInt(String.valueOf(input.get(i).charAt(j+1))): Integer.MAX_VALUE;
                    if(current < left && current < down && current < right && current < up)
                        lowPoints.add(current);
                }
            }
        }

        int answer = 0;
        for(Integer number: lowPoints)
            answer += (number + 1);

        System.out.println(answer);
    }
}
