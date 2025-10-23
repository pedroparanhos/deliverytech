package com.deliverytech.exception;

/**
 * Exceção personalizada para representar erros de regra de negócio na aplicação
 * Servirá como base base para exceções mais específicas
 */
public class BusinessException extends RuntimeException {
    /**
     * @param message A mensagem descritiva do erro
     */
    public BusinessException(String message) {
        super(message);
    }
}
