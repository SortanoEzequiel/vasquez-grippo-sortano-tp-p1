package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Piso {
  private int x;
  private int y;
  static private int alto = 38;
  static private int ancho = 135;
  private Image bloque = Herramientas.cargarImagen("imagenes/ground.png");

  public Piso(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void dibujar(Entorno entorno) {
    entorno.dibujarImagen(this.bloque, this.x, this.y, 0, 0.42);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y + 10;
  }

  static public int getAlto() {
    return alto;
  }

  static public int getAncho() {
    return ancho;
  }
}