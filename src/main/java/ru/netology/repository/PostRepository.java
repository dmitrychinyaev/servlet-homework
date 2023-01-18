package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {
  CopyOnWriteArrayList<Post> postsList;

  public List<Post> all() {
    return new ArrayList<>(postsList);
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postsList.get((int) id));
  }

  public Post save(Post post) {
    if (post.getId()==0 && postsList.size()==0){
      postsList.add(1,post);
    }
    if (post.getId()==0 && postsList.size() > 0 || post.getId() > postsList.size()){
      postsList.add(postsList.size() + 1,post);
    }
    if (post.getId() == postsList.get((int) post.getId()).getId()){
      postsList.get((int) post.getId()).setContent(post.getContent());
    }
    return post;
  }

  public void removeById(long id) {
    postsList.remove((int)id);
  }
}
