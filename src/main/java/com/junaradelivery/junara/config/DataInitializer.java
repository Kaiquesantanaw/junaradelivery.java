package com.junaradelivery.junara.config;

import com.junaradelivery.junara.entity.Produto;
import com.junaradelivery.junara.entity.User;
import com.junaradelivery.junara.repository.ProdutoRepository;
import com.junaradelivery.junara.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
            admin.setRole("ADMIN");
            admin.setEnabled(Boolean.TRUE);
            userRepository.save(admin);
            System.out.println("✅ Admin criado com sucesso");
        }

        if (produtoRepository.count() == 0) {
            Produto produto = new Produto();
            produto.setNome("Lasanha à Bolonhesa");
            produto.setDescricao("Deliciosa lasanha caseira com molho de carne, queijo derretido e camadas crocantes");
            produto.setPreco(45.9);
            produto.setImagemUrl("/images/lasanha.svg");
            produtoRepository.save(produto);
            System.out.println("✅ Produto padrão criado: Lasanha à Bolonhesa");
        }
    }
}
