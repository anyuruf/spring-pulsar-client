package net.anyuruf.pulsar_client.models;

import java.io.Serializable;

public record Item  (long id, long createdAt, String adjective,String category,
    String modifier, String name, double price) implements Serializable {}
