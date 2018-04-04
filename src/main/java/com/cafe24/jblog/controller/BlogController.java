package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.CategoryService;
import com.cafe24.jblog.service.FileUploadService;
import com.cafe24.jblog.service.PostService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.Auth.Role;
import com.cafe24.security.AuthUser;

@RequestMapping(value = "/{id:(?!assets).*}")
@Controller
public class BlogController {

	@Autowired
	BlogService blogService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PostService postService;

	@Auth(role=Role.GUEST)
	@RequestMapping(value={"", "/{path1:(?!images).*}", "/{path1:(?!images).*}/{path2}"})
	public String blogMain(
			Model model, 
			@PathVariable(value = "id") String id, 
			@PathVariable(value = "path1") Optional<Long> path1, 
			@PathVariable(value = "path2") Optional<Long> path2) {
		
		BlogVo blog = blogService.getBlog(id);
		if (blog == null) {
			return "/main/index";
		}
		
		List<PostVo> posts = null;
		PostVo mainPost = null;
		
		if(path2.isPresent()) {
			posts = postService.getPostList(blog.getNo(), path1.get());
			mainPost = postService.getPost(path2.get());
		} else if(path1.isPresent()) {
			posts = postService.getPostList(blog.getNo(), path1.get());			
		} else {
			posts = postService.getPostList(blog.getNo());			
		}
		
		List<CategoryVo> categories = categoryService.getCtegories(blog.getNo());	
		
		if(posts.size() != 0 && mainPost == null) {
			mainPost = posts.get(0);
		}
		model.addAttribute("mainPost", mainPost);
		model.addAttribute("blog", blog);
		model.addAttribute("posts", posts);
		model.addAttribute("categories", categories);
		model.addAttribute("id", id);

		return "/blog/blog-main";
	}

	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String adminBasicUpdate(
			Model model, 
			@PathVariable(value = "id") String id, 
			@AuthUser UserVo authUser) {

		if (authUser == null) {
			return "/user/login";
		}
		if(authUser.getId().equals(id) == false) {
			return "redirect:/"+ id + "/user/login";
		}
		
		BlogVo blog = blogService.getBlog(id);
		model.addAttribute("blog", blog);
		model.addAttribute("id", id);

		return "/blog/blog-admin-basic";
	}
	
	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String adminBasicUpdate(
			Model model, 
			@PathVariable(value = "id") String id, 
			@AuthUser UserVo authUser, 
			@RequestParam(value="title", required=false) String title, 
			@RequestParam("logofile") MultipartFile multipartFile) {

		if (authUser == null) {
			return "/user/login";
		}

		BlogVo blog = blogService.getBlog(id);
		if(blog == null) {
			return "/main/index";
		}
		
		blogService.adminBasicUpdate(blog, title, multipartFile, fileUploadService);
		
		boolean result = blogService.updateBlogInfo(blog);
		if(result == false) {
			return "/blog/blog-admin-basic";
		}
		
		model.addAttribute("blog", blog);

		return "redirect:/" + authUser.getId();
	}
	
	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(
			@PathVariable(value="id") String id, 
			@AuthUser UserVo authUser, 
			Model model) {
		
		if (authUser == null) {
			return "/user/login";
		}
		
		BlogVo blog = blogService.getBlog(id);
		List<CategoryVo> categories = categoryService.getCtegories(blog.getNo());
		model.addAttribute("blog", blog);
		model.addAttribute("list", categories);
		
		return "/blog/blog-admin-category";
	}
	
	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String writePost(
			Model model, 
			@AuthUser UserVo authUser, 
			@PathVariable(value="id") String id ) {
		BlogVo blog = blogService.getBlog(authUser.getId());
		
		List<CategoryVo> categories = categoryService.getCtegories(blog.getNo());
		
		model.addAttribute("categories", categories);		
		
		return "/blog/blog-admin-write";
	}
	
	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String writePost(
			@PathVariable(value="id") String id, 
			@ModelAttribute PostVo post,  
			@AuthUser UserVo authUser) {
				
		postService.writePost(post, authUser);
		
		return "redirect:/" + authUser.getId();
	}

}
