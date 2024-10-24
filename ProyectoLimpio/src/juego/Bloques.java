package juego;

import entorno.Entorno;

public class Bloques {
	private Bloque[] totalBloques;
	private int xPos;
	private int yPos;
	private int distPiso;
	private int distBloque;
	private int fila;
	private int xPosInicial;
	
	
	Bloques(){
		this.totalBloques= new Bloque[10];
		
		this.xPosInicial= 100;
		this.xPos= xPosInicial;
		this.yPos= 525;
		this.distPiso=125;
		this.distBloque=200;
		this.fila=1;
		
		for (int i=0;i<totalBloques.length;i++) {
			
			
			if (i<4) {
				totalBloques[i]= new Bloque( this.xPos, this.yPos);
				this.xPos+=distBloque;
				if (this.fila==1) {
					this.fila++;
				}
				if (i==3) {
					this.xPos=xPosInicial*fila;
				}
			}
			else if (i<7) {
				if (this.fila==2) {
					this.yPos=this.yPos-distPiso;
					this.fila++;
				}
				
				totalBloques[i]= new Bloque(this.xPos, this.yPos);
				
				this.xPos+=distBloque;
				
				if (i==6) {
					this.xPos=xPosInicial*fila;
				}
			}
			else if (i<9) {
				if (this.fila==3) {
					this.yPos=this.yPos-distPiso;
					this.fila++;
				}
				
				totalBloques[i]= new Bloque(this.xPos, this.yPos);
				this.xPos+=distBloque;
				
				if (i==8) {
					this.xPos=xPosInicial*fila;
				}
			}else {
				if (this.fila==4) {
					this.yPos=this.yPos-distPiso;
					this.fila++;
				}
				totalBloques[i]= new Bloque(this.xPos, this.yPos);
			}			
		}
	}
	
	
	void dibujarBloques(Entorno entorno) {
		for (int i=0;i<totalBloques.length;i++) {
			entorno.dibujarImagen(totalBloques[i].getImg(),totalBloques[i].getX(),totalBloques[i].getY(), 0);			
		}		
	}
	
	Bloque[] getTotalBloques() {
	    return totalBloques;
	}
}
