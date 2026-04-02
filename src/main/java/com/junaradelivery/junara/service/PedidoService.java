package com.junaradelivery.junara.service;

import com.junaradelivery.junara.dto.CreatePedidoRequest;
import com.junaradelivery.junara.entity.Pedido;
import com.junaradelivery.junara.entity.Produto;
import com.junaradelivery.junara.exception.ResourceNotFoundException;
import com.junaradelivery.junara.model.Cliente;
import com.junaradelivery.junara.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido criarPedidoComDTO(CreatePedidoRequest request) {
        Cliente cliente = clienteService.obterClientePorId(request.getClienteId());

        List<Produto> produtos = request.getProdutoIds().stream()
                .map(p -> produtoService.obterProdutoPorId(p.getProdutoId()))
                .collect(Collectors.toList());

        double valorTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .produtos(produtos)
                .valorTotal(valorTotal)
                .build();

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obterPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    public List<Pedido> obterPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obterPedidosPorStatus(String status) {
        Pedido.StatusPedido statusEnum = Pedido.StatusPedido.valueOf(status.toUpperCase());
        return pedidoRepository.findByStatus(statusEnum);
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = obterPedidoPorId(id);
        if (pedidoAtualizado.getStatus() != null) {
            pedido.setStatus(pedidoAtualizado.getStatus());
        }
        if (pedidoAtualizado.getProdutos() != null) {
            pedido.setProdutos(pedidoAtualizado.getProdutos());
        }
        if (pedidoAtualizado.getValorTotal() != null) {
            pedido.setValorTotal(pedidoAtualizado.getValorTotal());
        }
        return pedidoRepository.save(pedido);
    }

    public void excluirPedido(Long id) {
        obterPedidoPorId(id);
        pedidoRepository.deleteById(id);
    }
}
