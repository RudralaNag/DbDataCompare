package com.americanexpress.dbcompare.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataComparisonService {

	private static final Logger logger = LoggerFactory.getLogger(DataComparisonService.class);

	@Autowired
	private  JdbcTemplate jdbcTemplateDatabase1;
	
	@Autowired
	private  JdbcTemplate jdbcTemplateDatabase2;
	
	@Value("${source.database}")
	private String soruceDatabaseName;

	public void compareDatabases() {
		try (FileWriter logFile = new FileWriter("data_migration_log.txt", true)) {
			// Fetch the list of tables from both databases
			List<String> tablesDatabase1 = fetchTables(jdbcTemplateDatabase1);
			List<String> tablesDatabase2 = fetchTables(jdbcTemplateDatabase2);

			for (String table : tablesDatabase1) {
				if (tablesDatabase2.contains(table)) {
					compareTableData(table, logFile);
				} else {
					logFile.write("Table " + table + " exists in database1 but not in database2.\n");
					logger.info("Table " + table + " exists in database1 but not in database2.");
				}
			}

		} catch (IOException e) {
			logger.error("Error writing to log file", e);
		}
	}

	private List<String> fetchTables(JdbcTemplate jdbcTemplate) {
		String query = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
		return jdbcTemplate.queryForList(query, new Object[] { soruceDatabaseName }, String.class);
	}

	private void compareTableData(String tableName, FileWriter logFile) throws IOException {
		// Fetch data from both databases for the current table
		List<Map<String, Object>> rowsDatabase1 = jdbcTemplateDatabase1.queryForList("SELECT * FROM " + tableName);
		List<Map<String, Object>> rowsDatabase2 = jdbcTemplateDatabase2.queryForList("SELECT * FROM " + tableName);

		// Compare row by row
		for (int i = 0; i < rowsDatabase1.size(); i++) {
			Map<String, Object> row1 = rowsDatabase1.get(i);
			if (i < rowsDatabase2.size()) {
				Map<String, Object> row2 = rowsDatabase2.get(i);
				if (!row1.equals(row2)) {
					System.out.println(i+1);
					logFile.write("Data mismatch in table " + tableName + " at row " + (i + 1) + ":\n");
					logFile.write("Database1 row: " + row1.toString() + "\n");
					logFile.write("Database2 row: " + row2.toString() + "\n");
				}
			} else {
				logFile.write("Extra row found in database1 table " + tableName + " at row " + (i + 1) + ":\n");
				logFile.write("Database1 row: " + row1.toString() + "\n");
			}
		}

		// Check for extra rows in database2
		if (rowsDatabase2.size() > rowsDatabase1.size()) {
			for (int i = rowsDatabase1.size(); i < rowsDatabase2.size(); i++) {
				logFile.write("Extra row found in database2 table " + tableName + " at row " + (i + 1) + ":\n");
				logFile.write("Database2 row: " + rowsDatabase2.get(i).toString() + "\n");
				System.out.println(i+1);
			}
		}
	}
}
