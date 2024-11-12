package juego;

import java.awt.Color;
import entorno.Entorno;

public class DisparoPep {

	private int x;
	private int y;
	private int velocidad;
	private int radioDisparoPep;
	private boolean direccion;

	public DisparoPep(int x, int y, int velocidad, int radioDisparoPep, boolean direccion) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.radioDisparoPep = radioDisparoPep;
		this.direccion = direccion;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public int getRadioDisparoPep() {
		return radioDisparoPep;
	}

	public void moverDerecha() {
		this.x = this.x + 3;
	}

	public void moverIzquierda() {
		this.x = this.x - 3;
	}

	public void dibujarDisparo(Entorno entorno) {
		entorno.dibujarCirculo(x, y, radioDisparoPep, Color.red);
	}

	public boolean getDireccion() {
		return direccion;
	}

	public void moverDisparo() {
		if (direccion) {
			this.x += this.velocidad;
		} else {
			this.x -= this.velocidad;
		}
	}
}