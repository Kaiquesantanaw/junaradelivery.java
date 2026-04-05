package com.junaradelivery.junara.service;

import com.junaradelivery.junara.entity.Produto;
import com.junaradelivery.junara.exception.ResourceNotFoundException;
import com.junaradelivery.junara.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto obterProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
    }

    public Produto salvar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produto = obterProdutoPorId(id);
        if (produtoAtualizado.getNome() != null && !produtoAtualizado.getNome().isEmpty()) {
            produto.setNome(produtoAtualizado.getNome());
        }
        if (produtoAtualizado.getDescricao() != null && !produtoAtualizado.getDescricao().isEmpty()) {
            produto.setDescricao(produtoAtualizado.getDescricao());
        }
        if (produtoAtualizado.getPreco() != null && produtoAtualizado.getPreco() > 0) {
            produto.setPreco(produtoAtualizado.getPreco());
        }
        if (produtoAtualizado.getImagemUrl() != null) {
            produto.setImagemUrl(produtoAtualizado.getImagemUrl());
        }
        return produtoRepository.save(produto);
    }

    public void excluirProduto(Long id) {
        obterProdutoPorId(id);
        produtoRepository.deleteById(id);
    }
}
