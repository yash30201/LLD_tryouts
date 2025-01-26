package com.stackoverflow.Model;

import java.time.Instant;
import java.util.UUID;

final public class Comment extends Container {
    private final Container associatedContainer;

    private Comment(String id, User author, String content, Container associatedContainer) {
        super(id, author, Instant.now().getEpochSecond(), content);
        this.associatedContainer = associatedContainer;
    }

    public static Comment createComment(User author, String content, Container associatedContainer) {
        UUID uuid = UUID.randomUUID();
        return new Comment(uuid.toString(), author, content, associatedContainer);
    }
}
