package com.espe.arquitectura.backend.dto;

import java.util.List;

public record InfoCompletaDto(
    String email,
    String ruc,
    String nombre,
    String estadoContribuyente,
    List<SriVehiculoDto> vehiculos
) {}