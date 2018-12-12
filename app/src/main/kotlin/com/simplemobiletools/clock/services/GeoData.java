package com.simplemobiletools.clock.services;

public class GeoData {
    static final double RaioDoPlaneta = 6378.1370D;//já que é redondo né
    static final double Radiano = (Math.PI / 180D);//precisa pra formula

    public static double CalcularDistancia(double lat1, double lat2, double long1, double long2) {// Como o nome ja diz, irá calcular a distancia entre dois pontos distintos
        double distanciaLongitude = (long2 - long1) * Radiano;
        double distanciaLatitude = (lat2 - lat1) * Radiano;
        double part1 = Math.pow(Math.sin(distanciaLatitude / 2.0), 2.0)
                + Math.cos(lat1 * Radiano) * Math.cos(lat2 * Radiano)
                * Math.pow(Math.sin(distanciaLongitude / 2.0), 2.0);
        double part2 = 2.0 * Math.atan2(Math.sqrt(part1), Math.sqrt(1.0 - part1));
        double part3 = RaioDoPlaneta * part2;

        return part3 * 1000;
    }

    public static Boolean VerificaDistancia(double lat1, double lat2, double lon1, double lon2, double alt1, double alt2, double distanciaMaxima) {
        double distanciaDoPonto = CalcularDistancia(lat1, lat2, lon1, lon2); // Calcula a distancia entre as duas coordenadas
        Boolean resp = distanciaMaxima < distanciaDoPonto; // Se a distancia entre os pontos das coordenadas for maior que a distancia aceita pelo app, retorna true para despertar
        return resp;
    }
}
