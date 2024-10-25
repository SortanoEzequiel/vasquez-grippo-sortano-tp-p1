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
	
	public Gnomo() {	
		this.x = 400;
		this.y = 118;
		this.radio = 30;
		this.velocidad = 1;
		this.direccion=inicioRandom();
	}

	public void caer()
	{
		this.y = this.y + this.velocidad;
	}
	
	public boolean inicioRandom() {
		Random random = new Random();
		int randomInt = random.nextInt(2);
	    System.out.println(randomInt);
		if(randomInt == 0) {
			moverIzquierda();
			System.out.println("pasa");
			return false;
		}
		moverDerecha();
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
	public void moverGnomo(){
	    Random random = new Random();
	    int randomInt = random.nextInt(2);
	    System.out.println(randomInt);
		if(randomInt == 0) {
			this.moverIzquierda();
		}
		this.moverDerecha();
	}
	
	public void dibujar(Entorno entorno)
	{
		Image gnomo = Herramientas.cargarImagen("imagenes/pep-der.png");
		entorno.dibujarImagen(gnomo, this.x, this.y, 0, 0.1);
	}
	
	private boolean colisionBloqueGnomo(Gnomo gnomos, Bloque bloq)
	{
		boolean colisionX = bloq.getXizq()  < gnomos.getX() &&
				            bloq.getXder()  > gnomos.getX();
							
		boolean colisionY = gnomos.getY() + gnomos.getRadio() == bloq.getY();
							
		return colisionX && colisionY;
	}
	
private boolean bordeBloque(Gnomo gnomos,Bloque bloq) {
		
		boolean bloque = gnomos.getX() <= bloq.getXizq() || gnomos.getX() >= bloq.getXder();
		return bloque ;
	}
	
	public void lanzarGnomo(Entorno entorno, Gnomo[] gnomos, Bloque[] bloq) {
		for(int i = 0 ; i < gnomos.length; i++) {
			gnomos[i].dibujar(entorno);
			if (gnomos[i].getDireccion()) {
				gnomos[i].moverDerecha();
				if(bordeBloque(gnomos[i],bloq[9])) gnomos[i].caer();
			}else {
				gnomos[i].moverIzquierda();
				if(bordeBloque(gnomos[i],bloq[9])) gnomos[i].caer();
				System.out.println(gnomos[i].getX());
			}
			
			
			
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