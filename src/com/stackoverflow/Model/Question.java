package com.stackoverflow.Model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final public class Question extends Container {
    private final List<Answer> answers;
    private final List<Comment> comments;
    private final List<Tag> tags;

    private Question(String id, User author, String content) {
        super(id, author, Instant.now().getEpochSecond(), content);
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public static Question createQuestion(User author, String content) {
        UUID uuid = UUID.randomUUID();
        return new Question(uuid.toString(), author, content);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    public boolean hasPhrase(String phrase) {
        return getContent().toLowerCase().contains(phrase.toLowerCase());
    }
}
