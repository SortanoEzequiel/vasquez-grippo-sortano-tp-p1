package juego;

import entorno.Entorno;
import java.awt.Color;

public class Bloques {

  private int x;
  private int y;
  private int ancho;
  private int alto;

  public Bloques(int x, int y, int ancho, int alto) {

    this.x = x;
    this.y = y;
    this.ancho = ancho;
    this.alto = alto;

  }

  public void dibujar(Entorno entorno) {
    entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.BLACK);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getAncho() {
    return ancho;
  }

  public int getAlto() {
    return alto;
  }
}