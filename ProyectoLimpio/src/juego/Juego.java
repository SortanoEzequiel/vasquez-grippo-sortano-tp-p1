package juego;



import entorno.InterfaceJuego;

import java.awt.Color;
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
	
	private Image casita;
	
	private Bloques bl;
	
	private Bloque[] bloq;
	
	private Pep pep;
	
	private Gnomo gnomo;
	
	private Gnomo[] gnomos;	
	
	private Tortuga tortuga;
	
	private int cantActivos;
				
	
	
	Juego()
	{
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);		

		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		
		this.casita= Herramientas.cargarImagen("imagenes/casita.png");		
		
		this.bl = new Bloques();
		
		this.gnomo = new Gnomo();
		
		this.pep = new Pep(); // Creo a PEP
		
		this.gnomos= new Gnomo[30];
		
		gnomo.asignar(gnomos);		
		
		this.tortuga = new Tortuga(400);
		
		this.cantActivos=0;
				
		this.bloq = bl.getTotalBloques();	
						
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
		
		entorno.dibujarImagen(casita, 400, 100, 0,0.11);
		
		tortuga.dibujar(entorno);
		
		if (this.pep != null){
			
			
			boolean direccionPep= pep.moverPep(entorno);
			
			pep.dibujarPep(entorno, direccionPep);
						
			boolean pepNull = pep.pepSobreBloques(bloq);
			
			for (int i=0;i<gnomos.length;i++) {
				
				if (gnomos[i]!=null) {
					if (gnomos[i].esActivo ) { 
						gnomo.lanzarGnomo(entorno, gnomos, bloq,i); //probar poner el booleano de si esta activo en gnomo.lanzargnomo(entorno, gnomos[0], bloq, ESACTIVO)
					}
					if (((pep.getDer()+10>=gnomos[i].getX()-20 && pep.getIzq()-15<=gnomos[i].getX()+20) && (gnomos[i].getY()> pep.getTecho()-30 && gnomos[i].getY()< pep.getTecho()+40)) && (pep.getTecho()-30>300) || gnomos[i].getY()>700) {
						gnomos[i]=null;
						System.out.println(cantActivos);
					}
				}
				
				
				/*entorno.dibujarCirculo(pep.getDer()+10, pep.getY(), 10, Color.green);
				entorno.dibujarCirculo(gnomos[i].getX()-20, gnomos[i].getY(), 10, Color.red);				
				entorno.dibujarCirculo(gnomos[i].getX()+20, gnomos[i].getY(), 10, Color.orange);	
				entorno.dibujarCirculo(pep.getIzq()-15, pep.getY(), 10, Color.yellow);*/
								
				
				
				
				
			}	
			
			
			
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
