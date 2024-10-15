package juego;

import entorno.Entorno;
import java.util.Random;

public class Pisos {

  private Bloques[] bloques;
  private int y;

  public Pisos(int y, int numBloques, int anchoBloque, int altoBloque) {

    this.y = y;
    bloques = new Bloques[numBloques];
    Random rand = new Random();

    for (int i = 0; i < numBloques; i++) {
      bloques[i] = new Bloques(i * anchoBloque + anchoBloque / 2, y, anchoBloque, altoBloque);
    }
  }

  public void dibujar(Entorno entorno) {

    for (Bloques bloque : bloques) {
      if (bloque != null)
        bloque.dibujar(entorno);
    }
  }

  public Bloques[] getBloques() {
    return bloques;
  }

  public int getY() {
    return y;
  }
}