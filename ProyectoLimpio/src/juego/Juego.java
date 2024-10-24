package juego;



import entorno.InterfaceJuego;

import java.awt.Image;
import entorno.Herramientas;
import entorno.Entorno;


public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	// ...
	private Image fondo;
	
	private Bloques bl;
	
	private Pep pep;
		
	
	
	Juego()
	{
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);		

		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		
		this.bl = new Bloques();
		
		this.pep = new Pep(); // Creo a PEP
				
		this.entorno.iniciar();// Inicia el juego!
		
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

		entorno.dibujarImagen(fondo,entorno.ancho() / 2, entorno.alto() / 2, 0);
				
		bl.dibujarBloques(entorno);
		
		Bloque[] bloq = bl.getTotalBloques();		
		
		
		if (this.pep !=null){
			
			boolean direccionPep= pep.moverPep(entorno);
			
			pep.dibujarPep(entorno, direccionPep);
						
			boolean pepNull = pep.pepSobreBloques(bloq);
			
			if (pepNull) {
				this.pep=null;
				System.out.println(pepNull);
			}
						
		}		

	}	
	
	

	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
