package juego;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.awt.Image;
// import java.awt.Color;
// import java.awt.Font;
// import java.util.Random;

public class Juego extends InterfaceJuego {
	private Entorno entorno;

	private Image fondo;
	private Image casa;
	private int[] pisosX;
	private int[] pisosY;
	private Piso[] pisos;
	private Tortuga[] tortugas;

	Juego() {
		this.entorno = new Entorno(this, "TP Vasquez Grippo Sortano", 800, 600);
		this.iniciarJuego();
		this.entorno.iniciar();
	}

	private void iniciarJuego() {
		int[] xValues = { 400, 300, 500, 200, 400, 600, 100, 300, 500, 700, 0, 200, 400, 600, 800 };
		int[] yValues = { 130, 220, 220, 320, 320, 320, 420, 420, 420, 420, 520, 520, 520, 520, 520 };
		fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");
		casa = Herramientas.cargarImagen("imagenes/house.png");
		pisosX = xValues;
		pisosY = yValues;
		pisos = new Piso[pisosX.length];
		tortugas = new Tortuga[4];
		for (int i = 0; i < tortugas.length; i++) {
			tortugas[i] = new Tortuga();
		}
	}

	public void tick() {
		this.entorno.dibujarImagen(this.fondo, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0);
		this.actualizarPisos();
		this.entorno.dibujarImagen(this.casa, 400, 62, 0, 0.25);
		this.actualizarTortugas();
	}

	private void actualizarPisos() {
		for (int i = 0; i < this.pisos.length; i++) {
			if (this.pisos[i] == null) {
				this.pisos[i] = new Piso(this.pisosX[i], this.pisosY[i]);
			}
			this.pisos[i].dibujar(entorno);
		}
	}

	private void actualizarTortugas() {
		for (int i = 0; i < this.tortugas.length; i++) {
			this.tortugas[i].dibujar(this.entorno);
		}
		for (int i = 0; i < this.pisos.length; i++) {
			for (int j = 0; j < this.tortugas.length; j++) {
				if (TortugaSobrePiso(this.pisos[i], this.tortugas[j])) {
					this.tortugas[j].setCayendo(false);
					if (tortugaPisaBorde(this.pisos[i], this.tortugas[j])) {
						this.tortugas[j].rebotar();
					}
					this.tortugas[j].mover();
				}
			}
		}
		for (int i = 0; i < this.tortugas.length; i++) {
			if (this.tortugas[i].getCayendo()) {
				this.tortugas[i].caer();
			}
		}
	}

	private boolean TortugaSobrePiso(Piso piso, Tortuga tortuga) {
		return colision(piso.getY(), tortuga.getY()) && piso.getX() - piso.getAncho() / 2 < tortuga.getX()
				&& tortuga.getX() < piso.getX() + piso.getAncho() / 2;
	}

	private boolean tortugaPisaBorde(Piso piso, Tortuga tortuga) {
		return colision(tortuga.getX(), piso.getX() - piso.getAncho() / 2)
				|| colision(tortuga.getX(), piso.getX() + piso.getAncho() / 2);
	}

	private boolean colision(int a, int b) {
		return Math.abs(a - b) <= 3;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
