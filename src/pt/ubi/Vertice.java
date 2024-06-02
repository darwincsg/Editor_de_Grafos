
package pt.ubi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Vertice {

    //Dimension size;
    private Point location;
    private String name;
    
    /*public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }*/

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public Vertice(){
    }
    
    public Vertice(Point location, String name) {
        this.location = location;
        this.name = name;
    }
    
}
