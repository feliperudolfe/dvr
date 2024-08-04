package br.com.vr.autorizador.util.container;

import org.testcontainers.containers.MySQLContainer;

@SuppressWarnings("resource")
public class MySQLTestContainer extends MySQLContainer<MySQLTestContainer> {

	private static final MySQLTestContainer CONTAINER;
	private static final String IMAGE_NAME = "mysql:5.7.24";

	static {

		CONTAINER = new MySQLTestContainer()
				.withUsername("root")
				.withPassword("root")
				.withDatabaseName("miniautorizador");

		CONTAINER.start();

		System.setProperty("DATASOURCE_USERNAME", CONTAINER.getUsername());
    	System.setProperty("DATASOURCE_PASSWORD", CONTAINER.getPassword());
    	System.setProperty("DATASOURCE_HOST", CONTAINER.getHost());
    	System.setProperty("DATASOURCE_SCHEMA", CONTAINER.getDatabaseName());
    	System.setProperty("DATASOURCE_PORT", CONTAINER.getFirstMappedPort().toString());
	}

	public MySQLTestContainer() {
		super(IMAGE_NAME);
	}

	public static final MySQLTestContainer getInstance() {
		return CONTAINER;
	}
	
}