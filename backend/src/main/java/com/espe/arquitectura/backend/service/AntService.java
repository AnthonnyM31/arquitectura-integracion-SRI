package com.espe.arquitectura.backend.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AntService {

    private final StringRedisTemplate redisTemplate;

    // Constructor Manual
    public AntService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String obtenerPuntosLicencia(String cedula) {
        String cacheKey = "ant:puntos:" + cedula;

        // 1. Consultar Caché
        try {
            String puntosEnCache = redisTemplate.opsForValue().get(cacheKey);
            if (puntosEnCache != null) {
                System.out.println("Cache Hit! Desde Redis para: " + cedula);
                return puntosEnCache;
            }
        } catch (Exception e) {
            System.err.println("Redis no disponible: " + e.getMessage());
        }

        // 2. Consultar Web (Scraping)
        System.out.println("Cache Miss. Consultando ANT...");
        String puntosReales = consultarWebAnt(cedula);

        // 3. Guardar en Caché (1 hora)
        if (!puntosReales.startsWith("Error")) {
            try {
                redisTemplate.opsForValue().set(cacheKey, puntosReales, Duration.ofHours(1));
            } catch (Exception e) {
                System.err.println("No se pudo guardar caché: " + e.getMessage());
            }
        }

        return puntosReales;
    }

    private String consultarWebAnt(String cedula) {
        String url = "https://consultaweb.ant.gob.ec/PortalWEB/paginas/clientes/clp_grid_citaciones.jsp?ps_tipo_identificacion=CED&ps_identificacion=" 
                     + cedula + "&ps_placa=";
        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            String texto = doc.body().text();
            // Simulacion academica: si conecta, asumimos 30 puntos
            return texto.length() > 0 ? "30" : "0";
        } catch (Exception e) {
            return "Error: ANT No Disponible";
        }
    }
}