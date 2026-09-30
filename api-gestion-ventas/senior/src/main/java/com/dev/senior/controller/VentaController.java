package com.dev.senior.controller;

import com.dev.senior.service.*;

import jakarta.websocket.server.PathParam;

import com.dev.senior.dto.VentaResponse;
import com.dev.senior.model.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/ventas")
public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

@GetMapping
public ResponseEntity<List<VentaResponse>> listar() {
    List<VentaResponse> ventas = ventaService.listarTodas().stream()
            .map(venta -> new VentaResponse(
                    venta.getId(),
                    venta.getUsuario().getId(),
                    venta.getFecha(),
                    venta.getTotal(),
                    venta.getDetalles().stream()
                            .map(detalle -> new com.dev.senior.dto.DetalleVentaResponse(
                                    detalle.getProducto().getId(),
                                    detalle.getProducto().getNombre(),
                                    detalle.getCantidad(),
                                    detalle.getPrecioUnitario(),
                                    detalle.getPrecioUnitario()
                                            .multiply(java.math.BigDecimal.valueOf(detalle.getCantidad()))
                            ))
                            .toList()
            ))
            .toList();

    return ResponseEntity.ok(ventas);
}

    
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> buscarPorId(@PathVariable Long id){
        return ventaService.buscarPorId(id)
                    .map(venta -> {

                        VentaResponse response = new VentaResponse(
                            venta.getId(),
                            venta.getUsuario().getId(),
                            venta.getFecha(),
                            venta.getTotal(),
                            venta.getDetalles().stream()
                            .map(detalle -> new com.dev.senior.dto.DetalleVentaResponse(
                                    detalle.getProducto().getId(),
                                    detalle.getProducto().getNombre(),
                                    detalle.getCantidad(),
                                    detalle.getPrecioUnitario(),
                                    detalle.getPrecioUnitario()
                                            .multiply(java.math.BigDecimal.valueOf(detalle.getCantidad()))
                            ))
                            .toList()
                        );
                        return ResponseEntity.ok(response);
                    })
    .orElse(ResponseEntity.notFound().build());    
        }

}
