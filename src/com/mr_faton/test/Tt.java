package com.mr_faton.test;


/**
 * Created by root on 04.05.2015.
 */
public class Tt {
    public static void main(String[] args)  {
        int q = 50;
        int y = 81;
        double d = (double)q/y * 100;
        System.out.println(d);

        int e = (int) Math.round((double)q/y * 100);

        System.out.println(e);
    }

}
