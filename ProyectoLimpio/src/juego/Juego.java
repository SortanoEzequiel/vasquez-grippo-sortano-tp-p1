package juego;



import entorno.InterfaceJuego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
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
	
	private Bloques bl;
	
	private Bloque[] bloq;
	
	private Pep pep;
	
	private Gnomo gnomo;
	
	private Gnomo[] gnomos;	
	
	
	private Tortuga[] tortugas;
		
	private DisparoPep disparoPep;
	
	private int cantNull;
	
	private int gnomosRescatados;
				
	private int gnomosPerdidos;
	
	private int tortugaEliminada;
	
	private int juegoGanado;
	
	private int juegoPerdido;
	
	public int posX;
	
	private int tiempo;	
	
	boolean ganador=false;
	
	public int[] posicionUtilizada = new int[7];
	
	Juego()
	{
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);		

		this.fondo = Herramientas.cargarImagen("imagenes/fondogame.jpg");
		
		this.casita= Herramientas.cargarImagen("imagenes/casita.png");		
		
		this.gnomosRescatados = 0;
		this.gnomosPerdidos = 0;
		this.tortugaEliminada=0;
	
		this.tiempo = this.entorno.tiempo();
		 
		this.bl = new Bloques();
		
		this.gnomo = new Gnomo();
		
		this.pep = new Pep(); // Creo a PEP
		
		this.gnomos= new Gnomo[4];		
		gnomo.asignar(gnomos);		
		
		this.tortugas = new Tortuga[3];
		tortugas[0]=new Tortuga();
		tortugas[0].activar=true;
		
		this.cantNull=0;
		
		this.juegoGanado=15;
		
		this.juegoPerdido=100;
				
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
		
		
		if (this.pep != null){
			
			entorno.cambiarFont("Arial Black", 20, Color.BLACK);
			entorno.escribirTexto("Gnomos Rescatados: "+ gnomosRescatados, 10, 30); //Se escribe el texto y la cantidad e gnomos rescatados 		
			entorno.escribirTexto("Gnomos perdidos: "+ gnomosPerdidos, 10, 60);
			entorno.escribirTexto("Enemigos eliminados: "+ tortugaEliminada, 10, 90);
			entorno.escribirTexto(MinutosTranscurrido() + ":"+ SegundosTranscurrido()+"s", 700, 30);
			bl.dibujarBloques(entorno);
			entorno.dibujarImagen(casita, 400, 100, 0,0.11);
			
			boolean direccionPep= pep.moverPep(entorno);
			
			pep.dibujarPep(entorno, direccionPep);
						
			boolean pepNull = pepSobreBloques(bloq);

			//ESTRUCTURA DE TORTUGAS
			ArrayList<Integer> posiciones = new ArrayList<>();

			for (int t = 0; t < tortugas.length; t++) {
			    boolean enBloque = false;

			    if (tortugas[t] != null) {
			        if (!posiciones.contains(Integer.valueOf(tortugas[t].getPosInicial()))) {

			            if (tortugas[t].activar) {
			                tortugas[t].dibujar(entorno);
			                posiciones.add(Integer.valueOf(tortugas[t].getPosInicial()));
			            }

			            if (tortugas[t].getYBase() > 300) {
			                if (tortugaSobreBloque(tortugas[t])) {
			                    enBloque = true;
			                    tortugas[t].sobreBloque = true;
			                    if (t < tortugas.length - 1) {
			                        if (tortugas[t+1] == null) {
			                            tortugas[t+1] = new Tortuga();
			                            tortugas[t+1].activar = true;
			                        }
			                    }
			                }

			                if (enBloque) {
			                    tortugas[t].mover();
			                } else if (tortugas[t].sobreBloque) {
			                    tortugas[t].rebotar();
			                } else {
			                    tortugas[t].caer();
			                }
			            } else {
			                tortugas[t].caer();
			            }

			            if (disparoPep != null && tortugas[t] != null
			                    && (tortugas[t].getExtremoDer() >= disparoPep.getX()
			                    && tortugas[t].getExtremoIzq() <= disparoPep.getX()
			                    && tortugas[t].getYBase() >= disparoPep.getY()
			                    && tortugas[t].getYAltura() <= disparoPep.getY())) {
			            	posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
			                tortugas[t] = null;
			                disparoPep = null;
			                tortugaEliminada++;
			            }
			        } else {
			        	tortugas[t]=null;
			        }
			    } else {			    	
			        tortugas[t] = new Tortuga();
			        tortugas[t].activar = true;
			    }
			}
			
			
				
			
			
			//ESTRUCTURA DE GNOMOS
			for (int i=0;i<gnomos.length;i++) {
				if (i-cantNull<4) {
					
					if (gnomos[i]!=null) {
						if (gnomos[i].esActivo ) { 
							lanzarGnomo(entorno, gnomos, bloq,i); //probar poner el booleano de si esta activo en gnomo.lanzargnomo(entorno, gnomos[0], bloq, ESACTIVO)
						}
						if (((pep.getExtremoDer()>=gnomos[i].getExtremoIzq() && pep.getExtremoIzq()<=gnomos[i].getExtremoDer()) && (pep.getYBase() >= gnomos[i].getY()  && pep.getYAltura() <= gnomos[i].getY() )) && (pep.getYAltura()>=300)) {
							gnomos[i]=null;
							System.out.println("gnomo salvado");
							cantNull++;
							gnomosRescatados++;
							System.out.println(cantNull);
						}else if(gnomos[i].getY()>600) {
							gnomos[i]=null;
							System.out.println("gnomo perdido");
							cantNull++;
							gnomosPerdidos++;
						}else {
				            boolean colisionConTortuga = false;
				            for (int t = 0; t < tortugas.length; t++) {
				            	if (tortugas[t]!=null && tortugas[t].getYBase()>300) {
				            		if (tortugas[t].getExtremoDer()>gnomos[i].getX() && tortugas[t].getExtremoIzq() < gnomos[i].getX() && tortugas[t].getYBase()>gnomos[i].getY() && tortugas[t].getYAltura()<gnomos[i].getY()) {
					                    colisionConTortuga = true;
										System.out.println("gnomo perdido");					                    
					                }
					                
					                if((tortugas[t].getExtremoDer()>=pep.getX() && tortugas[t].getExtremoIzq() <= pep.getX() && tortugas[t].getYBase()>=pep.getY() && tortugas[t].getYAltura()<=pep.getY())) {
										pepNull=true;
									} 
				            	}
				            }

				            if (colisionConTortuga) {
				                gnomos[i] = null;
				                gnomosPerdidos++;
				                cantNull++;
				            }   				            
				        }
					}else {
						gnomos[i]=new Gnomo();
						gnomos[i].activar();
					}
				}					
			}					
			
			if((entorno.estaPresionada('c') || entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) && disparoPep == null) {
			     System.out.println("se presiono");
				 this.disparoPep = new DisparoPep(this.pep.getX(), (this.pep.getY()+20), 7, 20, this.pep.moverPep(entorno));
			    
			 }
			    
			if(disparoPep != null) {
			    	
			    this.disparoPep.moverDisparo();
			    this.disparoPep.dibujarDisparo(entorno);
			    if (disparoPep.getX() >= 800 || this.disparoPep.getX() <= 0) {
			    	disparoPep = null;
			    }
			    
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
				entorno.cambiarFont("Arial Black", 50, Color.BLUE);
				entorno.escribirTexto("GANASTE", 285, 250);
				entorno.cambiarFont("Arial Black", 40, Color.GREEN);
				entorno.escribirTexto("Gnomos Rescatados: "+ gnomosRescatados, 160, 350); //Se escribe el texto y la cantidad e gnomos rescatados 		
				entorno.cambiarFont("Arial Black", 40, Color.RED);
				entorno.escribirTexto("Gnomos perdidos: "+ gnomosPerdidos, 200, 400);
			}else {
				entorno.cambiarFont("Arial Black", 50, Color.RED);
				entorno.escribirTexto("PERDISTE", 285, 250);
				entorno.cambiarFont("Arial Black", 40, Color.GREEN);
				entorno.escribirTexto("Gnomos Rescatados: "+ gnomosRescatados, 160, 350); //Se escribe el texto y la cantidad e gnomos rescatados 		
				entorno.cambiarFont("Arial Black", 40, Color.BLUE);
				entorno.escribirTexto("Gnomos perdidos: "+ gnomosPerdidos, 200, 400);
			}			
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
	
	public boolean colisionBloque(int base,int extDer,int extIzq,Bloque bloque) {
		return 	base>= bloque.getSup() && 
				extDer> bloque.getXizq() && 
				extIzq<bloque.getXder() && 	
				base<bloque.getSup()+5;
				// altura < bloque.getInf()
				
	}
	
	public boolean tortugaSobreBloque(Tortuga tortuga) {
		boolean enBloque=false;	
		for (Bloque bloque: bloq) {
			if(colisionBloque(tortuga.getYBase(),tortuga.getX(),tortuga.getX(),bloque)) {
				enBloque=true;
			}
		}
		return enBloque;
	}
	
	
		
	public boolean pepSobreBloques(Bloque[] bloq) {
		
		boolean enBloque=false;	
		
					
		for (int i=0;i<bloq.length;i++) {
			if (colisionBloque(pep.getYBase(),pep.getExtremoDer(),pep.getExtremoIzq(),bloq[i])) {
				enBloque=true;
			}
		}
		
		if (pep.getYAltura()>600) {
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
			if(!colisionBloque(gnomo[numGnomo].getYBase(),gnomo[numGnomo].getExtremoDer(),gnomo[numGnomo].getExtremoIzq(),bloq[9])) {
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
					if (colisionBloque(gnomo[numGnomo].getYBase(),gnomo[numGnomo].getExtremoDer(),gnomo[numGnomo].getExtremoIzq(),bloq[i])){
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
	

	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}

