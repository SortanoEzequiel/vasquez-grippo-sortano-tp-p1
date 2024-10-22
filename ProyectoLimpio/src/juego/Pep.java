package juego;

import java.awt.Color;
import entorno.Herramientas;
import java.awt.Image;

import entorno.Entorno;

public class Pep {
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private Image pepDer;
	private Image pepIzq;
	private int saltosDisponibles;
	
	Pep(){
		this.x = 100;
		this.y = 400;
		this.ancho = 15;
		this.alto = 37;
		this.pepDer= Herramientas.cargarImagen("imagenes/pep.png");
		this.pepIzq= Herramientas.cargarImagen("imagenes/pepRot.png");
		this.saltosDisponibles=2;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + 3;
	}
	
	public void moverIzquierda()
	{
		this.x = this.x - 3;
	}
	
	/*public void dibujar(Entorno entorno)
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.GREEN);
	}
	
	public void dibujarImagen(Entorno entorno) {
		entorno.dibujarImagen(image, x, getTecho(), 0,0.2);
	}*/
	
	public void dibujarImagenEspejada(Entorno entorno, boolean direccion) {
		if (direccion)
			entorno.dibujarImagen(pepDer, x, getTecho(), 0,0.2);
		else
			entorno.dibujarImagen(pepIzq, x, getTecho(), 0,0.2);
	}
	
	public void caer() {
		this.y=this.y+2;
	}
	
	
	public void saltar() {
        if (this.saltosDisponibles > 0) {
            this.y -= (this.saltosDisponibles == 1) ? 150 : 70;
            saltosDisponibles--;
        }
    }
	
	public void reiniciarSaltos() {
		this.saltosDisponibles=2;
	}
	


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}
	
	public int getDer() {
		return x+(ancho/2);
	}
	
	public int getIzq() {
		return x-(ancho/2);
	}
	
	public int getBase() {
		return y+(alto/2);
	}
	
	public int getTecho() {
		return y-(alto/2);
	}


	public int getAncho() {
		return ancho;
	}


	public int getAlto() {
		return alto;
	}
}
