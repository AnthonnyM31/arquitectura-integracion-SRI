package com.espe.arquitectura.backend.client;

import com.espe.arquitectura.backend.dto.SriPersonaDto;
import com.espe.arquitectura.backend.dto.SriVehiculoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "sri-client", url = "https://srienlinea.sri.gob.ec")
public interface SriClient {

    // 1. Verificar si existe (El endpoint real devuelve boolean a veces, pero usaremos el de obtener datos para validar existencia directamente y ahorrar una llamada)
    
    // 2. Obtener datos de persona (Este endpoint suele devolver una lista)
    @GetMapping("/sri-catastro-sujeto-servicio-internet/rest/ConsolidadoContribuyente/obtenerPorNumerosRuc")
    List<SriPersonaDto> obtenerPersonaPorRuc(@RequestParam("ruc") String ruc);

    // 3. Obtener vehículo
    @GetMapping("/sri-matriculacion-vehicular-recaudacion-servicio-internet/rest/BaseVehiculo/obtenerPorNumeroPlacaOPorNumeroCampvOPorNumeroCpn")
    SriVehiculoDto obtenerVehiculoPorPlaca(@RequestParam("numeroPlacaCampvCpn") String placa);
}