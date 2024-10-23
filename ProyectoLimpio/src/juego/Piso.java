package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Piso {
  private int x;
  private int y;
  private Image bloque = Herramientas.cargarImagen("imagenes/ground.png");

  public Piso(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void dibujar(Entorno entorno) {
    entorno.dibujarImagen(bloque, x, y, 0, 0.42);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}