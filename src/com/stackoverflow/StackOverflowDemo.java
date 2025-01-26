package com.stackoverflow;

import com.Runner.Runner;
import com.stackoverflow.Model.Tag;
import com.stackoverflow.Model.User;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StackOverflowDemo implements Runner {
    private enum OP {
        SHOW,
        CLOSE,
        POST_ANSWER,
        POST_QUESTION,
        POST_COMMENT,
        SEARCH_BY_USER,
        SEARCH_BY_TAG,
        SEARCH_BY_PHRASE
    } 
    private final Scanner scanner;
    private final StackOverflow system;

    public StackOverflowDemo() {
        scanner = new Scanner(System.in);
        system = StackOverflow.getInstance();
    }
    
    @Override
    public void run() {
        sayGreetings();
        while (true) {
            OP op = getOp();
            switch (op) {
                case SHOW -> {
                    sayAvailableOps();
                    break;
                }
                case CLOSE -> {
                    sayClosingGreetings();
                    return;
                }
                case POST_QUESTION -> {
                    User user = getUser();
                    if (user == null) {
                        break;
                    }
                    String uuid = getQuestionAndPost(user);
                    System.out.println("Question posted. question id : " + uuid);
                }
                case POST_ANSWER -> {
                    User user = getUser();
                    if (user == null) {
                        break;
                    }
                    String questionId = getId("question");
                    String answerId = getAnswerAndPost(user, questionId);
                    System.out.println("Answer posted, answer id : " + answerId);
                }
                case POST_COMMENT -> {
                    User user = getUser();
                    if (user == null) {
                        break;
                    }
                    String elementId = getId("element");
                    String answerId = getCommentAndPost(user, elementId);
                    System.out.println("Comment posted, answer id : " + answerId);
                }
                case SEARCH_BY_PHRASE -> {
                    String phrase = getPhrase();
                    List<List<String>> res = system.searchByPhrase(phrase);
                    showQuestions(res);
                }
                case SEARCH_BY_TAG -> {
                    Tag tag = getTag();
                    List<List<String>> res = system.searchByTag(tag);
                    showQuestions(res);
                }
                case SEARCH_BY_USER -> {
                    User user = getUser();
                    if (user == null) {
                        break;
                    }
                    List<List<String>> res = system.searchByUsername(user.getUsername());
                    showQuestions(res);
                }
            }
        }
    }

    private void showQuestions(List<List<String>> questions) {
        int counter = 0;
        for (List<String> question : questions) {
            System.out.println("Question " + ++counter);
            System.out.println("    ID : " + question.get(0));
            System.out.println("    content : " + question.get(1));
        }
    }
    
    private String getPhrase() {
        System.out.print("Enter phrase: ");
        String res = scanner.nextLine();
        return res;
    }
    
    private String getId(String x) {
        System.out.print("Enter " + x + " id: ");
        String res = scanner.next();
        scanner.nextLine();
        return res;
    }
    
    private String getQuestionAndPost(User author) {
        System.out.print("Enter question: ");
        String content =  scanner.nextLine();
        Tag tag = getTag();
        return system.postQuestion(author, content, List.of(tag));
    }
    
    private Tag getTag() {
        Tag tag = null;
        while (true) {
            System.out.print("What is the question tag? (");
            for (var x : Tag.values()) {
                System.out.print(x.toString() + ", ");
            }
            System.out.print(" : ");
            String res = scanner.next();
            scanner.nextLine();
            try {
                tag = Tag.valueOf(res);
                break;
            } catch (Exception e) {
                System.out.println("Invalid tag, please retry");
            }
        }
        return tag;
    } 

    private String getAnswerAndPost(User author, String questionId) {
        System.out.print("Enter answer: ");
        String content =  scanner.nextLine();
        return system.postAnswer(author, questionId, content);
    }

    private String getCommentAndPost(User author, String elementId) {
        System.out.print("Enter comment: ");
        String content =  scanner.nextLine();
        return system.postComment(author, elementId, content);
    }
    
    private OP getOp() {
        OP op;
        while (true) {
            System.out.print("What do you want to do? : ");
            String res = scanner.next();
            scanner.nextLine();
            try {
                op = OP.valueOf(res);
                break;
            } catch (Exception e) {
                System.out.println("Unrecognized operation, please choose one of the following:");
                sayAvailableOps();
            }
        }
        return op;
    }
    
    private @Nullable User getUser() {
        User user = null;
        System.out.print("What's your username? : ");
        String username = scanner.next();
        scanner.nextLine();
        Optional<User> userOr = system.getUser(username);
        if (userOr.isEmpty()) {
            System.out.print("Username doesn't exists, do you want to create new user?(yes, no): ");
            String res = scanner.next();
            scanner.nextLine();
            if (res.equals("yes")) {
                user = createUser();
            }
        } else {
            user = userOr.get();
        }
        return user;
    }
    
    private User createUser() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.next();
        scanner.nextLine();
        return system.createUser(username, email);
    }
    
    private void sayGreetings() {
        System.out.println("Welcome to the stack overflow demo");
        sayAvailableOps();
    }
    
    private void sayClosingGreetings() {
        System.out.println("Thank you for using stack overflow");
    }
    
    private void sayAvailableOps() {
        System.out.println("""
                We support the following operations:
                    - SHOW => Show available ops
                    - CLOSE => Close the system
                    - POST_QUESTION => Post Question
                    - POST_ANSWER => Post Answer
                    - POST_COMMENT => Post Comment
                    - SEARCH_BY_USER => Search questions by User
                    - SEARCH_BY_TAG => Search questions by Tag
                    - SEARCH_BY_PHRASE => Search questions by content
                """);
    }
}
