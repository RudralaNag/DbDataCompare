package com.americanexpress.dbcompare.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
	private  JdbcTemplate jdbcTemplateDatabase1;
	
	@Autowired
	private  JdbcTemplate jdbcTemplateDatabase2;

    @PostConstruct
    public void init() throws SQLException, IOException {
        executeSqlScript(jdbcTemplateDatabase1, "classpath:db1-schema.sql");
        executeSqlScript(jdbcTemplateDatabase2, "classpath:db2-schema.sql");
    }

    private void executeSqlScript(JdbcTemplate jdbcTemplate, String scriptPath) throws IOException {
        Resource resource = resourceLoader.getResource(scriptPath);
        String sql = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        jdbcTemplate.execute(sql);
        System.out.println("Executed script: " + scriptPath);
    }
}

