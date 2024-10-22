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
	
	private boolean direccion=true;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		this.bl = new Bloques();
		this.pep = new Pep(); // Creo a PEP
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
		
		
		if (this.pep !=null){
			//pep.dibujar(entorno);
			
			boolean direccion= moverPep();
			
			pep.dibujarImagenEspejada(entorno, direccion);
						
			pepSobreBloques();
		}
		
		
		
		
		/*
		 
		Bloque[] bloq = bl.getTotalBloques();		
		
		boolean enBloque=false;	
		
		if (pep.getBase()>= bloq[9].getSup() && pep.getDer()> bloq[9].getXizq() && pep.getIzq()<bloq[9].getXder() && pep.getBase()<bloq[9].getInf()) 
				enBloque=true;
			else {
				enBloque=false;
			}
		
		
		if (pep.getBase()>= bloq[8].getSup() && pep.getDer()> bloq[8].getXizq() && pep.getIzq()<bloq[8].getXder() && pep.getBase()<bloq[8].getInf() || pep.getBase()>= bloq[8].getSup() && pep.getDer()> bloq[7].getXizq() && pep.getIzq()<bloq[7].getXder() && pep.getBase()<bloq[7].getInf()) 
				enBloque=true;
			else {
				enBloque=false;
		}
		
		if (pep.getBase()>= bloq[6].getSup()){
			if (pep.getDer()> bloq[6].getXizq() && pep.getIzq()<bloq[6].getXder() && pep.getBase()<bloq[6].getInf() || pep.getDer()> bloq[5].getXizq() && pep.getIzq()<bloq[5].getXder() && pep.getBase()<bloq[5].getInf() || pep.getDer()> bloq[4].getXizq() && pep.getIzq()<bloq[4].getXder() && pep.getBase()<bloq[4].getInf()) 
				enBloque=true;
			else {
				enBloque=false;
			}			
		}
		if (pep.getBase()>= bloq[0].getSup()){
			if (pep.getDer()> bloq[0].getXizq() && pep.getIzq()<bloq[0].getXder() && pep.getBase()<bloq[0].getInf() || pep.getDer()> bloq[1].getXizq() && pep.getIzq()<bloq[1].getXder() && pep.getBase()<bloq[1].getInf() || pep.getDer()> bloq[2].getXizq() && pep.getIzq()<bloq[2].getXder() && pep.getBase()<bloq[2].getInf() || pep.getDer()> bloq[3].getXizq() && pep.getIzq()<bloq[3].getXder() && pep.getBase()<bloq[3].getInf()) 
				enBloque=true;
			else {
				enBloque=false;
			}			
		}
					
			
		
				
		entorno.dibujarCirculo(bloq[1].getXder()+65, bloq[9].getInf(), 5, Color.BLUE);*/
		//entorno.dibujarRectangulo(100, 525, 140, 40, 0, null);
		

	}
	
	
	private boolean moverPep() {
		
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            pep.moverDerecha();
            direccion= true;
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            pep.moverIzquierda();
            direccion= false;
        }
        if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {        	
        	pep.saltar();
        }
        
        return direccion;
    }	
	
	
	
	private void pepSobreBloques() {
		
		Bloque[] bloq = bl.getTotalBloques();		
		
		boolean enBloque=false;		
					
		for (int i=0;i<bloq.length;i++) {
			if (pep.getBase()>= bloq[i].getSup() && pep.getDer()> bloq[i].getXizq() && pep.getIzq()<bloq[i].getXder() && pep.getBase()<bloq[i].getInf())
				enBloque=true;
		}
		
		if (pep.getTecho()>800) {
			this.pep=null;
		}else {
			if (!enBloque) {
				pep.caer();
			}else {
				pep.reiniciarSaltos();								
			}
		}		
		
	}
	

	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
