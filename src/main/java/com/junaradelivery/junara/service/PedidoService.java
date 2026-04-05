package com.junaradelivery.junara.service;

import com.junaradelivery.junara.dto.CreatePedidoRequest;
import com.junaradelivery.junara.entity.Pedido;
import com.junaradelivery.junara.entity.PedidoItem;
import com.junaradelivery.junara.entity.Produto;
import com.junaradelivery.junara.exception.ResourceNotFoundException;
import com.junaradelivery.junara.model.Cliente;
import com.junaradelivery.junara.repository.PedidoItemRepository;
import com.junaradelivery.junara.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido criarPedidoComDTO(CreatePedidoRequest request) {
        Cliente cliente = clienteService.obterClientePorId(request.getClienteId());

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .build();
        pedidoRepository.save(pedido);

        for (CreatePedidoRequest.ProdutoQuantidadeDTO p : request.getProdutoIds()) {
            Produto produto = produtoService.obterProdutoPorId(p.getProdutoId());
            int qty = p.getQuantidade() != null && p.getQuantidade() > 0 ? p.getQuantidade() : 1;
            PedidoItem item = PedidoItem.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(qty)
                    .precoUnitario(produto.getPreco())
                    .build();
            pedido.getItens().add(item);
        }

        pedido.recalcularTotal();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido adicionarProduto(Long pedidoId, Long produtoId, int quantidade) {
        Pedido pedido = obterPedidoPorId(pedidoId);
        Produto produto = produtoService.obterProdutoPorId(produtoId);

        pedidoItemRepository.findByPedidoIdAndProdutoId(pedidoId, produtoId)
                .ifPresentOrElse(
                        item -> item.setQuantidade(item.getQuantidade() + quantidade),
                        () -> pedido.getItens().add(PedidoItem.builder()
                                .pedido(pedido)
                                .produto(produto)
                                .quantidade(quantidade)
                                .precoUnitario(produto.getPreco())
                                .build()));

        pedido.recalcularTotal();
        pedido.setDataAtualizacao(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido removerProduto(Long pedidoId, Long produtoId) {
        Pedido pedido = obterPedidoPorId(pedidoId);
        pedido.getItens().removeIf(item -> item.getProduto().getId().equals(produtoId));
        pedido.recalcularTotal();
        pedido.setDataAtualizacao(LocalDateTime.now());
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

    @Transactional
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = obterPedidoPorId(id);
        if (pedidoAtualizado.getStatus() != null) {
            pedido.setStatus(pedidoAtualizado.getStatus());
        }
        pedido.setDataAtualizacao(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido editarItens(Long id, CreatePedidoRequest request) {
        Pedido pedido = obterPedidoPorId(id);

        // troca cliente se informado
        if (request.getClienteId() != null) {
            pedido.setCliente(clienteService.obterClientePorId(request.getClienteId()));
        }

        // substitui todos os itens
        pedido.getItens().clear();
        pedidoRepository.save(pedido); // flush orphans

        for (CreatePedidoRequest.ProdutoQuantidadeDTO p : request.getProdutoIds()) {
            Produto produto = produtoService.obterProdutoPorId(p.getProdutoId());
            int qty = p.getQuantidade() != null && p.getQuantidade() > 0 ? p.getQuantidade() : 1;
            pedido.getItens().add(PedidoItem.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(qty)
                    .precoUnitario(produto.getPreco())
                    .build());
        }

        pedido.recalcularTotal();
        pedido.setDataAtualizacao(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, String status) {
        Pedido pedido = obterPedidoPorId(id);
        pedido.setStatus(Pedido.StatusPedido.valueOf(status.toUpperCase()));
        pedido.setDataAtualizacao(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluirPedido(Long id) {
        obterPedidoPorId(id);
        pedidoRepository.deleteById(id);
    }
}
