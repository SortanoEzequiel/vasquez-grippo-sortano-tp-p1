package juego;

import java.awt.Image;

public class Bloque {
	private int x;
	private int y;
	private Image img;
	
	Bloque(Image imagen,int x,int y){
		this.x=x;
		this.y=y;
		this.img=imagen;				
	}
	
	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }
}
