package com.junaradelivery.junara.controller;

import com.junaradelivery.junara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody Map<String, String> body,
            Authentication auth) {

        String senhaAtual = body.get("senhaAtual");
        String novaSenha = body.get("novaSenha");

        if (senhaAtual == null || novaSenha == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Informe senhaAtual e novaSenha"));
        }

        userService.changePassword(auth.getName(), senhaAtual, novaSenha);
        return ResponseEntity.ok(Map.of("mensagem", "Senha alterada com sucesso"));
    }
}
