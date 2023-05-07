package com.vonage.api.interview.controller;

import com.vonage.api.interview.data.model.Document;
import com.vonage.api.interview.data.repository.DocumentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    DocumentRepositoryImpl documentRepository;

    @RequestMapping("/")
    public ModelAndView init() {
        return new ModelAndView("views/welcome");
    }

    @RequestMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("views/create");
    }

    @PostMapping("/save")
    private ModelAndView savePayment(@ModelAttribute Document document, Model model) {
        model.addAttribute("document", document);
        documentRepository.addDocument(document.getName(), document.getPages());
        return getDocuments();
    }

    @PostMapping("/search")
    private ModelAndView search(@ModelAttribute Document document, Model model) {
        model.addAttribute("document", document);
        List<Document> documents = documentRepository.searchDocument(document.getName());
        ModelAndView view = new ModelAndView("views/searchList");
        List<Document> documentsTotal = documentRepository.getDocuments();
        double percentage = ( (double) documents.size() / (double) documentsTotal.size() ) * 100;
        view.addObject("documents", documents);
        view.addObject("percentage", percentage);
        return view;
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    private ModelAndView getDocuments() {
        List<Document> documents = documentRepository.getDocuments();
        if (documents == null) {
            return new ModelAndView("views/welcome");
        }
        ModelAndView view = new ModelAndView("views/documentList");
        view.addObject("documents", documents);
        return view;
    }
}