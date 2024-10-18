package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bloques {
	private Bloque[] totalBloques;
	private Image bloque;
	private int x;
	private int x2;
	private int x3;
	private int x4;
	private int y;
	
	
	Bloques(){
		this.totalBloques= new Bloque[10];
		this.x=100;
		this.x2=200;
		this.x3=300;
		this.x4=400;
		this.y=525;		
		this.bloque = Herramientas.cargarImagen("imagenes/bloque1.jpg");

		for (int i=0;i<totalBloques.length;i++) {
			if (i<4) {
				totalBloques[i]= new Bloque(bloque, this.x, this.y);
				this.x+=200;
			}
			else if (i<7) {
				this.y=400;
				totalBloques[i]= new Bloque(bloque, this.x2, this.y);
				this.x2+=200;
			}
			else if (i<9) {
				this.y=275;
				totalBloques[i]= new Bloque(bloque, this.x3, this.y);
				this.x3+=200;
			}else
				totalBloques[i]= new Bloque(bloque, this.x4, 150);
			
		}
	}
	
	void dibujarBloques(Entorno entorno) {
		for (int i=0;i<totalBloques.length;i++) {
			entorno.dibujarImagen(totalBloques[i].getImg(),totalBloques[i].getX(),totalBloques[i].getY(), 0);
		}		
	}
	
	Bloque[] getTotalBloques() {
	    return totalBloques;
	}
}
