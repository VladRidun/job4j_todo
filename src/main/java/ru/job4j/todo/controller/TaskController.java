package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static ru.job4j.todo.utility.TimeZoneWrapper.timeZoneWrap;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks") /* Работать с фильмами будем по URI/tasks/***/
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpServletRequest request) {
        var taskOptional = taskService.findById(id);
        model.addAttribute("task", taskOptional.get());
        var session = request.getSession();
        session.setAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping()
    public String getAll(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrap(user, taskService.findAllOrderById()));
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session,  @RequestParam List<Integer> categoriesId) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        task.getCategories().addAll(categoryService.findAllById(categoriesId));
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model) {
        var task = taskService.findById(id).get();
        model.addAttribute("task", task);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, HttpSession session, @RequestParam List<Integer> categoriesId) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        task.getCategories().addAll(categoryService.findAllById(categoriesId));
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/completed")
    public String getPageTasksIsDone(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrap(user, taskService.findAllByDone(TRUE)));
        return "tasks/completed";
    }

    @GetMapping("/unfulfilled")
    public String getPageTasksNotDone(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("tasks", timeZoneWrap(user, taskService.findAllByDone(FALSE)));
        return "tasks/unfulfilled";
    }

    @GetMapping("/done/{id}")
    public String getPageTaskIsDone(@PathVariable int id) {
        var task = taskService.findById(id).get();
        task.setDone(TRUE);
        taskService.update(task);
        return "redirect:/tasks/completed";
    }
}