package juego;

import java.util.concurrent.TimeUnit;
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
	private Gnomo[] gnomos;
	private Bloques bl;

	
	Juego()
	{
		Random rand = new Random();
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.bl = new Bloques();
	
		this.gnomos= new Gnomo[4];
		
		for(int i = 0; i <this.gnomos.length; i++) {
			gnomos[i] = new Gnomo(400, 118,30, 1);
		}
		
		
		
	
		// Inicia el juego!
		this.entorno.iniciar();
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
		
		bl.dibujarBloques(entorno);
		
		Bloque[] bloq = bl.getTotalBloques();
		
		Image casita = Herramientas.cargarImagen("imagenes/house.png");
		entorno.dibujarImagen(casita, 400, 106, 0, 0.1);
	   
			for(int i = 0 ; i < gnomos.length; i++) {
				gnomos[i].dibujar(entorno);
				if(gnomos[i].getY() == 118) gnomos[i].moverIzquierda();
				
				if(bordeBloque(gnomos[i],bloq[9])) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[7])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[7]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 275 && gnomos[i].getY() + gnomos[i].getRadio()  < 400) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[8])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[8]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 275 && gnomos[i].getY() + gnomos[i].getRadio()  < 400) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[5])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[5]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 400 && gnomos[i].getY() + gnomos[i].getRadio()  < 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[4])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[4]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 400 && gnomos[i].getY() + gnomos[i].getRadio()  < 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[6])) {
					gnomos[i].moverDerecha();
				}
				if(bordeBloque(gnomos[i],bloq[6]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 400 && gnomos[i].getY() + gnomos[i].getRadio()  < 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[3])) {
					gnomos[i].moverDerecha();
				}
				if(bordeBloque(gnomos[i],bloq[3]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[2])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[2]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[1])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[1]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 525) gnomos[i].caer();
				
				if(colisionBloqueGnomo(gnomos[i], bloq[0])) {
					gnomos[i].moverIzquierda();
				}
				if(bordeBloque(gnomos[i],bloq[0]) && gnomos[i].getY() + gnomos[i].getRadio()  >= 525) gnomos[i].caer();
				

			
				
				
				
				
			
				
					
				
					
						
					
					 
	    
			   System.out.print(gnomos[i].getY() + gnomos[i].getRadio());
		     }
		
		
		
		
		
			
		

	}

	
	private boolean colisionBloqueGnomo(Gnomo gnomos, Bloque bloq)
	{
		boolean colisionX = bloq.getXizq()  < gnomos.getX() &&
				            bloq.getXder()  > gnomos.getX();
							
		boolean colisionY = gnomos.getY() + gnomos.getRadio() == bloq.getY();
							
		return colisionX && colisionY;
	}
    
	private boolean bordeBloque(Gnomo gnomos,Bloque bloq) {
		
		boolean bloque = gnomos.getX() == bloq.getXizq() || gnomos.getX() == bloq.getXder();
		return bloque ;
	}
	
private void moverGnomo(Gnomo gnomos) {
	    Random random = new Random();
	    int randomInt = random.nextInt(1);
	    System.out.println(randomInt);
		if(randomInt == 0) {
			gnomos.moverDerecha();
		}
		gnomos.moverIzquierda();
	}

	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
