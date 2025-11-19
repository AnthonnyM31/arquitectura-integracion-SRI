package com.espe.arquitectura.backend.dto;

public record SriPersonaDto(
    String numeroRuc,
    String razonSocial,
    String nombreComercial,
    String estadoPersona
) {}