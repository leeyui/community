package com.community.community.controller;


public class Test {
    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6};
//        myreverse(6,a);
        int len = a.length;
        int tmp;
        int j;
        for (int n = 0;n < len ; n++) {
            for (int m = len - 1; n < m; m--) {
                tmp  = a[n];
                a[n] = a[m];
                a[m] = tmp;
            }
        }
        for (int i : a) {
            System.out.println(i);
        }
        System.out.println("----");
        myreverse(5,a);
        for (int i : a) {
            System.out.println(i);
        }
    }
    static int[] myreverse(int n,int [] a) {
        if (n == -1) {
            return a;
        } else {
            myreverse(n - 1,a);
            return swap(a.length - n - 1 ,a);
        }
    }
    static int[] swap(int n, int[] a) {
        int tmp ;
        tmp = a[n];
        a[n] = a[n - 1];
        a[n - 1] = tmp;
        return a;
    }

}
