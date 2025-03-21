@GetMapping("/admin")
public String adminPage(HttpSession session, Model model) {
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin != null && isAdmin) {
        model.addAttribute("loggedIn", true);
        model.addAttribute("counter1", counter1);
        model.addAttribute("counter2", counter2);
    } else {
        model.addAttribute("notLoggedIn", true);
    }
    return "admin";
}

@PostMapping("/admin/login")
public String handleAdminLogin(@RequestParam String password,
                               HttpSession session,
                               Model model) {
    if (ADMIN_PASSWORD != null && ADMIN_PASSWORD.equals(password)) {
        session.setAttribute("admin", true);
        return "redirect:/admin";
    } else {
        model.addAttribute("notLoggedIn", true);
        model.addAttribute("loginFailed", true);
        return "admin";
    }
}

@PostMapping("/admin/update")
public String updateCountersFromAdmin(@RequestParam int counter1,
                                      @RequestParam int counter2,
                                      HttpSession session,
                                      Model model) {
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin == null || !isAdmin) {
        return "redirect:/admin";
    }

    this.counter1 = counter1;
    this.counter2 = counter2;

    model.addAttribute("loggedIn", true);
    model.addAttribute("updateSuccess", true);
    model.addAttribute("counter1", this.counter1);
    model.addAttribute("counter2", this.counter2);
    return "admin";
}
