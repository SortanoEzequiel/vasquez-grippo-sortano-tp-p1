package juego;

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
	private boolean direccion;
	
	Pep(){
		this.x = 100;
		this.y = 400;
		this.ancho = 15;
		this.alto = 37;
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
			entorno.dibujarImagen(pepDer, x, getTecho(), 0,0.2);
		else
			entorno.dibujarImagen(pepIzq, x, getTecho(), 0,0.2);
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
		
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            moverDerecha();
            direccion= true;
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            moverIzquierda();
            direccion= false;
        }
        if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {        	
        	saltar();
        }
        
        return direccion;
    }	
	
	public boolean pepSobreBloques(Bloque[] bloq) {
		
		boolean enBloque=false;		
					
		for (int i=0;i<bloq.length;i++) {
			if (getBase()>= bloq[i].getSup() && getDer()> bloq[i].getXizq() && getIzq()<bloq[i].getXder() && getBase()<bloq[i].getInf())
				enBloque=true;
		}
		
		if (getTecho()>800) {
			return true;
		}else {
			if (!enBloque) {
				caer();
			}else {
				reiniciarSaltos();								
			}
		}		
		return false;
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
