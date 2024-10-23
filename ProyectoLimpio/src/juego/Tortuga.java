package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;
import java.util.Random;

public class Tortuga {
  private int x;
  private int y;
  private int alto;
  private int ancho;
  private int velocidad;
  private boolean cayendo = true;
  private Image turtleDer = Herramientas.cargarImagen("imagenes/turtle-der.png");
  private Image turtleIzq = Herramientas.cargarImagen("imagenes/turtle-izq.png");

  public Tortuga() {
    this.y = 20;
    this.alto = 44;
    this.ancho = 60;
    Random rand = new Random();
    Piso piso = new Piso(400, 0);
    int num = rand.nextInt(800) + 1;
    if (400 - piso.getAncho() / 2 < num && num <= 400) {
      this.x = num + piso.getAncho() + this.ancho / 2;
    } else if (400 < num && num < 400 + piso.getAncho() / 2) {
      this.x = num - piso.getAncho() - this.ancho / 2;
    } else {
      this.x = num;
    }
    piso = null;
    boolean bool = rand.nextBoolean();
    num = rand.nextInt(3) + 1;
    if (bool) {
      this.velocidad = num;
    } else {
      this.velocidad = -1 * num;
    }
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

  public int getAlto() {
    return alto;
  }

  public int getAncho() {
    return ancho;
  }

  public boolean getCayendo() {
    return cayendo;
  }

  public void setCayendo(boolean cae) {
    this.cayendo = cae;
  }

  public void rebotar() {
    this.velocidad = this.velocidad * (-1);
  }

  public void mover() {
    this.x = this.x + this.velocidad;
  }

  public void caer() {
    this.y = this.y + 2;
  }
}
