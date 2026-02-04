package com.deliverytech.controller;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Endpoints para gerenciamento e clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @Operation(summary = "Cadastrar um novo cliente", description = "Cria um novo cliente no sistema.")
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        logger.info("Cadastro de cliente iniciado: {}", request.getEmail());

        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .ativo(true)
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);
        logger.debug("Cliente salvo com ID {}", salvo.getId());
        // Retorna 201 Created com a localização do novo recurso no header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(salvo.getId())
            .toUri();

        return ResponseEntity.created(location).body(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @Operation(summary = "Lista todos os clientes ativos", description = "Retorna uma lista pagina de todos os clientes comm status ativo.")
    @GetMapping
    public Page<ClienteResponse> listar(Pageable pageable) {
        logger.info("Listando todos os clientes ativos da forma paginada");
        Page<Cliente> clientesPaginados = clienteService.listarAtivos(pageable);
        return clientesPaginados.map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()));
    }
    
    @Operation(summary = "Busca um cliente por ID", description = "Retorna os detalhes de um cliente específico pelo seu ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return clienteService.buscarPorId(id)
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));
    }
    
    @Operation(summary = "Atualiza um cliente", description = "Atualiza os dados de um cliente existente a partir do seu ID.")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        logger.info("Atualizando cliente ID: {}", id);

        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);
        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @Operation(summary = "Ativa ou desativa um cliente", description = "Altere o status de um, cliente (ativo/inativo) a partir do seu ID.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza o status do cliente", description = "Verifica se está ativo, inativo ou outra funcionalidade que queira adicionar")
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}
