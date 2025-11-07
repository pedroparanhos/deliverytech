package com.deliverytech.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Define o gerenciador de cache da aplicação, agora utilizando o Caffeine.
     * O Caffeine é uma biblioteca de alta performance que nos dá mais controle
     * sobre o comportamento do cache.
     *
     * @return O gerenciador de cache Caffeine configurado.
     */
    // @Bean: Diz ao Spring: "Quando alguém precisar de um CacheManager, execute este método e use o objeto que ele retorna".
    @Bean
    public CacheManager cacheManager() {
        // Criamos um gerenciador de cache do tipo Caffeine
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // Informamos os nomes dos caches que já usamos na aplicação
        cacheManager.setCacheNames(List.of(
                "restaurantes",
                "restaurantesCategoria",
                "restaurantesPaginados",
                "clientes" // Boa prática: adicionar o cache de clientes aqui também
        ));

        // Aqui está a grande melhoria: configuramos o comportamento do cache!
        cacheManager.setCaffeine(caffeineBuilder());

        return cacheManager;
    }

    /**
     * Construtor de configurações para o cache Caffeine.
     * @return Uma instância do Caffeine configurada.
     */
    private Caffeine<Object, Object> caffeineBuilder() {
        return Caffeine.newBuilder()
                // Define que as entradas do cache expiram 5 minutos após a última escrita/modificação.
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // Define um tamanho máximo de 100 entradas no cache.
                .maximumSize(100)
                // Grava estatísticas de uso do cache (útil para monitoramento).
                .recordStats();
    }
}