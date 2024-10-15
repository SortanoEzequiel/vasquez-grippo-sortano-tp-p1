package juego;


import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	private Image fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
	private Pisos[] pisos; // creamos un vector de pisos
	
	Juego()
	{
		Random rand = new Random();
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		iniciarJuego();
		// Inicia el juego!
		this.entorno.iniciar();
	}
	 private void iniciarJuego(){
		 int cantPisos = 5 ;  // genero 5 pisos por defecto... se pueden modificar. 
		 this.pisos = new Pisos[cantPisos];
		 for (int i = 0; i < 5; i++) {
	            pisos[i] = new Pisos(600 - i * 150, 5, 80, 20); // Crear pisos con bloques cada uno
	        }
	 }
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...
		fondo = Herramientas.cargarImagen("imagenes/skyhill.png"); // cargamos la imagen de fondo
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 2.5); // la dibujamos.

		Image casita = Herramientas.cargarImagen("imagenes/house.png");
		entorno.dibujarImagen(casita, 400, 68, 0, 0.1);

		actualizarJuego();

	}

	private void actualizarJuego() {

		Image bloque = Herramientas.cargarImagen("imagenes/ground.png");
		entorno.dibujarImagen(bloque, 400, 110, 0, 0.5);
		entorno.dibujarImagen(bloque, 300, 200, 0, 0.5);
		entorno.dibujarImagen(bloque, 500, 200, 0, 0.5);
		entorno.dibujarImagen(bloque, 600, 300, 0, 0.5);
		entorno.dibujarImagen(bloque, 400, 300, 0, 0.5);
		entorno.dibujarImagen(bloque, 200, 300, 0, 0.5);
		entorno.dibujarImagen(bloque, 100, 400, 0, 0.5);
		entorno.dibujarImagen(bloque, 300, 400, 0, 0.5);
		entorno.dibujarImagen(bloque, 500, 400, 0, 0.5);
		entorno.dibujarImagen(bloque, 700, 400, 0, 0.5);
		entorno.dibujarImagen(bloque, 0, 500, 0, 0.5);
		entorno.dibujarImagen(bloque, 200, 500, 0, 0.5);
		entorno.dibujarImagen(bloque, 400, 500, 0, 0.5);
		entorno.dibujarImagen(bloque, 600, 500, 0, 0.5);
		entorno.dibujarImagen(bloque, 800, 500, 0, 0.5);

	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
