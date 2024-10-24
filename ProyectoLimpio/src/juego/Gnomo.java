package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Gnomo {
	private int x;
	private int y;
	private int radio;
	private int velocidad;
	
	public Gnomo(int x, int y, int radio, int velocidad) {	
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.velocidad = velocidad;
	}

	public void caer()
	{
		this.y = this.y + this.velocidad;
	}
	
	
	
	public void moverIzquierda()
	{
		this.x = this.x - this.velocidad;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + this.velocidad;
	}
	public void moverGnomo(){
	    Random random = new Random();
	    int randomInt = random.nextInt(2);
	    System.out.println(randomInt);
		if(randomInt == 0) {
			this.moverIzquierda();
		}
		this.moverDerecha();
	}
	
	public void dibujar(Entorno entorno)
	{
		Image gnomo = Herramientas.cargarImagen("imagenes/pep.png");
		entorno.dibujarImagen(gnomo, this.x, this.y, 0, 0.1);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getRadio() {
		return radio;
	}

	
	
	
	
}