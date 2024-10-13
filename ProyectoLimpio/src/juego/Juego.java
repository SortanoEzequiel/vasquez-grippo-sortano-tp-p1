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
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");  // cargamos la imagen de fondo
		entorno.dibujarImagen(fondo,entorno.ancho() / 2, entorno.alto() / 2, 0); // la dibujamos. 
	    
		 Image casita = Herramientas.cargarImagen("imagenes/casita3.jpg");
         entorno.dibujarImagen(casita, 400, 63, 0);
		
		actualizarJuego();
	   
	}
	 private void actualizarJuego() {
	        
	            Image bloque = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque, 400, 100, 0);
	            
	            Image bloque2 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque2, 300, 200, 0);
	            
	            Image bloque3 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque3, 500, 200, 0);
	            
	            Image bloque4 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque4, 600, 300, 0);
	            
	            Image bloque5 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque5, 400, 300, 0);
	            
	            Image bloque6 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque6, 200, 300, 0);
	            
	            Image bloque7 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque7, 100, 400, 0);
	            
	            Image bloque8 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque8, 300, 400, 0);
	            
	            Image bloque9 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque9, 500, 400, 0);
	            
	            Image bloque10 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque10, 700, 400, 0);
	            
	            Image bloque11 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque11, 0, 500, 0);
	            
	            Image bloque12 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque12, 200, 500, 0);
	            
	            Image bloque13 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque13, 400, 500, 0);
	            
	            Image bloque14 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque14, 600, 500, 0);
	            
	            Image bloque15 = Herramientas.cargarImagen("imagenes/bloque1.jpg");
	            entorno.dibujarImagen(bloque15, 800, 500, 0);
	             
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
