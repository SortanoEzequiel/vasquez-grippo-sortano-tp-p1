package juego;

import java.awt.Color;

import entorno.Entorno;

public class Pep {
	private int x;
	private int y;
	private int alto;
	private int ancho;
	
	Pep(int x, int y, int ancho, int alto){
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + 3;
	}
	
	public void moverIzquierda()
	{
		this.x = this.x - 3;
	}
	
	public void dibujar(Entorno entorno)
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.GREEN);
	}
	
	public void caer() {
		this.y=this.y+4;
	}
	
	public void saltar() {
		this.y-=200;
		
	}
	public void bajar() {
		this.y+=50;
		
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getAncho() {
		return ancho;
	}


	public int getAlto() {
		return alto;
	}
}
