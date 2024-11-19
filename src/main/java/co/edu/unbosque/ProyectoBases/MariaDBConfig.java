package co.edu.unbosque.ProyectoBases;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "co.edu.unbosque.ProyectoBases.MariaDB.repository", entityManagerFactoryRef = "mariaDBEntityManager", transactionManagerRef = "mariaDBTransactionManager")
public class MariaDBConfig {

	@Bean(name = "mariaDB")
	@ConfigurationProperties(prefix = "mariadb.datasource")
	public DataSource mariaDBDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("mariaDBEntityManager")
	public LocalContainerEntityManagerFactoryBean mariaDBEntityManager(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show_sql", "true");
		return entityManagerFactoryBuilder.dataSource(mariaDBDataSource())
				.packages("co.edu.unbosque.ProyectoBases.MariaDB.model").persistenceUnit("cliente")
				.properties(jpaProperties).build();
	}

	@Bean("mariaDBTransactionManager")
	public PlatformTransactionManager mariaDBTransactionManager(
			@Qualifier("mariaDBEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
