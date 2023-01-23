package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
  private final ConcurrentHashMap<Long, Post> postsMap;
  private final AtomicLong idCounter;

  public PostRepository() {
    postsMap = new ConcurrentHashMap<>();
    idCounter = new AtomicLong(0);
  }

  public List<Post> all() {
    return new ArrayList<>(postsMap.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(postsMap.get(id));
  }

  public Post save(Post post) {
    if (post.getId()==0 && postsMap.size()==0){
      idCounter.getAndIncrement();
      postsMap.put(idCounter.get(),post);
    }
    if (post.getId()==0 && postsMap.size() > 0 || post.getId() > postsMap.size()){
      idCounter.getAndIncrement();
      postsMap.put(idCounter.get(),post);
    }
    if (postsMap.containsKey(post.getId())){
      postsMap.replace(post.getId(),post);
    }
    return post;
  }

  public void removeById(long id) {
    postsMap.remove(id);
  }
}
