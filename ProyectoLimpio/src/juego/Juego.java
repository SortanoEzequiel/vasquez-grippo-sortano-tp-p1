package juego;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.util.Random;
import java.awt.Image;
// import java.awt.Color;
// import java.awt.Font;

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
		Random rand = new Random();
		int[] xValues = { 400, 300, 500, 200, 400, 600, 100, 300, 500, 700, 0, 200, 400, 600, 800 };
		int[] yValues = { 130, 220, 220, 320, 320, 320, 420, 420, 420, 420, 520, 520, 520, 520, 520 };
		fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");
		casa = Herramientas.cargarImagen("imagenes/house.png");
		pisosX = xValues;
		pisosY = yValues;
		pisos = new Piso[pisosX.length];
		tortugas = new Tortuga[1];
		for (int i = 0; i < tortugas.length; i++) {
			int num;
			do {
				num = rand.nextInt(800) + 1;
				num = 134;
			} while (400 - Piso.getAncho() / 2 < num && num < 400 + Piso.getAncho() / 2);
			tortugas[i] = new Tortuga(num);
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
		Random rand = new Random();
		// boolean pasarse = rand.nextInt(5) == 0;
		boolean pasarse = false;
		for (int i = 0; i < this.tortugas.length; i++) {
			this.tortugas[i].dibujar(this.entorno);
		}
		for (int i = 0; i < this.pisos.length; i++) {
			for (int j = 0; j < this.tortugas.length; j++) {
				if (TortugaSobrePiso(this.pisos[i], this.tortugas[j])) {
					this.tortugas[j].setCayendo(false);
					this.tortugas[j].mover();
					if (tortugaLlegaABorde(this.pisos[i], this.tortugas[j]) && pasarse) {
						this.tortugas[j].rebotar();
					} else if (pasarse) {
						this.tortugas[j].setCayendo(true);
						this.tortugas[j].caer();
					}
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
		return piso.getY() - Piso.getAlto() / 2 == tortuga.getY() + Tortuga.getAlto() / 2
				&& piso.getX() - Piso.getAncho() / 2 < tortuga.getX() + Tortuga.getAncho() / 2
				&& tortuga.getX() - Tortuga.getAncho() / 2 < piso.getX() + Piso.getAncho() / 2;
	}

	private boolean tortugaLlegaABorde(Piso piso, Tortuga tortuga) {
		boolean colisionIzq = tortuga.getX() - Tortuga.getAncho() / 2 == piso.getX() - Piso.getAncho() / 2
				&& tortuga.getX() > piso.getX() - Piso.getAncho() / 2 && tortuga.getDireccion() == "izquierda";
		boolean colisionDer = tortuga.getX() + Tortuga.getAncho() / 2 == piso.getX() + Piso.getAncho() / 2
				&& tortuga.getX() < piso.getX() + Piso.getAncho() / 2 && tortuga.getDireccion() == "derecha";
		return colisionIzq || colisionDer;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
