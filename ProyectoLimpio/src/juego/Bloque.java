package juego;

import java.awt.Image;
import entorno.Herramientas;

public class Bloque {
  private int x;
  private int y;
  private Image img;

  Bloque(int x, int y) {
    this.x = x;
    this.y = y;
    this.img = Herramientas.cargarImagen("imagenes/ground.png");
  }

  public int getX() {
    return x;
  }

  public int getXder() {
    return x + 65;
  }

  public int getXizq() {
    return x - 65;
  }

  public double getSup() {
    return y - 17.5;
  }

  public double getInf() {
    return y + 17.5;
  }

  public int getY() {
    return y;
  }

  public Image getImg() {
    return img;
  }
}