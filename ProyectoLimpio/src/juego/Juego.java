package juego;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.awt.Image;
// import java.awt.Color;
// import java.util.Random;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private Image fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");
	private Image casa = Herramientas.cargarImagen("imagenes/house.png");
	private int[] pisosX = { 400, 300, 500, 200, 400, 600, 100, 300, 500, 700, 0, 200, 400, 600, 800 };
	private int[] pisosY = { 130, 220, 220, 320, 320, 320, 420, 420, 420, 420, 520, 520, 520, 520, 520 };
	private Piso[] pisos = new Piso[pisosX.length];

	Juego() {
		// Random rand = new Random();
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		iniciarJuego();
		this.entorno.iniciar();
	}

	private void iniciarJuego() {
	}

	public void tick() {
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		actualizarPisos();
		entorno.dibujarImagen(casa, 400, 62, 0, 0.25);
	}

	private void actualizarPisos() {
		for (int i = 0; i < pisos.length; i++) {
			if (pisos[i] == null) {
				pisos[i] = new Piso(pisosX[i], pisosY[i]);
			}
			pisos[i].dibujar(entorno);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
