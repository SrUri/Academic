package deim.urv.cat.homework2.controller;

import jakarta.mvc.Controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("Shop")
public class ShopController {
    @GET
    public String showForm() {
        return "shop.jsp"; // Injects CRSF token
    }
}
