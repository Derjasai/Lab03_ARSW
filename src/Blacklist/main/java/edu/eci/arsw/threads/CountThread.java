/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import java.lang.*;
/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread{

    private int a;
    private int b;

    public void run(){
        for (int i = a; i <= b ; i++) {
            System.out.println(i);
            try {
                sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public CountThread(int a, int b){
        this.a = a;
        this.b = b;
    }
}
