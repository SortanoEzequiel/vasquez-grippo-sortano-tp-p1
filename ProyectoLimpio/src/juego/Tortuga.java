package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;
import java.util.Random;

public class Tortuga {
	private int x;
	private int y;
	public int posInicial;
	static private int alto = 44;
	static private int ancho = 60;
	private int velocidad;
	private boolean cayendo = true;
	private Image turtleDer = Herramientas.cargarImagen("imagenes/turtle-der.png");
	private Image turtleIzq = Herramientas.cargarImagen("imagenes/turtle-izq.png");
	public boolean sobreBloque = false;
	public boolean activar = false;
	public int cantRebotes = 0;

	public Tortuga() {
		posInicial = randomX();
		this.x = posInicial;
		this.y = 20;
		Random rand = new Random();
		boolean bool = rand.nextBoolean();
		int num = rand.nextInt(3) + 2;
		if (bool) {
			this.velocidad = num;
		} else {
			this.velocidad = -1 * num;
		}
	}

	public int getPosInicial() {
		return posInicial;
	}

	public int randomX() {
		int[] posicionX = new int[7];
		int posInicial = 100;
		Random r = new Random();
		for (int i = 0; i < posicionX.length; i++) {
			posicionX[i] = posInicial;
			posInicial += 100;
		}
		return posicionX[r.nextInt(posicionX.length)];
	}

	public void dibujar(Entorno entorno) {
		Image turtle = this.velocidad > 0 ? turtleDer : turtleIzq;
		entorno.dibujarImagen(turtle, x, y, 0, 0.1);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y + (alto / 2);
	}

	static public int getAlto() {
		return alto;
	}

	static public int getAncho() {
		return ancho;
	}

	public boolean getCayendo() {
		return cayendo;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setCayendo(boolean cae) {
		this.cayendo = cae;
	}

	public void rebotar() {
		this.velocidad = this.velocidad * (-1);
		this.x = this.x + this.velocidad;
	}

	public void mover() {
		this.x = this.x + this.velocidad;
	}

	public void caer() {
		this.y = this.y + 4;
	}

	public int getYBase() {
		return y + 12;
	}

	public int getYAltura() {
		return y - 20;
	}

	public int getExtremoDer() {
		return x + 25;
	}

	public int getExtremoIzq() {
		return x - 25;
	}
}
