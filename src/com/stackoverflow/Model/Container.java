package com.stackoverflow.Model;

/**
 * Base class for Question and Answer which can act as containers for other
 * Container or Comments.
 */
public abstract class Container {
    private final String id;
    private final User author;
    private final Long creationTimestamp;
    private final String content;

    Container(String id, User author, Long creationTimestamp, String content) {
        this.id = id;
        this.author = author;
        this.creationTimestamp = creationTimestamp;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getContent() {
        return content;
    }
}
