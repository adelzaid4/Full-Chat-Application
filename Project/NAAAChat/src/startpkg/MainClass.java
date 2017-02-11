/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startpkg;

import viewpkg.ViewController;

/**
 *
 * @author Nour
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(ViewController.class);
            }
        }.start();
    }
    
}
