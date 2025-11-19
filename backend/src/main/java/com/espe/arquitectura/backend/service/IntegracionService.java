package com.espe.arquitectura.backend.service;

import com.espe.arquitectura.backend.client.SriClient;
import com.espe.arquitectura.backend.dto.InfoCompletaDto;
import com.espe.arquitectura.backend.dto.SriPersonaDto;
import com.espe.arquitectura.backend.dto.SriVehiculoDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IntegracionService {

    private final SriClient sriClient;
    private final AntService antService; // <--- Nuevo servicio inyectado

    // Constructor Manual actualizado
    public IntegracionService(SriClient sriClient, AntService antService) {
        this.sriClient = sriClient;
        this.antService = antService;
    }

    public InfoCompletaDto obtenerInformacion(String ruc, String email, String placa) {
        // 1. SRI: Buscar persona
        List<SriPersonaDto> personas = sriClient.obtenerPersonaPorRuc(ruc);
        if (personas == null || personas.isEmpty()) {
            throw new RuntimeException("No se encontró contribuyente con ese RUC");
        }
        SriPersonaDto persona = personas.get(0);

        // 2. ANT: Buscar Puntos (Con Caché y Resiliencia)
        // Asumimos que la cédula son los 10 primeros dígitos del RUC (típico en Personas Naturales)
        String cedula = ruc.length() >= 10 ? ruc.substring(0, 10) : ruc;
        String puntos = antService.obtenerPuntosLicencia(cedula);

        // 3. SRI: Buscar vehículo
        List<SriVehiculoDto> vehiculos = Collections.emptyList();
        if (placa != null && !placa.isBlank()) {
            try {
                SriVehiculoDto vehiculo = sriClient.obtenerVehiculoPorPlaca(placa);
                if (vehiculo != null) {
                    vehiculos = List.of(vehiculo);
                }
            } catch (Exception e) {
                System.out.println("Error vehiculo: " + e.getMessage());
            }
        }

        // 4. Retornar todo junto
        return new InfoCompletaDto(
            email,
            ruc,
            persona.razonSocial(),
            persona.estadoPersona(),
            puntos, // <--- Dato de la ANT
            vehiculos
        );
    }
}