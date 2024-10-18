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
	
	private Pep p;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		this.bl = new Bloques();
		this.p = new Pep(100,490,30,30);
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
		
		p.dibujar(entorno);
		
		Bloque[] bloq = bl.getTotalBloques();
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.p.moverDerecha();
		}
		if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.p.moverIzquierda();
		}
		if(this.entorno.sePresiono(this.entorno.TECLA_ARRIBA)) {
			this.p.saltar();
		}	
		
		if(this.entorno.sePresiono(this.entorno.TECLA_ABAJO)) {
			this.p.bajar();
		}
		
		if (p.getY()<bloq[0].getY()-30) {
			p.caer();
		}
		
		
		if (p.getX()>bloq[0].getX()*2-25 && p.getX()<bloq[0].getX()*3-75 || p.getY()>550) {
			p.caer();
		}


	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
