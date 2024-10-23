package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Piso {
  private int x;
  private int y;
  private int alto;
  private int ancho;
  private Image bloque = Herramientas.cargarImagen("imagenes/ground.png");

  public Piso(int x, int y) {
    this.x = x;
    this.y = y;
    this.alto = 38;
    this.ancho = 135;
  }

  public void dibujar(Entorno entorno) {
    entorno.dibujarImagen(this.bloque, this.x, this.y, 0, 0.42);
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y + 3; // corrige la altura por la imagen
  }

  public int getAlto() {
    return this.alto;
  }

  public int getAncho() {
    return this.ancho;
  }
}