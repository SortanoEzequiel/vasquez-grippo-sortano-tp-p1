package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Gnomo {
	private int x;
	private int y;
	private int radio;
	private int velocidad;
	private boolean direccion;
	public boolean seCayo;
	public boolean esActivo;
	
	public Gnomo() {	
		this.x = 400;
		this.y = 118;
		this.radio = 30;
		this.velocidad = 1;
		this.direccion=inicioRandom();
		this.seCayo=false;
		this.esActivo=false;
	}

	public void caer()
	{
		this.y = this.y + this.velocidad;
	}
	
	public void activar() {
		esActivo=true;		
	}
	
	public boolean inicioRandom() {
		Random random = new Random();
		int randomInt = random.nextInt(2);
		if(randomInt == 0) {
			return false;
		}
		return true;
	}	
	
	
	public boolean getDireccion() {
		return direccion;
	}
			
	
	
	
	public void moverIzquierda()
	{
		this.x = this.x - this.velocidad;
	}
	
	public void moverDerecha()
	{
		this.x = this.x + this.velocidad;
	}
	
	
	public void moverGnomo(Gnomo gnomo){
				
		if(gnomo.direccion) {
			gnomo.moverDerecha();
		}else {
			gnomo.moverIzquierda();
		}
		
	}
	
	public void dibujar(Entorno entorno)
	{
		Image gnomo = Herramientas.cargarImagen("imagenes/gnomo.png");
		entorno.dibujarImagen(gnomo, this.x, this.y, 0, 0.015);
	}
	
	
	private boolean bordeBloque(Gnomo gnomos,Bloque bloq) {
		
		boolean bloque = gnomos.getX() <= bloq.getXizq() || gnomos.getX() >= bloq.getXder();
		return bloque ;
	}
	
	private boolean enBloque(Gnomo gnomos,Bloque bloq) {
		
		boolean bloque = (gnomos.getY()+20) >= (bloq.getSup()) && (gnomos.getY()-30) < (bloq.getSup()) && gnomos.getX()> bloq.getXizq() && gnomos.getX()<bloq.getXder();
		return bloque ;
	}
	
	public void lanzarGnomo(Entorno entorno, Gnomo[] gnomo, Bloque[] bloq, int numGnomo) {
		if (gnomo[numGnomo].esActivo) {
			gnomo[numGnomo].dibujar(entorno);
		}
		
		if (gnomo[numGnomo].seCayo==false) {
			moverGnomo(gnomo[numGnomo]);
			if(bordeBloque(gnomo[numGnomo],bloq[9])) {
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
					if (enBloque(gnomo[numGnomo],bloq[i])){
						enBloque=true;					
					}
				}
				
				if (gnomo[numGnomo].getY()>600) {
					gnomo[numGnomo].esActivo=false;
				}else if (!enBloque) {
					gnomo[numGnomo].caer();
					gnomo[numGnomo].direccion=inicioRandom();
				}
				else {
					moverGnomo(gnomo[numGnomo]);
				}
			}					
		}
	  

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getRadio() {
		return radio;
	}

	
	
	
	
}