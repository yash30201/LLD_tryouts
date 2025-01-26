package com.stackoverflow.Model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final public class Answer extends Container{
    private final Question associatedQuestion;
    private final List<Comment> comments;

    private Answer(Question associatedQuestion, String id, User author, String content) {
        super(id, author, Instant.now().getEpochSecond(), content);
        this.associatedQuestion = associatedQuestion;
        this.comments = new ArrayList<>();
    }

    public static Answer createAnswer(Question associatedQuestion, User author, String content) {
        UUID uuid = UUID.randomUUID();
        return new Answer(associatedQuestion, uuid.toString(), author, content);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
