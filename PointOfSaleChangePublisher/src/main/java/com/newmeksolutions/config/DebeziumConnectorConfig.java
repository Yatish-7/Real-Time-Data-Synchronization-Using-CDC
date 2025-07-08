package com.newmeksolutions.config;

import io.debezium.config.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class DebeziumConnectorConfig 
{
	 @Bean()
	    public Configuration posDBConfig() {
	        return Configuration.create()
	                .with("name", "pos_connector")
	                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
	                .with("plugin.name", "pgoutput")
	                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
	                .with("offset.storage.file.filename", "C:/debezium/pos_offsets.dat")
	                .with("database.hostname", "localhost")
	                .with("database.port", 5432)
	                .with("database.user", "postgres")
	                .with("database.password", "803671")
	                .with("database.dbname", "PointOfSale(CDC)")
	                .with("database.server.name", "pos_server")
	                .with("topic.prefix", "pos_server")
	                .with("slot.name", "pos_slot")
	                .with("publication.name", "pos_pub")
	                .with("table.include.list", "public.pos_kphb")
	                .build();
	    }

	   @Bean()
	    public Configuration ecommerceDBConfig() {
	        return Configuration.create()
	                .with("name", "ecommerce_connector")
	                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
	                .with("plugin.name", "pgoutput")
	                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
	                .with("offset.storage.file.filename", "C:/debezium/ecommerce_offsets.dat")
	                .with("database.hostname", "localhost")
	                .with("database.port", 5432)
	                .with("database.user", "postgres")
	                .with("database.password", "803671")
	                .with("database.dbname", "EcommerceDB(CDC)")
	                .with("database.server.name", "ecommerce_server")
	                .with("topic.prefix", "ecommerce_server")
	                .with("slot.name", "ecommerce_slot")
	                .with("publication.name", "ecommerce_pub")
	                .with("table.include.list", "public.onlineregistration")
	                .build();
	    }
}
