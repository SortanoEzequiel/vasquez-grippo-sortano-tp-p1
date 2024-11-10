package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Gnomo {
	private int x;
	private int y;
	private int radio;
	private int velocidad;
	public boolean direccion;
	public boolean seCayo;
	public boolean esActivo;
	
	public Gnomo() {	
		this.x = 400;
		this.y = 130-17;
		this.radio = 30;
		this.velocidad = 1;
		this.direccion=inicioRandom();
		this.seCayo=false;
		this.esActivo=false;
	}
	
	public void asignar(Gnomo[] gnomos){
		for(int i = 0; i <gnomos.length; i++) {
			gnomos[i] = new Gnomo();
		}
		gnomos[0].activar();
	}

	public void caer()
	{
		this.y = this.y + this.velocidad;
	}
	
	public void activar() {
		esActivo=true;		
	}
	
	public boolean inicioRandom() {
		Random random = new Random();
		int randomInt = random.nextInt(2);
		if(randomInt == 0) {
			return false;
		}
		return true;
	}	
	
	
	public boolean getDireccion() {
		return direccion;
	}
			
	
	
	
	public void moverIzquierda()
	{
		this.x = this.x - this.velocidad;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + this.velocidad;
	}
	
	
	public void moverGnomo(Gnomo gnomo){
				
		if(gnomo.direccion) {
			gnomo.moverDerecha();
		}else {
			gnomo.moverIzquierda();
		}
		
	}
	
	public void dibujar(Entorno entorno)
	{
		Image gnomo = Herramientas.cargarImagen("imagenes/gnomo.png");
		entorno.dibujarImagen(gnomo, x, y, 0, 0.015);
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
	
	public int getExtremoDer() {
		return x+15;
	}
	
	public int getExtremoIzq() {
		return x-15;
	}
	
	public int getYBase() {
		return y+20; //modificar para la base
	}
	
	public int getYAltura() {
		return y-20;
	}
	
	public int getVelocidad() {
		return velocidad;
	}

	
	
	
	
}