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
	
	private Image casita;
	
	private Bloques bl;
	
	private Bloque[] bloq;
	
	private Pep pep;
	
	private Gnomo gnomo;
	
	private Gnomo[] gnomos;	
	
	
	private Tortuga[] tortugas;
	
	private int cantNull;
				
	
	
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
		
		this.tortugas = new Tortuga[2];
		
		tortugas[0] = new Tortuga(200);
		tortugas[1] = new Tortuga(600);
		
		this.cantNull=0;
				
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
		
		
		
		if (this.pep != null){
			
			for (int i=0; i<tortugas.length;i++) {
				tortugas[i].dibujar(entorno);
				
				if (tortugas[i].getY()<=bloq[6].getSup())
					tortugas[i].caer();
				else {
					if (tortugas[i].getX()>=bloq[4].getXizq() && tortugas[i].getX()<=bloq[4].getXder() || tortugas[i].getX()>=bloq[6].getXizq() && tortugas[i].getX()<=bloq[6].getXder() ) {
						tortugas[i].mover();
					}
						else {
							tortugas[i].rebotar();
							tortugas[i].mover();
						}
				}
			}
				
			
			
			
			
			
			boolean direccionPep= pep.moverPep(entorno);
			
			pep.dibujarPep(entorno, direccionPep);
						
			boolean pepNull = pepSobreBloques(bloq);
			
			for (int i=0;i<gnomos.length;i++) {
				if (i-cantNull<4)
				if (gnomos[i]!=null) {
					if (gnomos[i].esActivo ) { 
						lanzarGnomo(entorno, gnomos, bloq,i); //probar poner el booleano de si esta activo en gnomo.lanzargnomo(entorno, gnomos[0], bloq, ESACTIVO)
					}
					if (((pep.getExtremoDer()>=gnomos[i].getExtremoIzq() && pep.getExtremoIzq()<=gnomos[i].getExtremoDer()) && (pep.getYBase() >= gnomos[i].getY()  && pep.getYAltura() <= gnomos[i].getY() )) && (pep.getYAltura()>=300) || gnomos[i].getY()>600 || (tortugas[1].getExtremoDer()>gnomos[i].getX() && tortugas[1].getExtremoIzq() < gnomos[i].getX() && tortugas[1].getYBase()>gnomos[i].getY() && tortugas[1].getYAltura()<gnomos[i].getY())) {
						gnomos[i]=null;
						cantNull++;
					}
				}				
				/*entorno.dibujarCirculo(pep.getDer()+10, pep.getY(), 10, Color.green);
				entorno.dibujarCirculo(gnomos[i].getX()-20, gnomos[i].getY(), 10, Color.red);				
				entorno.dibujarCirculo(gnomos[i].getX()+20, gnomos[i].getY(), 10, Color.orange);	*/
				
			}	
			
			
			
			if (pepNull) {
				this.pep=null;
				System.out.println(pepNull);
			}								
		}
		System.out.println(cantNull);

		/*entorno.dibujarCirculo(pep.getExtremoIzq(),pep.getYAltura(),10,Color.orange);
		entorno.dibujarCirculo(pep.getX(),gnomo.getYBase(),10,Color.green);
		entorno.dibujarCirculo(gnomo.getX()-10,bloq[9].getSup(),10,Color.red);*/
		

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

	

	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
