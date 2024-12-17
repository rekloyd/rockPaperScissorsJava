package org.example.restjava;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/")
public class rockPaperScissorWebService {
    private static ArrayList<Partida> llistaPartides = new ArrayList<>();

    @GET
    @Path("/partida")
    public Response llistarPartides() {
        System.out.println(llistaPartides);
        if (llistaPartides.isEmpty()) {
            return Response.status(404).entity("No hay partidas disponibles").build();
        }
        return Response.status(200).entity(llistaPartides.toString()).build();
    }



    // http://localhost:8080/RestJava_war_exploded/api/consultarEstatPartida/{codiPartida}
    @GET
    @Path("/consultarEstatPartida/{codiPartida}")
    public String llistarUnaPartida(@PathParam("codiPartida") int codiPartida) {
        int pos = llistaPartides.indexOf(new Partida(codiPartida));

        // Comprobar si la partida existe
        if (pos == -1) {
            return Response.status(404).entity("Partida no trobada").build().toString();
        }

        return llistaPartides.get(pos).toString();
    }

    @POST
    @Path("/iniciarJoc/{codiPartida}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response iniciarPartida(@PathParam("codiPartida") int codiPartida,
                                   @FormParam("jugador1") String jugador1,
                                   @FormParam("jugador2") String jugador2
                                   ) {
        // Crear la nueva partida
        Partida nuevaPartida = new Partida(codiPartida);
        if(jugador1 != null && jugador2 != null) {
            nuevaPartida.setJugador1(jugador1);
            nuevaPartida.setJugador2(jugador2);
        }else{
            return Response.status(400).entity("Uno o más jugadores están vacíos").build();
        }


        // Agregar la partida a la lista
        llistaPartides.add(nuevaPartida);

        // Mostrar el estado de la lista en consola
        System.out.println(llistaPartides);

        // Retornar la respuesta exitosa
        return Response.status(200).entity("Partida iniciada correctament").build();
    }


    @DELETE
    @Path("/acabarPartida/{codiPartida}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response esborrarPartida(@PathParam("codiPartida") int codiPartida) {
        int pos = llistaPartides.indexOf(new Partida(codiPartida));

        // Comprobar si la partida existe
        if (pos == -1) {
            return Response.status(404).entity("Partida no trobada").build();
        }

        llistaPartides.remove(pos);
        return Response.status(200).entity("Partida esborrada").build();
    }

    @PUT
    @Path("/moureJugador/{codiPartida}/{jugador}/{tipusMoviment}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modificarPartida(@PathParam("codiPartida") int codiPartida,
                                     @PathParam("jugador") String jugador,
                                     @PathParam("tipusMoviment") String tipusMoviment) {
        // Buscar la partida por código
        int pos = llistaPartides.indexOf(new Partida(codiPartida));

        // Verificar si la partida existe
        if (pos == -1) {
            return Response.status(404).entity("Partida no trobada").build();
        }

        Partida partida = llistaPartides.get(pos);

        // Verificar si el jugador es uno de los jugadores de la partida
        if (!partida.getJugador1().equals(jugador) && !partida.getJugador2().equals(jugador)) {
            return Response.status(403).entity("Jugador no autoritzat").build();
        }

        // Actualizar el movimiento del jugador
        if (partida.getJugador1().equals(jugador)) {
            partida.setMovimientoJ1(tipusMoviment);
            partida.setTurno(partida.getTurno() + 1);
        } else if (partida.getJugador2().equals(jugador)) {
            partida.setMovimientoJ2(tipusMoviment);
            partida.setTurno(partida.getTurno() + 1);
        }

        // Comprobar si ambos jugadores han realizado su movimiento
        if (!partida.getMovimientoJ1().isEmpty() && !partida.getMovimientoJ2().isEmpty()) {
            // Comparar los movimientos según las reglas de Piedra, Papel, Tijeras
            String movimientoJ1 = partida.getMovimientoJ1().toLowerCase();
            String movimientoJ2 = partida.getMovimientoJ2().toLowerCase();

            if (movimientoJ1.equals(movimientoJ2)) {
                // Empate
                return Response.status(200).entity("Empate, los jugadores han elegido el mismo movimiento").build();
            } else if ((movimientoJ1.equals("pedra") && movimientoJ2.equals("tissores")) ||
                    (movimientoJ1.equals("tissores") && movimientoJ2.equals("paper")) ||
                    (movimientoJ1.equals("paper") && movimientoJ2.equals("pedra"))) {
                // Jugador 1 gana
                partida.setPuntuacionJ1(partida.getPuntuacionJ1() + 1);
                partida.setMovimientoJ1("");
                partida.setMovimientoJ2("");
                if (partida.getPuntuacionJ1() == 3) {
                    return Response.status(200).entity("Jugador 1 guanya la partida amb 3 punts").build();
                }
                return Response.status(200).entity("Jugador 1 guanya").build();
            } else {
                // Jugador 2 gana
                partida.setPuntuacionJ2(partida.getPuntuacionJ2() + 1);
                partida.setMovimientoJ1("");
                partida.setMovimientoJ2("");
                if (partida.getPuntuacionJ2() == 3) {
                    return Response.status(200).entity("Jugador 2 guanya la partida amb 3 punts").build();
                }
                return Response.status(200).entity("Jugador 2 guanya").build();
            }
        }

        // Si ambos jugadores no han hecho su movimiento, no se puede comparar
        return Response.status(200).entity("Esperant el moviment de l'altre jugador").build();
    }



    // Constructor
    public rockPaperScissorWebService() {
        if (llistaPartides.isEmpty()) {
            llistaPartides.add(new Partida(10));
        }

    }
}
