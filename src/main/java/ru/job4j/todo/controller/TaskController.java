package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/tasks") /* Работать с фильмами будем по URI/tasks/***/
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpServletRequest request) {
        var taskOptional = taskService.findById(id);
        model.addAttribute("task", taskOptional.get());
        var session = request.getSession();
        session.setAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAllOrderById());
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session) {
        var user = (User) session.getAttribute("user");
        task.setUser(user);
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage() {
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
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }


    @GetMapping("/completed")
    public String getPageTasksIsDone(Model model) {
        model.addAttribute("tasks", taskService.findAllByDone(TRUE));
        return "tasks/completed";
    }

    @GetMapping("/unfulfilled")
    public String getPageTasksNotDone(Model model) {
        model.addAttribute("tasks", taskService.findAllByDone(FALSE));
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