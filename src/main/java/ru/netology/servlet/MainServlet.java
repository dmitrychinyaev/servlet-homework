package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private PostRepository repository;
    private PostService service;
    private final String methodGet = "GET";
    private final String methodPost = "POST";
    private final String methodDelete = "DELETE";
    private final String pathForReadingAllPosts = "/api/posts/?$";
    private final String pathForSavePost = "/api/posts";
    private final String pathForGetAndRemovePost = "/api/posts/\\d+";

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        repository = context.getBean(PostRepository.class);
        service = context.getBean(PostService.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(methodGet) && path.equals(pathForReadingAllPosts)) {
                controller.all(resp);
                return;
            }
            if (method.equals(methodPost) && path.equals(pathForSavePost)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(methodDelete) && path.matches(pathForGetAndRemovePost)) {
                controller.removeById(getPostID(path), resp);
                return;
            }
            if (method.equals(methodGet) && path.matches(pathForGetAndRemovePost)) {
                // easy way
                controller.getById(getPostID(path), resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getPostID(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

