package com.simplemobiletools.clock.services;

public class GeoData {
    public static double CalcularDistancia(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        int RaioDoPlaneta = 6371; // RaioDoPlanetaaio do nosso planeta redondo que deveria se chamar redondeta
        double altura = el1 - el2; // calcula a distancia da altura
        double DistanciaLatitudes = Math.toRadians(lat2 - lat1); //distancia entre as latitudes
        double DistanciaLongitudes = Math.toRadians(lon2 - lon1); // distancia entre as longitudes
        double a = Math.sin(DistanciaLatitudes / 2) * Math.sin(DistanciaLatitudes / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(DistanciaLongitudes / 2) * Math.sin(DistanciaLongitudes / 2);
        double variosCalculosLoucos = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double Distancia = RaioDoPlaneta * variosCalculosLoucos; // converte a distancia em metros.
        Distancia = Math.pow(Distancia, 2) + Math.pow(altura, 2); // calcula a distancia levando em consideração junto a diferença de altura desejada e registrada, não adianta estar passando em cima de avião na hora
        return Math.sqrt(Distancia);
    }

    public static Boolean VerificaDistancia(double lat1, double lat2, double lon1, double lon2, double alt1, double alt2, double distanciaMaxima) {
        double distanciaDoPonto = CalcularDistancia(lat1, lat2, lon1, lon2, alt1, alt2);
        Boolean resp = distanciaMaxima > distanciaDoPonto;
        System.out.println(resp+"aaaaaa");
        return resp;
    }
}
