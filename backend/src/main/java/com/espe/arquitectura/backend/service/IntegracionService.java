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

    // --- Constructor Manual ---
    public IntegracionService(SriClient sriClient) {
        this.sriClient = sriClient;
    }
    // --------------------------

    public InfoCompletaDto obtenerInformacion(String ruc, String email, String placa) {
        // 1. Buscar persona
        List<SriPersonaDto> personas = sriClient.obtenerPersonaPorRuc(ruc);

        if (personas == null || personas.isEmpty()) {
            throw new RuntimeException("No se encontró contribuyente con ese RUC");
        }

        SriPersonaDto persona = personas.get(0);

        // 2. Buscar vehículo (si aplica)
        List<SriVehiculoDto> vehiculos = Collections.emptyList();
        if (placa != null && !placa.isBlank()) {
            try {
                SriVehiculoDto vehiculo = sriClient.obtenerVehiculoPorPlaca(placa);
                if (vehiculo != null) {
                    vehiculos = List.of(vehiculo);
                }
            } catch (Exception e) {
                System.out.println("Error buscando vehiculo: " + e.getMessage());
            }
        }

        // 3. Respuesta
        return new InfoCompletaDto(
            email,
            ruc,
            persona.razonSocial(),
            persona.estadoPersona(),
            vehiculos
        );
    }
}