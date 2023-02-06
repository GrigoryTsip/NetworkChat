package ru.netology;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String s = "@@_%Date and time@@_%id talkshow@@_%@nickname1@@_%@nickname2@nickname3@@_%message body@@_%";
        String[] as;

        as = s.split("@@_%");
        System.out.println(Arrays.toString(as));
    }
}