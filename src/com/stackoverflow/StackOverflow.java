package com.stackoverflow;

import com.stackoverflow.Model.*;

import java.util.*;

public class StackOverflow {
    private final List<User> users;
    private final List<Question> questions;
    private final Map<User, List<Container>> userArtifacts;
    private final Map<String, Container> idToArtifact;
    private static StackOverflow instance = new StackOverflow();

    private StackOverflow() {
        this.users = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.userArtifacts = new HashMap<>();
        this.idToArtifact = new HashMap<>();
    }

    public static StackOverflow getInstance() {
        return instance;
    }

    public Optional<User> getUser(String username) {
        for (var user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User createUser(String username, String email) {
        User user = User.createUser(username, email);
        users.add(user);
        return user;
    }

    public String postQuestion(User author, String content, List<Tag> tags) {
        Question question = Question.createQuestion(author, content);
        for (var tag : tags) {
            question.addTag(tag);
        }
        questions.add(question);
        addArtifact(author, question);
        idToArtifact.put(question.getId(), question);
        return question.getId();
    }

    public String postAnswer(User author, String questionId, String content) {
        Question question = (Question) idToArtifact.get(questionId);
        Answer answer = Answer.createAnswer(question, author, content);
        addArtifact(author, answer);
        idToArtifact.put(answer.getId(), answer);
        question.addAnswer(answer);
        return answer.getId();
    }

    public String postComment(User author, String containerId, String content) {
        Container container = idToArtifact.get(containerId);
        Comment comment = Comment.createComment(author, content, container);
        addArtifact(author, comment);
        idToArtifact.put(comment.getId(), comment);
        if (container instanceof Question) {
            Question question = (Question) container;
            question.addComment(comment);
        } else {
            Answer answer = (Answer) container;
            answer.addComment(comment);
        }
        return comment.getId();
    }

    public List<List<String>> searchByUsername(String username) {
        List<List<String>> result = new ArrayList<>();
        User user = null;
        for (User currUser : users) {
            if (currUser.getUsername().equals(username)) {
                user = currUser;
                break;
            }
        }
        if (user == null) {
            return result;
        }
        List<Container> elements = userArtifacts.get(user);
        for (var element : elements) {
            if (element instanceof Question) {
                result.add(List.of(element.getId(), element.getContent()));
            }
        }
        return result;
    }

    public List<List<String>> searchByTag(Tag tag) {
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, Container> entry : idToArtifact.entrySet()) {
            Container container = entry.getValue();
            if (container instanceof Question) {
                Question question = (Question) container;
                if (question.hasTag(tag)) {
                    result.add(List.of(entry.getKey(), question.getContent()));
                }
            }
        }
        return result;
    }

    public List<List<String>> searchByPhrase(String phrase) {
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, Container> entry : idToArtifact.entrySet()) {
            Container container = entry.getValue();
            if (container instanceof Question) {
                Question question = (Question) container;
                if (question.hasPhrase(phrase)) {
                    result.add(List.of(entry.getKey(), question.getContent()));
                }
            }
        }
        return result;
    }

    private void addArtifact(User author, Container container) {
        if (!userArtifacts.containsKey(author)) {
            userArtifacts.put(author, new ArrayList<>());
        }
        userArtifacts.get(author).add(container);
    }
}
