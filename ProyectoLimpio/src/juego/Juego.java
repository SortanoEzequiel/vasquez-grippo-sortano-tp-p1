package juego;

import entorno.InterfaceJuego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import entorno.Herramientas;
import entorno.Entorno;

public class Juego extends InterfaceJuego {
  private Entorno entorno;
  private Image fondo;
  private Image casita;
  private Bloques bl;
  private Bloque[] bloq;
  private Pep pep;
  private Gnomo gnomo;
  private Gnomo[] gnomos;
  private Tortuga[] tortugas;
  private DisparoPep disparoPep;
  private int gnomosRescatados;
  private int gnomosPerdidos;
  private int tortugaEliminada;
  private int juegoGanado;
  private int juegoPerdido;
  public int posX;
  boolean ganador = false;
  public int[] posicionUtilizada = new int[7];

  // Constructor del juego. Inicializa el entorno y comienza el juego.
  Juego() {
    this.entorno = new Entorno(this, "TP P1 - Grippo, Sortano, Vasquez", 800, 600);
    this.iniciarJuego();
    this.entorno.iniciar();
  }

  // Método principal del juego que se llama en cada frame.
  public void tick() {
    entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
    if (this.pep != null) {
      entorno.dibujarRectangulo(400,300,800,600,0, new Color(1f,0f,0f,gnomosPerdidos/10f));
      bl.dibujarBloques(entorno);
      entorno.dibujarImagen(casita, 400, 85, 0, 0.25);

      // actualizar pep
      boolean direccionPep = pep.mover(entorno);
      pep.dibujar(entorno, direccionPep);
      boolean pepNull = pepSobreBloques(bloq);
      manejarDisparoPep();

      pepNull = manejarTortugas(pepNull);
      manejarGnomos();

      if (pepNull) {
        this.pep = null;
      }
      if (gnomosRescatados == juegoGanado) {
        ganador = true;
        this.pep = null;
      }
      if (gnomosPerdidos == juegoPerdido) {
        this.pep = null;
      }

      escribirStats();
    } else {
      mensajeFinal(ganador);
    }
  }

  // Inicializa los elementos del juego.
  public void iniciarJuego() {
    this.fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");
    this.casita = Herramientas.cargarImagen("imagenes/house.png");
    this.gnomosRescatados = 0;
    this.gnomosPerdidos = 0;
    this.tortugaEliminada = 0;
    this.bl = new Bloques();
    this.gnomo = new Gnomo();
    this.pep = new Pep();
    this.gnomos = new Gnomo[4];
    gnomo.asignar(gnomos);
    this.tortugas = new Tortuga[3];
    tortugas[0] = new Tortuga();
    tortugas[0].activar = true;
    this.juegoGanado = 15;
    this.juegoPerdido = 10;
    this.bloq = bl.getTotalBloques();
  }

  // Calcula los minutos transcurridos desde el inicio del juego.
  public int minutosTranscurridos() {
    int tiempoTranscurrido = this.entorno.tiempo();
    int minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
    return minutos;
  }

  // Calcula los segundos transcurridos desde el inicio del juego.
  public int segundosTranscurridos() {
    int tiempoTranscurrido = this.entorno.tiempo();
    int segundos = (tiempoTranscurrido / 1000) % 60;
    return segundos;
  }

  // Elimina un gnomo del arreglo de gnomos.
  public void gnomoNull(int i) {
    gnomos[i] = null;
  }

  // Verifica si hay colisión entre un objeto y un bloque.
  public boolean colisionBloque(int base, int extDer, int extIzq, Bloque bloque) {
    return base >= bloque.getSup() &&
        extDer > bloque.getXizq() &&
        extIzq < bloque.getXder() &&
        base < bloque.getSup() + 5;
  }

  // Verifica si hay colisión entre un objeto y un bloque durante un salto.
  public boolean colisionBloqueSalto(int base, int extDer, int extIzq, int altura, Bloque bloque) {
    return base > bloque.getSup() &&
        extDer > bloque.getXizq() &&
        extIzq < bloque.getXder() &&
        altura < bloque.getInf();
  }

  // Verifica si una tortuga está sobre un bloque.
  public boolean tortugaSobreBloque(Tortuga tortuga) {
    boolean enBloque = false;
    for (Bloque bloque : bloq) {
      if (colisionBloque(tortuga.getYBase(), tortuga.getX(), tortuga.getX(), bloque)) {
        enBloque = true;
      }
    }
    return enBloque;
  }

// Verifica si Pep está sobre algún bloque.
public boolean pepSobreBloques(Bloque[] bloq) {
  boolean enBloque = false; // Indica si Pep está sobre un bloque.
  boolean saltoCorto = false; // Indica si Pep debe realizar un salto corto.
  // Recorre todos los bloques para verificar colisiones.
  for (int i = 0; i < bloq.length; i++) {
    // Verifica si Pep está sobre el bloque actual.
    if (colisionBloque(pep.getYBase(), pep.getExtremoDer(), pep.getExtremoIzq(), bloq[i])) {
      enBloque = true;
    }
    // Verifica si Pep puede realizar un salto largo sobre el bloque actual.
    if (colisionBloqueSalto(pep.getY() - 70, pep.getExtremoDer(), pep.getExtremoIzq(), pep.getY() - 70, bloq[i])) {
      saltoCorto = true;
    }
  }
  // Si Pep está por debajo de la pantalla, se considera que ha caído.
  if (pep.getYAltura() > 600) {
    return true;
  } else {
    // Si Pep no está sobre ningún bloque, debe caer.
    if (!enBloque) {
      pep.caer();
    } else {
      // Si Pep está sobre un bloque y se presiona la tecla de salto, realiza un salto.
      if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w')) {
        pep.saltar(saltoCorto);
      }
    }
  }
  return false;
}

// Genera un gnomo y maneja su movimiento y colisiones.
public void lanzarGnomo(Entorno entorno, Gnomo[] gnomo, Bloque[] bloq, int numGnomo) {
  gnomo[numGnomo].dibujar(entorno);
  if (gnomo[numGnomo].seCayo == false) {  // Si el gnomo no se ha caído.
    gnomo[numGnomo].mover(gnomo[numGnomo]);
    // Verifica si el gnomo no está colisionando con el bloque 9.
    if (!colisionBloque(gnomo[numGnomo].getYBase(), gnomo[numGnomo].getExtremoDer(), gnomo[numGnomo].getExtremoIzq(), bloq[9])) {
      // Marca que el gnomo se ha caído.
      gnomo[numGnomo].seCayo = true;
      // Activa el siguiente gnomo en la lista si existe.
      if (numGnomo < gnomo.length - 1) {
        gnomo[numGnomo + 1].activar();
      } else {
        gnomo[numGnomo].activar();
      }
    }
  } else {
    boolean enBloque = false;
    // Recorre todos los bloques para verificar colisiones con gnomos.
    for (int i = 0; i < bloq.length - 1; i++) {
      // Verifica si el gnomo está colisionando con el bloque actual.
      if (colisionBloque(gnomo[numGnomo].getYBase(), gnomo[numGnomo].getExtremoDer(), gnomo[numGnomo].getExtremoIzq(), bloq[i])) {
        enBloque = true;
      }
    }
    // Si el gnomo no está sobre ningún bloque, debe caer.
    if (!enBloque) {
      gnomo[numGnomo].caer();
      // Asigna una nueva dirección aleatoria al gnomo.
      gnomo[numGnomo].direccion = gnomo[numGnomo].inicioRandom();
    } else {
      // Si el gnomo está sobre un bloque, lo mueve.
      gnomo[numGnomo].mover(gnomo[numGnomo]);
    }
  }
}

// Maneja el movimiento y las colisiones de los gnomos.
public void manejarGnomos() {
  for (int i = 0; i < gnomos.length; i++) {
    if (gnomos[i] != null) {
      // Si el gnomo está activo, se maneja su movimiento y colisiones.
      if (gnomos[i].esActivo) {
        lanzarGnomo(entorno, gnomos, bloq, i);
      }
      // Verifica si Pep rescata al gnomo.
      if (((pep.getExtremoDer() >= gnomos[i].getExtremoIzq() && pep.getExtremoIzq() <= gnomos[i].getExtremoDer())
          && (pep.getYBase() >= gnomos[i].getY() && pep.getYAltura() <= gnomos[i].getY()))
          && (pep.getYAltura() >= 300)) {
        // Si Pep rescata al gnomo, se incrementa el contador de gnomos rescatados.
        gnomos[i] = null;
        gnomosRescatados++;
      } else if (gnomos[i].getY() > 600) {
        // Si el gnomo cae fuera de la pantalla, se incrementa el contador de gnomos perdidos.
        gnomos[i] = null;
        gnomosPerdidos++;
      } else {
        boolean colisionConTortuga = false;
        // Verifica colisiones con tortugas.
        for (int t = 0; t < tortugas.length; t++) {
          if (tortugas[t] != null && tortugas[t].getYBase() > 300) {
            if (tortugas[t].getExtremoDer() > gnomos[i].getX() && tortugas[t].getExtremoIzq() < gnomos[i].getX()
                && tortugas[t].getYBase() > gnomos[i].getY() && tortugas[t].getYAltura() < gnomos[i].getY()) {
              colisionConTortuga = true;
            }
          }
        }
        // Si hay colisión con una tortuga, se incrementa el contador de gnomos perdidos.
        if (colisionConTortuga) {
          gnomos[i] = null;
          gnomosPerdidos++;
        }
      }
    } else {
      // Si el gnomo es nulo, se crea un nuevo gnomo y se activa.
      gnomos[i] = new Gnomo();
      gnomos[i].activar();
    }
  }
}

// Maneja el movimiento y las colisiones de las tortugas.
public boolean manejarTortugas(boolean pepNull) {
  ArrayList<Integer> posiciones = new ArrayList<>();
  for (int t = 0; t < tortugas.length; t++) {
    boolean enBloque = false;
    if (tortugas[t] != null) {
      // Verifica si la posición inicial de la tortuga no está en la lista de posiciones.
      if (!posiciones.contains(Integer.valueOf(tortugas[t].getPosInicial()))) {
        if (tortugas[t].activar) {
          tortugas[t].dibujar(entorno);
          posiciones.add(Integer.valueOf(tortugas[t].getPosInicial()));
        }
        // Verifica si la tortuga está por encima de una cierta altura.
        if (tortugas[t].getYBase() > 300) {
          // Verifica si hay colisión entre la tortuga y Pep.
          if ((tortugas[t].getExtremoDer() >= pep.getX() && tortugas[t].getExtremoIzq() <= pep.getX()
              && tortugas[t].getYBase() >= pep.getY() && tortugas[t].getYAltura() <= pep.getY())) {
            pepNull = true;
          }
          // Verifica si la tortuga está sobre un bloque.
          if (tortugaSobreBloque(tortugas[t])) {
            enBloque = true;
            tortugas[t].yaTocoBloque = true;
            // Activa la siguiente tortuga si existe.
            if (t < tortugas.length - 1 && tortugas[t + 1] == null) {
              tortugas[t + 1] = new Tortuga();
              tortugas[t + 1].activar = true;
            }
          }
          // Mueve la tortuga si está sobre un bloque.
          if (enBloque) {
            tortugas[t].mover();
          } else if (tortugas[t].yaTocoBloque) {
            // Si la tortuga ya tocó un bloque, verifica si debe rebotar o caer.
            if (tortugas[t].cantRebotes > 2) {
              tortugas[t].caer();
            } else {
              tortugas[t].rebotar();
              tortugas[t].cantRebotes++;
            }
          } else {
            tortugas[t].caer();
          }
        } else {
          tortugas[t].caer();
        }
        // Si la tortuga cae fuera de la pantalla, se reinicia.
        if (tortugas[t].getY() > 600) {
          posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
          tortugas[t] = null;
          tortugas[t] = new Tortuga();
          tortugas[t].activar = true;
        }
        // Verifica si hay colisión entre la tortuga y el disparo de Pep.
        if (disparoPep != null && tortugas[t] != null
            && (tortugas[t].getExtremoDer() >= disparoPep.getX()
                && tortugas[t].getExtremoIzq() <= disparoPep.getX()
                && tortugas[t].getYBase() >= disparoPep.getY()
                && tortugas[t].getYAltura() <= disparoPep.getY())) {
          posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
          tortugas[t] = null;
          tortugas[t] = new Tortuga();
          tortugas[t].activar = true;
          disparoPep = null;
          tortugaEliminada++;
        }
      } else {
        tortugas[t] = null;
        tortugas[t] = new Tortuga();
        tortugas[t].activar = true;
      }
    }
  }
  return pepNull;
}

// Maneja el disparo de Pep.
public void manejarDisparoPep() {
  if ((entorno.estaPresionada('c') || entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) && disparoPep == null) {
    // Crea un nuevo disparo de Pep.
    this.disparoPep = new DisparoPep(this.pep.getX(), (this.pep.getY() + 20), 7, 20, this.pep.mover(entorno));
  }
  // Si hay un disparo activo.
  if (disparoPep != null) {
    // Mueve y dibuja el disparo.
    this.disparoPep.moverDisparo();
    this.disparoPep.dibujarDisparo(entorno);
    // Verifica si el disparo sale de los límites de la pantalla para eliminarlo.
    if (disparoPep.getX() >= 800 || this.disparoPep.getX() <= 0) {
      disparoPep = null;
    }
  }
}

  // Escribe las estadísticas del juego en la pantalla.
  public void escribirStats() {
    int y = 20;
    entorno.dibujarRectangulo(400, y - 15, 810, 40, 0, new Color(0f, 0f, 0f, 0.5f));
    entorno.cambiarFont("Calibri Black", 16, Color.WHITE, 1);
    entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 10, y);
    entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 270, y);
    entorno.escribirTexto("Enemigos eliminados: " + tortugaEliminada, 500, y);
    entorno.escribirTexto(minutosTranscurridos() + ":" + segundosTranscurridos() + "s", 750, y);
  }

  // Muestra un mensaje final de victoria o derrota.
  public void mensajeFinal(boolean g) {
    if (g) {
      entorno.dibujarRectangulo(400, 232, 300, 50, 0, new Color(30, 200, 30));
      entorno.cambiarFont("Calibri Black", 50, Color.BLACK, 1);
      entorno.escribirTexto("GANASTE", 280, 250);
    } else {
      entorno.dibujarRectangulo(400, 232, 300, 50, 0, new Color(200, 30, 30));
      entorno.cambiarFont("Calibri Black", 50, Color.BLACK, 1);
      entorno.escribirTexto("PERDISTE", 275, 250);
    }
    entorno.cambiarFont("Calibri Black", 40, new Color(30, 200, 30), 1);
    entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 175, 350);
    entorno.cambiarFont("Calibri Black", 40, new Color(200, 30, 30), 1);
    entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 205, 400);
    entorno.cambiarFont("Calibri Black", 30, Color.WHITE, 1);
    if (entorno.tiempo() % 1000 < 500)
      entorno.escribirTexto("Presiona ENTER para reiniciar ", 182, 580);
    disparoPep = null;
    reiniciarJuego();
  }

  // Reinicia el juego si se presiona la tecla ENTER.
  public void reiniciarJuego() {
    if (entorno.sePresiono(entorno.TECLA_ENTER)) {
      this.iniciarJuego();
      this.entorno.iniciar();
    }
  }

  // Método principal que inicia el juego.
  @SuppressWarnings("unused")
  public static void main(String[] args) {
    Juego juego = new Juego();
  }
}