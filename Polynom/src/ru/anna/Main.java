package ru.anna;

import ru.anna.polynoms.Polynom;

public class Main {
    public static void main(String[] args) {
        double[] c1 = {1, 2, 3};
        double[] c2 = {-1, -2, -2};
        Polynom p1 = new Polynom(c1);
        Polynom p2 = new Polynom(c2);
        Polynom p3 = p1.plus(p2);

        System.out.println(p1);
//        System.out.println("P1(1)="+p1.invoke(1));
        System.out.println(p2);
//        System.out.println("P2(-2)="+p2.invoke(-2));
        System.out.println(p3);
//        System.out.println("P3(5)="+p3.invoke(5));
    }
}
