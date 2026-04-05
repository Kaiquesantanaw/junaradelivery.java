package com.junaradelivery.junara.controller;

import com.junaradelivery.junara.entity.Produto;
import com.junaradelivery.junara.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp", ".gif");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    // Magic-byte signatures for allowed image types
    private static final byte[][] MAGIC_BYTES = {
            new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF }, // JPEG
            new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 }, // PNG
            new byte[] { 0x47, 0x49, 0x46, 0x38 }, // GIF
            new byte[] { 0x52, 0x49, 0x46, 0x46 }, // WEBP (RIFF header)
    };

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.obterProdutoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio");
        }

        // Server-side size check
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body("Arquivo muito grande. Máximo: 5 MB");
        }

        // Extension whitelist
        String originalName = file.getOriginalFilename();
        String ext = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf('.')).toLowerCase()
                : "";
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            return ResponseEntity.badRequest().body("Extensão não permitida. Use: jpg, jpeg, png, webp ou gif");
        }

        // Content-Type check
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Apenas imagens são permitidas");
        }

        // Magic bytes check (file signature)
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[8];
            int read = is.read(header);
            if (read < 4 || !hasValidMagicBytes(header)) {
                return ResponseEntity.badRequest().body("Conteúdo do arquivo inválido");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Não foi possível ler o arquivo");
        }

        try {
            Path uploadDir = Paths.get("uploads").toAbsolutePath();
            Files.createDirectories(uploadDir);
            String fileName = UUID.randomUUID().toString() + ext;
            Path filePath = uploadDir.resolve(fileName);
            // Prevent path traversal
            if (!filePath.startsWith(uploadDir)) {
                return ResponseEntity.badRequest().body("Caminho inválido");
            }
            Files.write(filePath, file.getBytes());
            return ResponseEntity.ok("/uploads/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar arquivo");
        }
    }

    private boolean hasValidMagicBytes(byte[] header) {
        for (byte[] magic : MAGIC_BYTES) {
            if (header.length >= magic.length &&
                    Arrays.equals(Arrays.copyOf(header, magic.length), magic)) {
                return true;
            }
        }
        return false;
    }
}
