package com.baeldung.resource.web.controller;

import com.baeldung.resource.persistence.model.Foo;
import com.baeldung.resource.service.IFooService;
import com.baeldung.resource.web.dto.FooDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/secured/foos")
public class FooSecuredController {
    private IFooService fooService;

    public FooSecuredController(IFooService fooService) {
        this.fooService = fooService;
    }

    @CrossOrigin(origins = "http://localhost:8089")
    @GetMapping
    public Collection<FooDto> findAll() {
        Iterable<Foo> foos = this.fooService.findAll();
        List<FooDto> fooDtos = new ArrayList<>();
        foos.forEach(p -> fooDtos.add(convertToDto(p)));
        return fooDtos;
    }

    protected FooDto convertToDto(Foo entity) {
        return new FooDto(entity.getId(), entity.getName());
    }
}
