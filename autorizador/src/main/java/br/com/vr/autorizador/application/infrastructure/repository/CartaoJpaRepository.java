package br.com.vr.autorizador.application.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.autorizador.domain.cartao.model.Cartao;
import br.com.vr.autorizador.domain.cartao.repository.CartaoRepository;

public interface CartaoJpaRepository extends CartaoRepository, JpaRepository<Cartao, String> {

}