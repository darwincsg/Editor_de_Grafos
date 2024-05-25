/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.ubi;

import java.awt.Point;

/**
 *
 * @author joels
 */
public class Board {
    private Point[][] BattleshipCoords = new Point[4][3];
    private Point[][] DestroyerCoords = new Point[5][2];
    private Point[] SubmarinesCoords = new Point[3];

    public Board() {
    }
    
    public Point getBattleShipCoords(int i, int j){
        return BattleshipCoords[i][j].getLocation();
    }
    
    public void setBattleShipCoords(int x, int y, int i, int j){
        this.BattleshipCoords[i][j] = new Point(x,y);
    }
    
    public Point getDestroyerCoords(int i, int j){
        return DestroyerCoords[i][j].getLocation();
    }
    
    public void setDestroyerCoords(int x, int y, int i, int j){
        this.DestroyerCoords[i][j] = new Point(x,y);
    }
    
    public Point getSubmarinesCoords(int i){
        return SubmarinesCoords[i].getLocation();
    }
    
    public void setSubmarinesCoords(int x, int y, int i){
        this.SubmarinesCoords[i] = new Point(x,y);
    }
    
}
