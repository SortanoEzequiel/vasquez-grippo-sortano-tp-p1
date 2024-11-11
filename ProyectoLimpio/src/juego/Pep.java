package juego;

import entorno.Herramientas;
import java.awt.Image;

import entorno.Entorno;

public class Pep {
	private int x;
	private int y;
	private Image pepDer;
	private Image pepIzq;
	private int saltosDisponibles;
	private boolean direccion;
	
	
	Pep(){
		this.x = 100;
		this.y = 400;
		this.pepDer= Herramientas.cargarImagen("imagenes/pep-der.png");
		this.pepIzq= Herramientas.cargarImagen("imagenes/pep-izq.png");
		this.saltosDisponibles=2;
		this.direccion=true;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + 3;
	}
	
	public void moverIzquierda()
	{
		this.x = this.x - 3;
	}
	
	
	public void dibujarPep(Entorno entorno, boolean direccion) {
		if (direccion)
			entorno.dibujarImagen(pepDer, x, y, 0,0.2); //modificar 
		else
			entorno.dibujarImagen(pepIzq, x, y, 0,0.2); //modificar getaltura
	}
	
	public void caer() {
		this.y=this.y+3;
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
	
	public boolean moverPep(Entorno entorno) {
		
        if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
            moverDerecha();
            direccion= true;
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
            moverIzquierda();
            direccion= false;
        }
        if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w')) {        	
        	saltar();
        }
        
        return direccion;
    }	
	

		


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}
	
	public int getExtremoDer() {
		return x+15;
	}
	
	public int getExtremoIzq() {
		return x-20;
	}
	
	public int getYBase() {
		return y+38;
	}
	
	public int getYAltura() {
		return y-30;
	}
}
