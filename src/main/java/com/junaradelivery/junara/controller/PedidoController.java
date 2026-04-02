package com.junaradelivery.junara.controller;

import com.junaradelivery.junara.dto.CreatePedidoRequest;
import com.junaradelivery.junara.entity.Pedido;
import com.junaradelivery.junara.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/criar")
    public ResponseEntity<Pedido> criarPedidoComDTO(@RequestBody CreatePedidoRequest request) {
        Pedido novoPedido = pedidoService.criarPedidoComDTO(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obterPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obterPedidoPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obterPedidosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.obterPedidosPorCliente(clienteId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> obterPedidosPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(pedidoService.obterPedidosPorStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }
}
