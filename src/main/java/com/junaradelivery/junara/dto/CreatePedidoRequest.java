package com.junaradelivery.junara.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePedidoRequest {

    private Long clienteId;
    private List<ProdutoQuantidadeDTO> produtoIds;
    private String observacoes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoQuantidadeDTO {
        private Long produtoId;
        private Integer quantidade;
    }
}
