package juego;



import entorno.InterfaceJuego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

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
	
	private Image Turtle;
	
	private Image Gnomo;
	
	private Image Reloj;
	
	private Bloques bl;
	
	private Bloque[] bloq;
	
	private Pep pep;
	
	private Gnomo gnomo;
	
	private Gnomo[] gnomos;	
	
	private int vidas;
	
	private Tortuga[] tortugas;
	
	private DisparoPep disparoPep;
	
	private int cantNull;
	
	private int gnomosRescatados;
				
	private int gnomosPerdidos;
	
	private int enemigosEliminados;
	
	private int tiempo;

	private int juegoGanado;
	
	private int juegoPerdido;
	
	boolean ganador=false;
	
	
	Juego()
	{
		
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);		
        
		iniciarJuego();
						
		this.entorno.iniciar();// Inicia el juego!
		
	}
	
	private void iniciarJuego() {
		Random rand = new Random();
		this.fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");
		
		this.casita= Herramientas.cargarImagen("imagenes/casita.png");	
		
		this.Turtle = Herramientas.cargarImagen("imagenes/turtle-der.png");
		
		this.Gnomo =  Herramientas.cargarImagen("imagenes/gnomo.png");
		
		this.Reloj = Herramientas.cargarImagen("imagenes/rel.png");
		this.gnomosRescatados = 0;
		this.gnomosPerdidos = 0;
	    this.enemigosEliminados = 0;
	    this.vidas=5;
	    this.tiempo = this.entorno.tiempo();
		 
		this.bl = new Bloques();
		
		this.gnomo = new Gnomo();
		
		this.pep = new Pep(); // Creo a PEP
        
		this.cantNull=0;
		
		this.juegoGanado=15;
		
		this.juegoPerdido=10;
				
		
		this.gnomos= new Gnomo[4];
		
		gnomo.asignar(gnomos);		
		
		this.tortugas = new Tortuga[3];
		for (int i = 0; i < tortugas.length; i++) {
			int num;
			do {
				num = rand.nextInt(800) + 1;
			} while (400 - Bloque.getAncho() / 2 < num && num < 400 + Bloque.getAncho() / 2);
			tortugas[i] = new Tortuga(num);
		}
		
				
		this.bloq = bl.getTotalBloques();	
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
		entorno.cambiarFont("Arial Black", 20, Color.black);
		entorno.dibujarImagen(fondo,entorno.ancho() / 2, entorno.alto() / 2, 0);
	
		if (this.pep != null){
		
		entorno.dibujarImagen(Turtle, 30, 30, 0, 0.06);
		entorno.escribirTexto("Eliminadas: "+ enemigosEliminados, 65, 43);
		entorno.dibujarImagen(Gnomo, 30, 70, 0, 0.014);
		entorno.escribirTexto("Rescatados: "+ gnomosRescatados, 65, 85); //Se escribe el texto y la cantidad e gnomos rescatados 		
		entorno.dibujarImagen(Gnomo, 30, 120, 0, 0.014);
		entorno.escribirTexto("Perdidos: "+ gnomosPerdidos, 65, 130);
		entorno.dibujarImagen(Reloj, 360, 25, 0, 0.014);
		entorno.escribirTexto(MinutosTranscurrido() + ":"+ SegundosTranscurrido()+"s", 380, 30);
		bl.dibujarBloques(entorno);
		entorno.dibujarImagen(casita, 400, 100, 0,0.11);
		
		
		    
		    
		    actualizarTortugas();
				
			
			
			
			
			
			
						
			boolean pepNull = pepSobreBloques(bloq);
			boolean direccionPep= pep.moverPep(entorno);
			pep.dibujarPep(entorno, direccionPep);
		  
			
			for(int i=0;i<tortugas.length;i++) {
				if(tortugas[i] != null && pep != null) {
					if(colisionPepTortuga(i))
					   	this.pep=null;
				   
					   
				}
				
			}
			  
				
			for (int i=0;i<gnomos.length;i++) {
			
				if (gnomos[i]!=null) {
					
					lanzarGnomo(entorno, gnomos, bloq,i); //probar poner el booleano de si esta activo en gnomo.lanzargnomo(entorno, gnomos[0], bloq, ESACTIVO)
					
					if(pep != null) {
					if (((pep.getExtremoDer()>=gnomos[i].getExtremoIzq() && pep.getExtremoIzq()<=gnomos[i].getExtremoDer()) && (pep.getYBase() >= gnomos[i].getY()  && pep.getYAltura() <= gnomos[i].getY() )) && (pep.getYAltura()>=300)) {
						gnomos[i]=null;
						this.gnomos[i] = new Gnomo();
						gnomosRescatados++;
					}
					if(gnomos[i].getY()>600) {
						gnomos[i]=null;
						this.gnomos[i] = new Gnomo();
						gnomosPerdidos++;
					}
					for(Tortuga tortuga:tortugas) {
						if((tortuga.getExtremoDer()>=gnomos[i].getX() && tortuga.getExtremoIzq() <= gnomos[i].getX() && tortuga.getYBase()-20>=gnomos[i].getY() && tortuga.getYAltura()+10<=gnomos[i].getY())) {
							gnomos[i]=null;
							this.gnomos[i] = new Gnomo();
							gnomosPerdidos++;
						}
					}
					
				  }
				}				
				/*entorno.dibujarCirculo(pep.getDer()+10, pep.getY(), 10, Color.green);
				entorno.dibujarCirculo(gnomos[i].getX()-20, gnomos[i].getY(), 10, Color.red);				
				entorno.dibujarCirculo(gnomos[i].getX()+20, gnomos[i].getY(), 10, Color.orange);	*/
				
			}	
			
			//DISPARO------------------------------------ Colision con tortuga
			 if(disparoPep == null && this.entorno.sePresiono('c') ) {
			     System.out.println("se presiono");
				 this.disparoPep = new DisparoPep(this.pep.getX(), (this.pep.getY()+16), 7, 20, this.pep.moverPep(entorno));
			    
			 }
			    
			    if(disparoPep != null) {
			    	
			    	this.disparoPep.moverDisparo();
			    	this.disparoPep.dibujarDisparo(entorno);
			    	if (disparoPep.getX() >= 800 || this.disparoPep.getX() <= 0) {
			    		disparoPep = null;
			    	}
			    		colisionDisparoTortuga();
			    	
			    }
			    if (pepNull) {
					this.pep=null;
				}
			    
			    if (gnomosRescatados==juegoGanado) {
					ganador=true;
				
					this.pep=null;
				}
				if (gnomosPerdidos==juegoPerdido) {
					
					this.pep=null;
				}
				
		}else {
			if (ganador) {
				entorno.cambiarFont("Arial Black", 40, Color.BLACK);
				entorno.escribirTexto("GANASTE", 285, 100);
				entorno.cambiarFont("Arial Black", 40, Color.RED);
				entorno.dibujarImagen(Gnomo, 240, 330, 0, 0.014);
				entorno.escribirTexto("Rescatados: "+ gnomosRescatados, 260, 350); //Se escribe el texto y la cantidad e gnomos rescatados 		
				entorno.cambiarFont("Arial Black", 40, Color.RED);
				entorno.dibujarImagen(Gnomo, 240, 400, 0, 0.014);
				entorno.escribirTexto("Perdidos: "+ gnomosPerdidos, 260, 420);
				
				entorno.cambiarFont("Arial Black", 30, Color.RED);
				entorno.escribirTexto("Presiona ENTER para reiniciar ", 150, 550);
				disparoPep = null;
				reiniciarJuego();
			}else {
				entorno.cambiarFont("Arial Black", 40, Color.BLACK);
				entorno.escribirTexto("PERDISTE", 285, 100);
				entorno.cambiarFont("Arial Black", 40, Color.RED);
				entorno.dibujarImagen(Gnomo, 240, 330, 0, 0.014);
				entorno.escribirTexto("Rescatados: "+ gnomosRescatados, 260, 350); //Se escribe el texto y la cantidad e gnomos rescatados 		
				entorno.cambiarFont("Arial Black", 40, Color.RED);
				entorno.dibujarImagen(Gnomo, 240, 400, 0, 0.014);
				entorno.escribirTexto("Perdidos: "+ gnomosPerdidos, 260, 420);
				
				entorno.cambiarFont("Arial Black", 30, Color.RED);
				entorno.escribirTexto("Presiona ENTER para reiniciar ", 150, 550);
				disparoPep = null;
				
				reiniciarJuego();
				
		}
		}
				
		
		

		/*entorno.dibujarCirculo(pep.getExtremoIzq(),pep.getYAltura(),10,Color.orange);
		entorno.dibujarCirculo(pep.getX(),gnomo.getYBase(),10,Color.green);
		entorno.dibujarCirculo(gnomo.getX()-10,bloq[9].getSup(),10,Color.red);*/
		

	}
	
			
    public void reiniciarJuego() {
    	if(entorno.sePresiono(entorno.TECLA_ENTER)) {
			this.iniciarJuego();
		}
    }
	
	
	public void reaparecerPep(Entorno entorno) {
		if (pep == null) {
	    this.pep = new Pep();
		boolean direccionPepp= pep.moverPep(entorno);
	    pep.dibujarPep(entorno, direccionPepp);
		}
	}
	
	  public int MinutosTranscurrido() {
		    int tiempoTranscurrido =  this.entorno.tiempo();
		    int minutos =  (tiempoTranscurrido / (1000 * 60)) % 60;
		    return minutos;
	    }
	  public int SegundosTranscurrido() {
		    int tiempoTranscurrido =  this.entorno.tiempo();
		    int segundos = (tiempoTranscurrido / 1000) % 60;
		    return segundos;
	    }
	  
	  public void gnomoNull(int i) {
			gnomos[i]=null;
	  }
	
	public boolean colisionBloque(int base,int altura,int extDer,int extIzq,Bloque bloque) {
		return 	base>= bloque.getSup() && 
				extDer> bloque.getXizq() && 
				extIzq<bloque.getXder() && 	
				base<bloque.getSup()+5;
				// altura < bloque.getInf()
				
	}
	
	
		
	public boolean pepSobreBloques(Bloque[] bloq) {
		
		boolean enBloque=false;	
		
					
		for (int i=0;i<bloq.length;i++) {
			if (colisionBloque(pep.getYBase(),pep.getYAltura(),pep.getExtremoDer(),pep.getExtremoIzq(),bloq[i])) {
				enBloque=true;
			}
		}
		
		if (pep.getYAltura()>600) {
			pep = null;
			return true;
		}else {
			if (!enBloque) {
				pep.caer();
			}else {				
				pep.reiniciarSaltos();					
			}
		}		
		return false;
	}
	
	public void lanzarGnomo(Entorno entorno, Gnomo[] gnomo, Bloque[] bloq, int numGnomo) {

		gnomo[numGnomo].dibujar(entorno);			
		
		if (gnomo[numGnomo].seCayo==false) {
			gnomo[numGnomo].moverGnomo(gnomo[numGnomo]);
			if(!colisionBloque(gnomo[numGnomo].getYBase(),gnomo[numGnomo].getYAltura(),gnomo[numGnomo].getExtremoDer(),gnomo[numGnomo].getExtremoIzq(),bloq[9])) {
				gnomo[numGnomo].seCayo=true;
				if (numGnomo<gnomo.length-1) {
					gnomo[numGnomo+1].activar();
				}else {
					gnomo[numGnomo].activar();
				}					
			}				
		}			
		else {				
				boolean enBloque = false;
				for (int i=0;i<bloq.length-1;i++) {
					if (colisionBloque(gnomo[numGnomo].getYBase(),gnomo[numGnomo].getYAltura(),gnomo[numGnomo].getExtremoDer(),gnomo[numGnomo].getExtremoIzq(),bloq[i])){
						enBloque=true;					
					}
				}
				
									
				
				if (!enBloque) {
					gnomo[numGnomo].caer();
					gnomo[numGnomo].direccion=gnomo[numGnomo].inicioRandom();
				}
				else {
					gnomo[numGnomo].moverGnomo(gnomo[numGnomo]);
				}
			}	
		
	}

	public void colisionDisparoTortuga() {
		 for(int tortuga = 0; tortuga < tortugas.length ;tortuga++) {
			 if(disparoPep != null && tortugas[tortuga] != null &&
			    tortugas[tortuga].getExtremoDer()>=disparoPep.getX() &&
			    tortugas[tortuga].getExtremoIzq() <= disparoPep.getX() && 
			    tortugas[tortuga].getYBase()>=disparoPep.getY() && 
			    tortugas[tortuga].getYAltura()<=disparoPep.getY()) {
				tortugas[tortuga] = null;
				disparoPep = null;
				int num;
				Random rand = new Random();
				do {
						num = rand.nextInt(800) + 1;
				} while (400 - Bloque.getAncho() / 2 < num && num < 400 + Bloque.getAncho() / 2);
				tortugas[tortuga] = new Tortuga(num);
				enemigosEliminados++;
			 }	
         }
		
	}
	
	private boolean colisionPepTortuga(int i) {
		    int pepX = pep.getX();
		    int pepY = pep.getY(); 
		    int pepAlto = pep.getYAltura();   // Altura del objeto 'pep'
            int pepDer= pep.getExtremoDer();
            int pepIzq= pep.getExtremoIzq();
		    int tortugaX = tortugas[i].getExtremoIzq();  // Coordenada X de la tortuga (izquierda)
		    int tortugaY = tortugas[i].getY();           // Coordenada Y de la tortuga
		    int tortugaAncho = tortugas[i].getAncho();   // Ancho de la tortuga
		    int tortugaAlto = tortugas[i].getAlto();     // Altura de la tortuga
		    boolean colisionX = pepX < (tortugaX + tortugaAncho) && (pepX + 30) > tortugaX;
		    boolean colisionY = pepY > (tortugaY-74) && pepY < (tortugaY + tortugaAlto);
     return colisionX && colisionY;
	}
	
	private void actualizarTortugas() {
		for (int i = 0; i < this.tortugas.length; i++) {
			this.tortugas[i].dibujar(this.entorno);
		}
		
		for (int i = 0; i < this.bloq.length; i++) {
			for (int j = 0; j < this.tortugas.length; j++) {
				if (TortugaSobrePiso(this.bloq[i], this.tortugas[j])) {
					this.tortugas[j].setCayendo(false);
					this.tortugas[j].mover();
					if (tortugaPisaBorde(this.bloq[i], this.tortugas[j])) {
						
							this.tortugas[j].rebotar();
						
						
					}
				}
			}
		}
		for (int i = 0; i < this.tortugas.length; i++) {
			if (this.tortugas[i].getCayendo()) {
				this.tortugas[i].caer();
				if(this.tortugas[i].getY()>610) {
					this.tortugas[i] = null;
					int num;
					Random rand = new Random();
					do {
							num = rand.nextInt(800) + 1;
					} while (400 - Bloque.getAncho() / 2 < num && num < 400 + Bloque.getAncho() / 2);
					tortugas[i] = new Tortuga(num);
					enemigosEliminados++;
				}
				
				
			}
		}
	}

	private boolean TortugaSobrePiso(Bloque piso, Tortuga tortuga) {
		return colision(piso.getY()-5, tortuga.getY()) && piso.getX() - Bloque.getAncho() / 2 < tortuga.getX()
				&& tortuga.getX() < piso.getX() + Bloque.getAncho() / 2;
	}

	private boolean tortugaPisaBorde(Bloque piso, Tortuga tortuga) {
		return colision(tortuga.getX(), piso.getX() - Bloque.getAncho() / 2)
				|| colision(tortuga.getX(), piso.getX() + Bloque.getAncho() / 2);
	}

	private boolean colision(int a, int b) {
		return Math.abs(a - b) <= 7; 
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
