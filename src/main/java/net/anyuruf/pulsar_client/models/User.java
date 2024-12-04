package net.anyuruf.pulsar_client.models;

import java.io.Serializable;

public record User (long id, String firstName, String lastName,
    String emailAddress, long createdAt, long deletedAt, long mergedAt,
    long parentUserId) implements Serializable{}
