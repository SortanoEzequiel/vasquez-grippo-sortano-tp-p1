package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

public class Tortuga {
	private int x;
	  private int y;
	  static private int alto = 44;
	  static private int ancho = 60;
	  private int velocidad;
	  private boolean cayendo = true;
	  private Image turtleDer = Herramientas.cargarImagen("imagenes/turtle-der.png");
	  private Image turtleIzq = Herramientas.cargarImagen("imagenes/turtle-izq.png");
	  public boolean sobreBloque = false;
	  public boolean activar=false;

	  public Tortuga() {
	    this.x = randomX(posicionUtilizada);
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
	  
	  public int[] posicionUtilizada = new int[7];
	  
	  public int randomX(int[] posicionUtilizada) {
		  int[] posicion = new int[7];
		  int posInicial = 100;
		  
		  Random r = new Random();
		  
		  for (int i=0; i<posicion.length;i++) {
			  posicion[i]=posInicial;
			  posInicial+=100;
			  System.out.println(posicion[i]);
		  }
		  
		  int posRandom = r.nextInt(posicion.length);

		  for (int pos = 0; pos < posicionUtilizada.length; pos++) {
			  
		        posRandom = r.nextInt(posicion.length);

		        // Si la posición no ha sido utilizada, la seleccionamos y la marcamos
		        if (posicionUtilizada[pos]!=posicion[posRandom]) {
		            posicionUtilizada[posRandom] = posicion[posRandom];  // Marcamos la posición como utilizada
		            break;  // Salimos del bucle una vez que encontramos una posición válida
		        }
		  }
		  return posicionUtilizada[posRandom];
		  
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

	  static public int getAlto() {
	    return alto;
	  }

	  static public int getAncho() {
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
	    this.y = this.y + 3;
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