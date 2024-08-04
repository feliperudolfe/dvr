package br.com.vr.autorizador.util.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.autorizador.domain.sk.domain.DomainObject;
import br.com.vr.autorizador.util.container.MySQLTestContainer;

@ControllerIT
public abstract class ControllerBaseIT {

	public static MySQLTestContainer postgresContainer = MySQLTestContainer.getInstance();

	@Autowired
	protected MockMvc mock;

	@Autowired
	protected ObjectMapper mapper;
	
	public static class ContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
        	// Caso necessário inicialização de alguma outra config
        }
    }

	protected String toJson(DomainObject model) throws JsonProcessingException {
		return this.mapper.writeValueAsString(model);
	}

}