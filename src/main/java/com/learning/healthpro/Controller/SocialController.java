package com.learning.healthpro.controller;

import com.learning.healthpro.common.Result;
import com.learning.healthpro.context.ConcurrentContext;
import com.learning.healthpro.entity.Comment;
import com.learning.healthpro.entity.Favorite;
import com.learning.healthpro.entity.Follow;
import com.learning.healthpro.entity.Post;
import com.learning.healthpro.service.CommentService;
import com.learning.healthpro.service.FavoriteService;
import com.learning.healthpro.service.FollowService;
import com.learning.healthpro.service.PostLikeService;
import com.learning.healthpro.service.PostService;
import com.learning.healthpro.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Social")
@RestController
public class SocialController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FollowService followService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PostLikeService postLikeService;

    // ==================== 帖子相关 ====================

    @GetMapping("/GetPosts")
    public Result getPosts(@RequestParam(required = false) String category) {
        ArrayList<Post> posts;
        if (category != null && !category.isEmpty()) {
            posts = postService.getPostsByCategory(category);
        } else {
            posts = postService.getAllPosts();
        }
        return Result.success(posts);
    }

    @GetMapping("/GetFollowingPosts")
    public Result getFollowingPosts() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<Follow> followingList = followService.getFollowingList(userId);
        List<Integer> followingIds = followingList.stream().map(Follow::getFollowing_id).collect(Collectors.toList());
        ArrayList<Post> posts = postService.getPostsByFollowingIds(followingIds);
        return Result.success(posts);
    }

    @GetMapping("/GetPostById/{id}")
    public Result getPostById(@PathVariable int id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return Result.error("帖子不存在");
        }
        return Result.success(post);
    }

    @GetMapping("/GetPostsByUser/{userId}")
    public Result getPostsByUser(@PathVariable int userId) {
        ArrayList<Post> posts = postService.getPostsByUserId(userId);
        return Result.success(posts);
    }

    @PostMapping("/AddPost")
    public Result addPost(@RequestBody Post post) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        post.setUser_id(userId);
        try {
            var user = userInfoService.getInfo(userId);
            if (user != null && user.getName() != null) {
                post.setUser_name(user.getName());
            } else {
                post.setUser_name("用户" + userId);
            }
        } catch (Exception e) {
            post.setUser_name("用户" + userId);
        }
        post.setLikes(0);
        post.setComments(0);
        post.setStatus(1); // 无审核 直接通过，无需审核
        post.setIs_top(0);
        postService.addPost(post);
        return Result.success();
    }

    @PostMapping("/LikePost/{id}")
    public Result likePost(@PathVariable int id) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean success = postLikeService.like(userId, id);
        if (success) {
            return Result.success();
        } else {
            return Result.error("已经点赞过了");
        }
    }

    @PostMapping("/UnlikePost/{id}")
    public Result unlikePost(@PathVariable int id) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean success = postLikeService.unlike(userId, id);
        if (success) {
            return Result.success();
        } else {
            return Result.error("尚未点赞");
        }
    }

    @GetMapping("/IsLiked/{id}")
    public Result isLiked(@PathVariable int id) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean liked = postLikeService.isLiked(userId, id);
        return Result.success(liked);
    }

    @DeleteMapping("/DeletePost/{id}")
    public Result deletePost(@PathVariable int id) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        postService.deletePost(id);
        return Result.success();
    }

    // ==================== 评论相关 ====================

    @GetMapping("/GetComments/{postId}")
    public Result getComments(@PathVariable int postId) {
        ArrayList<Comment> comments = commentService.getCommentsByPostId(postId);
        return Result.success(comments);
    }

    @PostMapping("/AddComment")
    public Result addComment(@RequestBody Comment comment) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        comment.setUser_id(userId);
        try {
            var user = userInfoService.getInfo(userId);
            if (user != null && user.getName() != null) {
                comment.setUser_name(user.getName());
            } else {
                comment.setUser_name("用户" + userId);
            }
        } catch (Exception e) {
            comment.setUser_name("用户" + userId);
        }
        commentService.addComment(comment);
        postService.commentPost(comment.getPost_id());
        return Result.success();
    }

    @DeleteMapping("/DeleteComment/{id}")
    public Result deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return Result.success();
    }

    // ==================== 关注相关 ====================

    @PostMapping("/Follow/{followingId}")
    public Result follow(@PathVariable int followingId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        if (userId == followingId) {
            return Result.error("不能关注自己");
        }
        String date = java.time.LocalDate.now().toString();
        followService.follow(userId, followingId, date);
        return Result.success();
    }

    @PostMapping("/Unfollow/{followingId}")
    public Result unfollow(@PathVariable int followingId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        followService.unfollow(userId, followingId);
        return Result.success();
    }

    @GetMapping("/IsFollowing/{followingId}")
    public Result isFollowing(@PathVariable int followingId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean following = followService.isFollowing(userId, followingId);
        return Result.success(following);
    }

    @GetMapping("/GetFollowingList/{userId}")
    public Result getFollowingList(@PathVariable int userId) {
        ArrayList<Follow> list = followService.getFollowingList(userId);
        return Result.success(list);
    }

    @GetMapping("/GetFollowerList/{userId}")
    public Result getFollowerList(@PathVariable int userId) {
        ArrayList<Follow> list = followService.getFollowerList(userId);
        return Result.success(list);
    }

    @GetMapping("/GetFollowerCount/{userId}")
    public Result getFollowerCount(@PathVariable int userId) {
        int count = followService.getFollowerCount(userId);
        return Result.success(count);
    }

    @GetMapping("/GetFollowingCount/{userId}")
    public Result getFollowingCount(@PathVariable int userId) {
        int count = followService.getFollowingCount(userId);
        return Result.success(count);
    }

    // ==================== 收藏相关 ====================

    @PostMapping("/Favorite/{postId}")
    public Result favorite(@PathVariable int postId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        String date = java.time.LocalDate.now().toString();
        favoriteService.favorite(userId, postId, date);
        return Result.success();
    }

    @PostMapping("/Unfavorite/{postId}")
    public Result unfavorite(@PathVariable int postId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        favoriteService.unfavorite(userId, postId);
        return Result.success();
    }

    @GetMapping("/IsFavorite/{postId}")
    public Result isFavorite(@PathVariable int postId) {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        boolean isFav = favoriteService.isFavorite(userId, postId);
        return Result.success(isFav);
    }

    @GetMapping("/GetFavoritePosts")
    public Result getFavoritePosts() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        ArrayList<Post> posts = favoriteService.getFavoritePosts(userId);
        return Result.success(posts);
    }

    @GetMapping("/GetFavoriteCount")
    public Result getFavoriteCount() {
        Integer userId = ConcurrentContext.get();
        if (userId == null) {
            return Result.error("未找到用户信息");
        }
        int count = favoriteService.getFavoriteCount(userId);
        return Result.success(count);
    }
}
