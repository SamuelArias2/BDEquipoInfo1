package org.admin.controllers;

import org.admin.entities.deviceassignment;
import org.admin.services.interfaces.ideviceassignmentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/deviceassignments")
public class deviceassignmentcontroller {
    //comentario
    @Autowired
    private ideviceassignmentservice deviceassignmentservice;

    public String index(Model model, @RequestParam("page")Optional<Integer> page, @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(1) -1; //si no esta seteado se asigna 0
        int pageSize = size.orElse(5); //tamaño de la pagina, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<deviceassignment> deviceassignments = deviceassignmentservice.findAll(pageable);
        model.addAttribute("deviceassignments", deviceassignments);

        int totalPages = deviceassignments.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
        }
         return  "deviceassignment/index";
    }
}
