package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

public class Tortuga {
	private int x;
	  private int y;
	  private int alto = 44;
	  private int ancho = 60;
	  private int velocidad;
	  private boolean cayendo = true;
	  private Image turtleDer = Herramientas.cargarImagen("imagenes/turtle-der.png");
	  private Image turtleIzq = Herramientas.cargarImagen("imagenes/turtle-izq.png");

	  public Tortuga(int x) {
	    this.x = x;
	    this.y = 20;
	    Random rand = new Random();
	    boolean bool = rand.nextBoolean();
	    int num = rand.nextInt(3) + 1;
	    if (bool) {
	      this.velocidad = num;
	    } else {
	      this.velocidad = -1 * num;
	    }
	  }

	  public void dibujar(Entorno entorno) {
	    Image turtle = this.velocidad > 0 ? turtleDer : turtleIzq;
	    entorno.dibujarImagen(turtle, x, y, 0, 0.1);
	  }

	  public int getX() {
	    return x;
	  }

	  public int getY() {
	    return y + (alto / 2);
	  }

	  public int getAlto() {
	    return alto;
	  }

	  public int getAncho() {
	    return ancho;
	  }

	  public boolean getCayendo() {
	    return cayendo;
	  }

	  public int getVelocidad() {
	    return velocidad;
	  }

	  public void setCayendo(boolean cae) {
	    this.cayendo = cae;
	  }

	  public void rebotar() {
	    this.velocidad = this.velocidad * (-1);
	    this.x = this.x + this.velocidad;
	  }

	  public void mover() {
	    this.x = this.x + this.velocidad;
	  }

	  public void caer() {
	    this.y = this.y + 2;
	  }
	  
	  public int getYBase() {
		  return y+20;
	  }
	  
	  public int getYAltura() {
		  return y-20;
	  }
	  
	  public int getExtremoDer() {
			return x+25;
	  }
	  
	  public int getExtremoIzq() {
		  return x-25;
	  }
		

}
