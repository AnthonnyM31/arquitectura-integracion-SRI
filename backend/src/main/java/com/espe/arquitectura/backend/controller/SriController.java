package com.espe.arquitectura.backend.controller;

import com.espe.arquitectura.backend.dto.InfoCompletaDto;
import com.espe.arquitectura.backend.service.IntegracionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integracion")
@CrossOrigin(origins = "*") 
public class SriController {

    private final IntegracionService service;

    // --- Constructor Manual (Para arreglar el error de Lombok) ---
    public SriController(IntegracionService service) {
        this.service = service;
    }
    // ------------------------------------------------------------

    @GetMapping("/consultar")
    public ResponseEntity<InfoCompletaDto> consultar(
            @RequestParam String ruc,
            @RequestParam String email,
            @RequestParam(required = false) String placa
    ) {
        return ResponseEntity.ok(service.obtenerInformacion(ruc, email, placa));
    }
}