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
  boolean ganador = false;

  Juego() {
    this.entorno = new Entorno(this, "TP P1 - Grippo, Sortano, Vasquez", 800, 600);
    this.iniciarJuego();
    this.entorno.iniciar();
  }

  public void tick() {
    entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
    if (this.pep != null) {
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
    this.juegoGanado = 15;
    this.juegoPerdido = 10;
    this.bloq = bl.getTotalBloques();
  }

  public int minutosTranscurridos() {
    int tiempoTranscurrido = this.entorno.tiempo();
    int minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
    return minutos;
  }

  public int segundosTranscurridos() {
    int tiempoTranscurrido = this.entorno.tiempo();
    int segundos = (tiempoTranscurrido / 1000) % 60;
    return segundos;
  }

  public boolean colisionBloque(int base, int extDer, int extIzq, Bloque bloque) {
    return base >= bloque.getSup() &&
        extDer > bloque.getXizq() &&
        extIzq < bloque.getXder() &&
        base < bloque.getSup() + 5;
  }
  
  public boolean colisionBloqueSalto(int base, int extDer, int extIzq,int altura, Bloque bloque) {
	    return base > bloque.getSup() &&
	        extDer > bloque.getXizq() &&
	        extIzq < bloque.getXder() &&
	        altura < bloque.getInf();
	  }

  public boolean tortugaSobreBloque(Tortuga tortuga) {
    boolean enBloque = false;
    for (Bloque bloque : bloq) {
      if (colisionBloque(tortuga.getYBase(), tortuga.getX(), tortuga.getX(), bloque)) {
        enBloque = true;
      }
    }
    return enBloque;
  }

  public boolean pepSobreBloques(Bloque[] bloq) {
    boolean enBloque = false;
    boolean saltoCorto = false;
    for (int i = 0; i < bloq.length; i++) {
      if (colisionBloque(pep.getYBase(), pep.getExtremoDer(), pep.getExtremoIzq(), bloq[i])) {
        enBloque = true;
      }
      if (colisionBloqueSalto(pep.getY()-70, pep.getExtremoDer(), pep.getExtremoIzq(),pep.getY()-70, bloq[i])) {
          saltoCorto = true;
        }
    }
    if (pep.getYAltura() > 600) {
      return true;
    } else {
      if (!enBloque) {
        pep.caer();
      } else {

    	  if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w')) {
              // Si salto corto está habilitado, ejecutar salto corto.
              if (saltoCorto) {
                  pep.saltoCorto();
              } else {
                  pep.saltoLargo();
              }
          }
     	      
      }
    }
    return false;
  }

  public void lanzarGnomo(Entorno entorno, Gnomo[] gnomo, Bloque[] bloq, int numGnomo) {
    gnomo[numGnomo].dibujar(entorno);
    if (gnomo[numGnomo].seCayo == false) {
      gnomo[numGnomo].mover(gnomo[numGnomo]);
      if (!colisionBloque(gnomo[numGnomo].getYBase(), gnomo[numGnomo].getExtremoDer(), gnomo[numGnomo].getExtremoIzq(),
          bloq[9])) {
        gnomo[numGnomo].seCayo = true;
        if (numGnomo < gnomo.length - 1) {
          gnomo[numGnomo + 1].activar();
        } else {
          gnomo[numGnomo].activar();
        }
      }
    } else {
      boolean enBloque = false;
      for (int i = 0; i < bloq.length - 1; i++) {
        if (colisionBloque(gnomo[numGnomo].getYBase(), gnomo[numGnomo].getExtremoDer(), gnomo[numGnomo].getExtremoIzq(),
            bloq[i])) {
          enBloque = true;
        }
      }
      if (!enBloque) {
        gnomo[numGnomo].caer();
        gnomo[numGnomo].direccion = gnomo[numGnomo].inicioRandom();
      } else {
        gnomo[numGnomo].mover(gnomo[numGnomo]);
      }
    }
  }

  public void manejarGnomos() {
    for (int i = 0; i < gnomos.length; i++) {
      if (gnomos[i] != null) {
        if (gnomos[i].esActivo) {
          lanzarGnomo(entorno, gnomos, bloq, i);
        }
        if (((pep.getExtremoDer() >= gnomos[i].getExtremoIzq() && pep.getExtremoIzq() <= gnomos[i].getExtremoDer())
            && (pep.getYBase() >= gnomos[i].getY() && pep.getYAltura() <= gnomos[i].getY()))
            && (pep.getYAltura() >= 300)) {
          gnomos[i] = null;
          gnomosRescatados++;
        } else if (gnomos[i].getY() > 600) {
          gnomos[i] = null;
          gnomosPerdidos++;
        } else {
          boolean colisionConTortuga = false;
          for (int t = 0; t < tortugas.length; t++) {
            if (tortugas[t] != null && tortugas[t].getYBase() > 300) {
              if (tortugas[t].getExtremoDer() > gnomos[i].getX() && tortugas[t].getExtremoIzq() < gnomos[i].getX()
                  && tortugas[t].getYBase() > gnomos[i].getY() && tortugas[t].getYAltura() < gnomos[i].getY()) {
                colisionConTortuga = true;
              }
            }
          }
          if (colisionConTortuga) {
            gnomos[i] = null;
            gnomosPerdidos++;
          }
        }
      } else {
        gnomos[i] = new Gnomo();
        gnomos[i].activar();
      }
    }
  }
  

  public boolean manejarTortugas(boolean pepNull) {
    ArrayList<Integer> posiciones = new ArrayList<>();
    for (int t = 0; t < tortugas.length; t++) {
      boolean enBloque = false;
      if (tortugas[t] != null) {
        if (!posiciones.contains(Integer.valueOf(tortugas[t].getPosInicial()))) {
            tortugas[t].dibujar(entorno);
            posiciones.add(Integer.valueOf(tortugas[t].getPosInicial()));
            
            if (t < tortugas.length - 1 && tortugas[t].getYBase() > 200) {
                if (tortugas[t + 1] == null) {
                  tortugas[t + 1] = new Tortuga();
                }
            }  
            
          if (tortugas[t].getYBase() > 300) {
        	
            
            if (tortugaSobreBloque(tortugas[t])) {
              enBloque = true;
              tortugas[t].sobreBloque = true;                  
            }
            if (enBloque) {
              tortugas[t].mover();
              
            } else if (tortugas[t].sobreBloque) {
              tortugas[t].rebotar();
              tortugas[t].cantRebotes++;
              if (tortugas[t].cantRebotes > 2 && tortugas[t].getYBase() > 400) {
                tortugas[t].caer();                  
              }
            } else {
              tortugas[t].caer();
            }
          } else {
            tortugas[t].caer();
          }
          if ((tortugas[t].getExtremoDer() >= pep.getExtremoIzq() && tortugas[t].getExtremoIzq() <= pep.getExtremoDer()
                  && tortugas[t].getYBase() >= pep.getY() && tortugas[t].getYAltura() <= pep.getY()) || (tortugas[t].getExtremoDer() >= pep.getExtremoDer() && tortugas[t].getExtremoIzq() <= pep.getExtremoIzq()
                  && tortugas[t].getYBase() >= pep.getY() && tortugas[t].getYAltura() <= pep.getY())) {
                 pepNull = true;
         }
          if (tortugas[t].getY() > 600) {
            posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
            tortugas[t] = null;
            tortugas[t] = new Tortuga();
          }
          if (disparoPep != null && tortugas[t] != null
              && (tortugas[t].getExtremoDer() >= disparoPep.getX()
                  && tortugas[t].getExtremoIzq() <= disparoPep.getX()
                  && tortugas[t].getYBase() >= disparoPep.getY()
                  && tortugas[t].getYAltura() <= disparoPep.getY())) {
            posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
            tortugas[t] = null;
            tortugas[t] = new Tortuga();
            disparoPep = null;
            tortugaEliminada++;
          }
        } else {
          tortugas[t] = null;
          tortugas[t] = new Tortuga();
        }
      }
    }
    return pepNull;
  }

  public void manejarDisparoPep() {
    if ((entorno.estaPresionada('c') || entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) && disparoPep == null) {
      this.disparoPep = new DisparoPep(this.pep.getX(), (this.pep.getY() + 20), 7, 20, this.pep.mover(entorno));
    }

    if (disparoPep != null) {
      this.disparoPep.moverDisparo();
      this.disparoPep.dibujarDisparo(entorno);
      if (disparoPep.getX() >= 800 || this.disparoPep.getX() <= 0) {
        disparoPep = null;
      }
    }
  }

  public void escribirStats() {
    int y = 20;
    entorno.dibujarRectangulo(400, y - 15, 810, 40, 0, new Color(0f, 0f, 0f, 0.5f));
    entorno.cambiarFont("Calibri Black", 16, Color.WHITE, 1);
    entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 10, y);
    entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 270, y);
    entorno.escribirTexto("Enemigos eliminados: " + tortugaEliminada, 500, y);
    entorno.escribirTexto(minutosTranscurridos() + ":" + segundosTranscurridos() + "s", 750, y);
  }

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
    entorno.escribirTexto("Presiona ENTER para reiniciar ", 182, 580);
    disparoPep = null;
    reiniciarJuego();
  }

  public void reiniciarJuego() {
    if (entorno.sePresiono(entorno.TECLA_ENTER)) {
      this.iniciarJuego();
      this.entorno.iniciar();
    }
  }


  @SuppressWarnings("unused")
  public static void main(String[] args) {
    Juego juego = new Juego();
  }
}