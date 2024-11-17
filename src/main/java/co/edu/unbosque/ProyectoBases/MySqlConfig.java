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
@EnableJpaRepositories(basePackages = "co.edu.unbosque.ProyectoBases.Sql.repository", entityManagerFactoryRef = "mySqlEntityManager", transactionManagerRef = "mySqlTransactionManager")
public class MySqlConfig {

	@Bean(name = "mysqlDb")
	@ConfigurationProperties(prefix = "mysql.datasource")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean("mySqlEntityManager")
	public LocalContainerEntityManagerFactoryBean mySqlEntityManager(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
		Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show_sql", "true");
		return entityManagerFactoryBuilder.dataSource(mysqlDataSource())
				.packages("co.edu.unbosque.ProyectoBases.Sql.model").persistenceUnit("apartamento")
				.properties(jpaProperties).build();
	}

	@Bean("mySqlTransactionManager")
	public PlatformTransactionManager mySqlTransactionManager(
			@Qualifier("mySqlEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
