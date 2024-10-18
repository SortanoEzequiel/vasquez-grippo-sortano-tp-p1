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
	
	private Bloques bl;
	
	private Pep pep;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		this.bl = new Bloques();
		this.pep = new Pep(100,490,30,30); // Creo a PEP
		// Inicia el juego!
		this.entorno.iniciar();
		
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
		entorno.dibujarImagen(fondo,entorno.ancho() / 2, entorno.alto() / 2, 0);
		
		bl.dibujarBloques(entorno);
		
		pep.dibujar(entorno);
		
		Bloque[] bloq = bl.getTotalBloques();
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.pep.moverDerecha();
		}
		if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.pep.moverIzquierda();
		}
		if(this.entorno.sePresiono(this.entorno.TECLA_ARRIBA)) {
			this.pep.saltar();
		}	
		
		if(this.entorno.sePresiono(this.entorno.TECLA_ABAJO)) {
			this.pep.bajar();
		}
		
		if (pep.getY()<bloq[0].getY()-30) {
			pep.caer();
		}
		
		//if (pep.getX()>bloq[0].getX()*2-25 && pep.getX()<bloq[0].getX()*3-75 || pep.getY()>550) {
		//	pep.caer();
		//}
		
		if (pep.getX()< bloq[0].getXizq() || pep.getX()>bloq[0].getXder() && pep.getX()<bloq[0].getXder()+50 || pep.getY()>550) {
			pep.caer();
		}
		
		if (pep.getX()>bloq[1].getXder() && pep.getX()<bloq[1].getXder()+50 || pep.getY()>550) {
			pep.caer();
		}
		if (pep.getX()>bloq[2].getXder() && pep.getX()<bloq[2].getXder()+50 || pep.getY()>550) {
			pep.caer();
		}
		if (pep.getX()>bloq[3].getXder()) {
			pep.caer();
		}
		
		
		
		
		entorno.dibujarCirculo(100, 525, 5, null);
		//entorno.dibujarRectangulo(100, 525, 140, 40, 0, null);


	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
