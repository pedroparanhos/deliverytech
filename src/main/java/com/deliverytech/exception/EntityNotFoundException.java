package com.deliverytech.exception;

public class EntityNotFoundException extends BusinessException {
    /**
     * Construtor que formata uma mensagem padrão de "não encontrado".
     * @param entityName O nome da entidade (ex: "Cliente", "Produto").
     * @param id O identificador que não foi encotrado.
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s com ID %d não encontrado", entityName, id));
    }
}
