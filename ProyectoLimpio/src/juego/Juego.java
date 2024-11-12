package juego;

import entorno.InterfaceJuego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import entorno.Herramientas;
import entorno.Entorno;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	// ...
	private Image fondo;

	private Image casita;

	private Bloques bl;

	private Bloque[] bloq;

	private Pep pep;

	private Gnomo gnomo;

	private Gnomo[] gnomos;

	private Tortuga[] tortugas;

	private DisparoPep disparoPep;

	private int cantNull;

	private int gnomosRescatados;

	private int gnomosPerdidos;

	private int tortugaEliminada;

	private int juegoGanado;

	private int juegoPerdido;

	public int posX;

	private int tiempo;

	boolean ganador = false;

	public int[] posicionUtilizada = new int[7];

	Juego() {
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);

		this.fondo = Herramientas.cargarImagen("imagenes/skyhill.jpg");

		this.casita = Herramientas.cargarImagen("imagenes/house.png");

		this.gnomosRescatados = 0;
		this.gnomosPerdidos = 0;
		this.tortugaEliminada = 0;

		this.tiempo = this.entorno.tiempo();

		this.bl = new Bloques();

		this.gnomo = new Gnomo();

		this.pep = new Pep(); // Creo a PEP

		this.gnomos = new Gnomo[4];
		gnomo.asignar(gnomos);

		this.tortugas = new Tortuga[3];
		tortugas[0] = new Tortuga();
		tortugas[0].activar = true;

		this.cantNull = 0;

		this.juegoGanado = 15;

		this.juegoPerdido = 10;

		this.bloq = bl.getTotalBloques();

		this.entorno.iniciar();// Inicia el juego!

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y
	 * por lo tanto es el método más importante de esta clase. Aquí se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);
		if (this.pep != null) {
			bl.dibujarBloques(entorno);
			entorno.dibujarImagen(casita, 400, 85, 0, 0.25);
			boolean direccionPep = pep.moverPep(entorno);
			pep.dibujarPep(entorno, direccionPep);
			boolean pepNull = pepSobreBloques(bloq);
			// ESTRUCTURA DE TORTUGAS
			ArrayList<Integer> posiciones = new ArrayList<>();
			for (int t = 0; t < tortugas.length; t++) {
				boolean enBloque = false;

				if (tortugas[t] != null) {
					if (!posiciones.contains(Integer.valueOf(tortugas[t].getPosInicial()))) {
						if (tortugas[t].activar) {
							tortugas[t].dibujar(entorno);
							posiciones.add(Integer.valueOf(tortugas[t].getPosInicial()));
						}

						if (tortugas[t].getYBase() > 300) {
							if (tortugaSobreBloque(tortugas[t])) {
								enBloque = true;
								tortugas[t].sobreBloque = true;
								if (t < tortugas.length - 1) {
									if (tortugas[t + 1] == null) {
										tortugas[t + 1] = new Tortuga();
										tortugas[t + 1].activar = true;
									}
								}
							}

							if (enBloque) {
								tortugas[t].mover();
							} else if (tortugas[t].sobreBloque) {
								tortugas[t].rebotar();
								tortugas[t].cantRebotes++;
								if (tortugas[t].cantRebotes > 2) {
									tortugas[t].caer();
								}
							} else {
								tortugas[t].caer();
							}
						} else {
							tortugas[t].caer();
						}

						if (tortugas[t].getY() > 600) {
							posiciones.remove(Integer.valueOf(tortugas[t].getPosInicial()));
							tortugas[t] = null;
							tortugas[t] = new Tortuga();
							tortugas[t].activar = true;
						}

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

			// ESTRUCTURA DE GNOMOS
			for (int i = 0; i < gnomos.length; i++) {

				if (gnomos[i] != null) {
					if (gnomos[i].esActivo) {
						lanzarGnomo(entorno, gnomos, bloq, i); // probar poner el booleano de si esta activo en
																										// gnomo.lanzargnomo(entorno, gnomos[0], bloq, ESACTIVO)
					}
					if (((pep.getExtremoDer() >= gnomos[i].getExtremoIzq() && pep.getExtremoIzq() <= gnomos[i].getExtremoDer())
							&& (pep.getYBase() >= gnomos[i].getY() && pep.getYAltura() <= gnomos[i].getY()))
							&& (pep.getYAltura() >= 300)) {
						gnomos[i] = null;
						cantNull++;
						gnomosRescatados++;
					} else if (gnomos[i].getY() > 600) {
						gnomos[i] = null;
						cantNull++;
						gnomosPerdidos++;
					} else {
						boolean colisionConTortuga = false;
						for (int t = 0; t < tortugas.length; t++) {
							if (tortugas[t] != null && tortugas[t].getYBase() > 300) {
								if (tortugas[t].getExtremoDer() > gnomos[i].getX() && tortugas[t].getExtremoIzq() < gnomos[i].getX()
										&& tortugas[t].getYBase() > gnomos[i].getY() && tortugas[t].getYAltura() < gnomos[i].getY()) {
									colisionConTortuga = true;
								}

								if ((tortugas[t].getExtremoDer() >= pep.getX() && tortugas[t].getExtremoIzq() <= pep.getX()
										&& tortugas[t].getYBase() >= pep.getY() && tortugas[t].getYAltura() <= pep.getY())) {
									pepNull = true;
								}
							}
						}

						if (colisionConTortuga) {
							gnomos[i] = null;
							gnomosPerdidos++;
							cantNull++;
						}
					}
				} else {
					gnomos[i] = new Gnomo();
					gnomos[i].activar();
				}

			}

			if ((entorno.estaPresionada('c') || entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) && disparoPep == null) {
				this.disparoPep = new DisparoPep(this.pep.getX(), (this.pep.getY() + 20), 7, 20, this.pep.moverPep(entorno));
			}

			if (disparoPep != null) {

				this.disparoPep.moverDisparo();
				this.disparoPep.dibujarDisparo(entorno);
				if (disparoPep.getX() >= 800 || this.disparoPep.getX() <= 0) {
					disparoPep = null;
				}

			}

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

			entorno.cambiarFont("Calibri Black", 16, Color.WHITE, 1);
			entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 10, 30);
			entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 270, 30);
			entorno.escribirTexto("Enemigos eliminados: " + tortugaEliminada, 500, 30);
			entorno.escribirTexto(MinutosTranscurrido() + ":" + SegundosTranscurrido() + "s", 750, 30);
		} else {
			if (ganador) {
				entorno.dibujarRectangulo(400, 232, 300, 50, 0, Color.GREEN);
				entorno.cambiarFont("Calibri Black", 50, Color.BLACK, 1);
				entorno.escribirTexto("GANASTE", 280, 250);
				entorno.cambiarFont("Calibri Black", 40, Color.GREEN, 1);
				entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 160, 350);
				entorno.cambiarFont("Calibri Black", 40, Color.RED, 1);
				entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 200, 400);
			} else {
				entorno.dibujarRectangulo(400, 232, 300, 50, 0, Color.RED);
				entorno.cambiarFont("Calibri Black", 50, Color.BLACK, 1);
				entorno.escribirTexto("PERDISTE", 275, 250);
				entorno.cambiarFont("Calibri Black", 40, Color.GREEN, 1);
				entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 160, 350);
				entorno.cambiarFont("Calibri Black", 40, Color.RED, 1);
				entorno.escribirTexto("Gnomos perdidos: " + gnomosPerdidos, 200, 400);
			}
		}
	}

	public int MinutosTranscurrido() {
		int tiempoTranscurrido = this.entorno.tiempo();
		int minutos = (tiempoTranscurrido / (1000 * 60)) % 60;
		return minutos;
	}

	public int SegundosTranscurrido() {
		int tiempoTranscurrido = this.entorno.tiempo();
		int segundos = (tiempoTranscurrido / 1000) % 60;
		return segundos;
	}

	public void gnomoNull(int i) {
		gnomos[i] = null;
	}

	public boolean colisionBloque(int base, int extDer, int extIzq, Bloque bloque) {
		return base >= bloque.getSup() &&
				extDer > bloque.getXizq() &&
				extIzq < bloque.getXder() &&
				base < bloque.getSup() + 5;
		// altura < bloque.getInf()
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
		for (int i = 0; i < bloq.length; i++) {
			if (colisionBloque(pep.getYBase(), pep.getExtremoDer(), pep.getExtremoIzq(), bloq[i])) {
				enBloque = true;
			}
		}
		if (pep.getYAltura() > 600) {
			return true;
		} else {
			if (!enBloque) {
				pep.caer();
			} else {
				pep.reiniciarSaltos();
			}
		}
		return false;
	}

	public void lanzarGnomo(Entorno entorno, Gnomo[] gnomo, Bloque[] bloq, int numGnomo) {
		gnomo[numGnomo].dibujar(entorno);
		if (gnomo[numGnomo].seCayo == false) {
			gnomo[numGnomo].moverGnomo(gnomo[numGnomo]);
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
				gnomo[numGnomo].moverGnomo(gnomo[numGnomo]);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
