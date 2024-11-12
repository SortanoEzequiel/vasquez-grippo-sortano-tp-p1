package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;
import java.util.Random;

public class Tortuga {
  private int x;
  private int y;
  static private int alto = 44;
  static private int ancho = 60;
  private int direccion;
  private boolean cayendo = true;
  private Image turtleDer = Herramientas.cargarImagen("imagenes/turtle-der.png");
  private Image turtleIzq = Herramientas.cargarImagen("imagenes/turtle-izq.png");

  public Tortuga(int x) {
    this.x = x;
    this.y = 200;
    Random rand = new Random();
    this.direccion = rand.nextBoolean() ? 1 : -1;
  }

  public void dibujar(Entorno entorno) {
    Image turtle = this.direccion > 0 ? turtleDer : turtleIzq;
    entorno.dibujarImagen(turtle, x, y, 0, 0.1);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  static public int getAlto() {
    return alto;
  }

  static public int getAncho() {
    return ancho;
  }

  public String getDireccion() {
    return this.direccion < 0 ? "izquierda" : "derecha";
  }

  public boolean getCayendo() {
    return cayendo;
  }

  public void setCayendo(boolean cae) {
    this.cayendo = cae;
  }

  public void rebotar() {
    this.direccion = this.direccion * (-1);
    // this.x = this.x + this.direccion;
  }

  public void mover() {
    this.x = this.x + this.direccion;
  }

  public void caer() {
    this.y = this.y + 1;
  }
}
