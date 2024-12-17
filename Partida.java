package org.example.restjava;

import java.util.Objects;

public class Partida {
    private int codi;
    private String movimientoJ1;
    private String movimientoJ2;
    private String jugador1;
    private String jugador2;
    private int turno;
    private int puntuacionJ1;
    private int puntuacionJ2;

    public Partida(int codi) {
        this.codi = codi;
        this.movimientoJ1 = "";
        this.movimientoJ2 = "";
        this.jugador1 = "jugador1";
        this.jugador2 = "jugador2";
        this.turno = 1;
        this.puntuacionJ1 = 0;
        this.puntuacionJ2 = 0;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getMovimientoJ1() {
        return movimientoJ1;
    }

    public void setMovimientoJ1(String movimientoJ1) {
        this.movimientoJ1 = movimientoJ1;
    }

    public String getMovimientoJ2() {
        return movimientoJ2;
    }

    public void setMovimientoJ2(String movimientoJ2) {
        this.movimientoJ2 = movimientoJ2;
    }

    public String getJugador1() {
        return jugador1;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getPuntuacionJ1() {
        return puntuacionJ1;
    }

    public void setPuntuacionJ1(int puntuacionJ1) {
        this.puntuacionJ1 = puntuacionJ1;
    }

    public int getPuntuacionJ2() {
        return puntuacionJ2;
    }

    public void setPuntuacionJ2(int puntuacionJ2) {
        this.puntuacionJ2 = puntuacionJ2;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "codi=" + codi +
                ", movimientoJ1='" + movimientoJ1 + '\'' +
                ", movimientoJ2='" + movimientoJ2 + '\'' +
                ", jugador1='" + jugador1 + '\'' +
                ", jugador2='" + jugador2 + '\'' +
                ", turno=" + turno +
                ", puntuacionJ1=" + puntuacionJ1 +
                ", puntuacionJ2=" + puntuacionJ2 +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Partida partida = (Partida) obj;
        return codi == partida.codi;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codi);
    }

}
