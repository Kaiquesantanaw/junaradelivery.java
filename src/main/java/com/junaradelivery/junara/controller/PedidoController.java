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
        return ResponseEntity.ok(pedidoService.listarPedidos());
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

    @PostMapping("/{id}/produtos")
    public ResponseEntity<Pedido> adicionarProduto(
            @PathVariable Long id,
            @RequestParam Long produtoId,
            @RequestParam(defaultValue = "1") int quantidade) {
        return ResponseEntity.ok(pedidoService.adicionarProduto(id, produtoId, quantidade));
    }

    @DeleteMapping("/{id}/produtos/{produtoId}")
    public ResponseEntity<Pedido> removerProduto(
            @PathVariable Long id,
            @PathVariable Long produtoId) {
        return ResponseEntity.ok(pedidoService.removerProduto(id, produtoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.atualizarPedido(id, pedido));
    }

    @PutMapping("/{id}/itens")
    public ResponseEntity<Pedido> editarItens(
            @PathVariable Long id,
            @RequestBody CreatePedidoRequest request) {
        return ResponseEntity.ok(pedidoService.editarItens(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoService.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }
}
